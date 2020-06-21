package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.dto.SearchMapper;
import com.aushev.autoriasearch.exception.SearchException;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.Config;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.search.Top;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.repository.ConfigRepository;
import com.aushev.autoriasearch.repository.SearchRepository;
import com.aushev.autoriasearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@Service
public class MailServiceImpl implements MailService {

    private ConfigRepository configRepository;
    private SearchRepository searchRepository;
    private UserRepository userRepository;
    private JavaMailSender javaMailSender;
    private SearchService searchService;
    private TaskScheduler taskScheduler;
    private SearchMapper mapper;

    private static final int HOURS = 0;
    private static final int MINUTES = 1;
    private static final String COUNT_PAGE = "100";

    private String cronExpression;
    private String mailingTitle;
    private String mailingTime;
    private String subject;
    private ScheduledFuture scheduledFuture;

    public MailServiceImpl(ConfigRepository configRepository, SearchRepository searchRepository,
                           UserRepository userRepository, JavaMailSender javaMailSender, SearchService searchService,
                           TaskScheduler taskScheduler, SearchMapper mapper) {
        this.configRepository = configRepository;
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.searchService = searchService;
        this.taskScheduler = taskScheduler;
        this.mapper = mapper;
    }

    @Override
    public void setMailingTime(Config config) {
        String[] timeSplit = config.getValue().split(":");
        String cron = String.format(cronExpression, timeSplit[MINUTES], timeSplit[HOURS]);
        if (Objects.nonNull(scheduledFuture)) {
            scheduledFuture.cancel(true);
        }
        scheduledFuture = taskScheduler.schedule(this::mailing, new CronTrigger(cron));
    }

    @Override
    public void saveMailingTime(Config config) {
        Config existConfig = configRepository.findByTitle(mailingTitle).orElse(defaultMailingTime());
        existConfig.setValue(config.getValue());
        configRepository.save(existConfig);
    }

    @Override
    public void deactivateMailing(int searchId, int userId) {
        Search search = searchRepository.findById(searchId).orElseThrow(() ->
                new SearchException("Друг, что-то ты не то клацнул"));
        if (search.getUser().getId() != userId) {
            throw new SearchException("Дружище, видимо, ты кого-то без расслыки хочешь оставить");
        }
        search.setMailing(false);
        searchRepository.save(search);
    }

    @Override
    public void activateMailing(int searchId, int userId) {
        Search search = searchRepository.findById(searchId).orElseThrow(() ->
                new SearchException("Друг, что-то ты не то клацнул"));
        if (search.getUser().getId() != userId) {
            throw new SearchException("Дружище, не стоит за кого-то решать, что ему рассылать");
        }
        search.setMailing(true);
        searchRepository.save(search);
    }

    private void mailing() {
        Map<String, List<Search>> usersSearch = findUsersForMailing();
        Map<String, String> usersText = mailText(usersSearch);
        sendEmail(usersText);
    }

    private Map<String, List<Search>> findUsersForMailing() {

        Map<String, List<Search>> usersSearch = new HashMap<>();
        List<User> users = userRepository.findAllByMailing();
        users.forEach(user -> {
            List<Search> searchList = searchRepository.findAllByUserAndMailing(user, true);
            usersSearch.put(user.getEmail(), searchList);
        });
        return usersSearch;
    }

    private Map<String, String> mailText(Map<String, List<Search>> usersSearch) {

        Map<String, String> usersText = new HashMap<>();
        usersSearch.forEach((email, searchList) -> {

            StringBuilder text = new StringBuilder("Добрый день!<br><br>По вашим запросам:<br><br>");
            searchList.forEach(search -> {

                search.setTop(Top.DAY.getValue());
                search.setCountPage(COUNT_PAGE);
                String requestUrl = searchService.requestUrl(search);
                List<Car> cars = searchService.searchAds(requestUrl);
                text.append(String.format("<b>%s</b> появилось %s новых объявлений: ",
                        mapper.toDto(search).getTitle(), cars.size()));
                cars.forEach(car ->
                        text.append(String.format("<a href=\"https://auto.ria.com%s\">%s %s USD</a>, ",
                                car.getLinkToView(), car.getTitle(), car.getUSD())));
                text.replace(text.length() - 2, text.length() - 1, ".");
                text.append("<br><br>");
            });
            text.append("С уважением,<br><br>Aushev.");
            usersText.put(email, text.toString());
        });
        return usersText;
    }

    private void sendEmail(Map<String, String> usersText) {

        usersText.forEach((email, text) -> {
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
                helper.setTo(email);
                helper.setSubject(subject);
                helper.setText(text, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            javaMailSender.send(message);
        });
    }

    private Config defaultMailingTime() {
        Config config = new Config();
        config.setTitle(mailingTitle);
        config.setValue(mailingTime);
        return config;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startupMailingTime() {
        Config config = configRepository.findByTitle(mailingTitle).orElse(defaultMailingTime());
        setMailingTime(config);
    }

    @Value("${cronExpression}")
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Value("${mailingTitle}")
    public void setMailingTitle(String mailingTitle) {
        this.mailingTitle = mailingTitle;
    }

    @Value("${mailingTime}")
    public void setMailingTime(String mailingTime) {
        this.mailingTime = mailingTime;
    }

    @Value("${subject}")
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
