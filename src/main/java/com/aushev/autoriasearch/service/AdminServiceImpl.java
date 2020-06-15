package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.dto.SearchMapper;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.Config;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.search.Top;
import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserStatus;
import com.aushev.autoriasearch.repository.ConfigRepository;
import com.aushev.autoriasearch.repository.SearchRepository;
import com.aushev.autoriasearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminServiceImpl implements AdminService {

    private ConfigRepository configRepository;
    private SearchRepository searchRepository;
    private UserRepository userRepository;
    private JavaMailSender javaMailSender;
    private SearchService searchService;
    private TaskScheduler taskScheduler;
    private SearchMapper mapper;

    private static final int HOURS = 0;
    private static final int MINUTES = 1;

    private String mailingTitle;
    private String mailingTime;
    private String subject;
    private ScheduledFuture scheduledFuture;

    @Autowired
    public AdminServiceImpl(SearchRepository searchRepository, UserRepository userRepository, SearchMapper mapper,
                            JavaMailSender javaMailSender, SearchService searchService,
                            TaskScheduler taskScheduler, ConfigRepository configRepository) {
        this.configRepository = configRepository;
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.searchService = searchService;
        this.taskScheduler = taskScheduler;
        this.mapper = mapper;
    }

    @Override
    public void sendEmail(String subject, String text, String... to) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    @Override
    public void mailing() {

        Map<String, List<Search>> mailing = new HashMap<>();

        List<User> users = userRepository.findAllByMailing();
        users.forEach(user -> {
            List<Search> searchList = searchRepository.findAllByUserAndMailing(user, true);
            mailing.put(user.getEmail(), searchList);
        });

        mailing.forEach((k, v) -> {

            StringBuilder text = new StringBuilder("Добрый день!<br><br>По вашим запросам:<br><br>");
            v.forEach(search -> {

                search.setTop(Top.DAY.getValue());
                List<Car> cars = searchService.searchAds(search);
                text.append(String.format("<b>%s</b> появилось %s новых объявлений: ",
                        mapper.toDto(search).getTitle(), cars.size()));
                cars.forEach(car ->
                        text.append(String.format("<a href=\"localhost:8080/search/car?id=%s\">%s %s USD</a>, ",
                                car.getAutoData().getAutoId(), car.getTitle(), car.getUSD())));
                text.append("<br><br>");
            });
            text.append("С уважением,<br><br>Aushev.");
            sendEmail(subject, text.toString(), k);
        });
    }

    @Override
    public void setMailingTime(Config config) {
        String[] timeSplit = config.getValue().split(":");
        String cron = String.format("0 %s %s ? * 1/1", timeSplit[MINUTES], timeSplit[HOURS]);
        if (Objects.nonNull(scheduledFuture)) {
            scheduledFuture.cancel(true);
        }
        scheduledFuture = taskScheduler.schedule(this::mailing, new CronTrigger(cron));
    }

    @Override
    public void saveMailingTime(Config config) {
        Config existConfig = configRepository.findByTitle(mailingTitle).orElse(defaultMailing());
        existConfig.setValue(config.getValue());
        configRepository.save(existConfig);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findNotActiveUsers() {
        return userRepository.findAllByUserStatus(UserStatus.NOT_ACTIVE);
    }

    @Override
    public void activateUsers(NotActiveUsers users) {

        users.getNotActiveUsers().forEach(user -> {
            user.setUserStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        });
    }

    @Override
    public void activateUser(int id) {
        User user = userRepository.findById(id).get();
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(int id) {
        User user = userRepository.findById(id).get();
        user.setUserStatus(UserStatus.NOT_ACTIVE);
        searchRepository.deactivateMailing(id);
        userRepository.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startupMailingTime() {
        Config config = configRepository.findByTitle(mailingTitle).orElse(defaultMailing());
        setMailingTime(config);
    }

    private Config defaultMailing() {
        Config config = new Config();
        config.setTitle(mailingTitle);
        config.setValue(mailingTime);
        return config;
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

