package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.dto.SearchDto;
import com.aushev.autoriasearch.dto.SearchMapper;
import com.aushev.autoriasearch.model.Ads;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchRepository searchRepository;
    private RestTemplate restTemplate;
    private SearchMapper mapper;

    private String url;
    private String urlCar;
    private String apiKey;
    private String templateRequestUrl;

    @Autowired
    public SearchServiceImpl(RestTemplateBuilder restTemplateBuilder, SearchRepository searchRepository,
                             SearchMapper mapper) {
        this.searchRepository = searchRepository;
        this.mapper = mapper;

        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }

    @Override
    public List<Car> searchAds(String requestUrl) {
        Ads ads = restTemplate.getForObject(String.format("%s%s%s", url, apiKey, requestUrl), Ads.class);
        List<Car> cars = new ArrayList<>();
        ads.getResult().getSearch_result().getIds().forEach(id -> cars.add(searchCar(id)));
        return cars;
    }

    @Override
    public Car searchCar(String id) {
        return restTemplate.getForObject(String.format(urlCar, apiKey, id), Car.class);
    }


    @Override
    public void saveSearch(Search search, User user) {
        search.setUser(user);
        search.setDate(LocalDateTime.now().withNano(0));
        searchRepository.save(search);
    }

    @Override
    public List<SearchDto> findSearchListByUser(User user, Pageable pageable, Boolean mailing) {
        List<Search> searchList;
        if (Objects.isNull(mailing)) {
            searchList = searchRepository.findAllByUser(user, pageable);
        } else if (mailing) {
            searchList = searchRepository.findAllByUserAndMailing(user, pageable, true);
        } else {
            searchList = searchRepository.findAllByUserAndMailing(user, pageable, false);
        }
        List<SearchDto> searchDtoList = new ArrayList<>();
        searchList.forEach(search -> searchDtoList.add(mapper.toDto(search)));
        return searchDtoList;
    }

    @Override
    public Map<String, String> findLatestRecords(User user) {
        List<Search> searchList = searchRepository.findTop5ByUserOrderByDateDesc(user);
        Map<String, String> searchDtoMap = new LinkedHashMap<>();
        searchList.forEach(search -> {
            String title = mapper.toDto(search).getTitle();
            if (!searchDtoMap.containsKey(title)) {
                searchDtoMap.put(title, requestUrl(search));
            }
        });
        return searchDtoMap;
    }

    @Override
    public String requestUrl(Search search) {

        String bodyStyle = search.getBodyStyle();
        String brand = search.getBrand();
        String model = search.getModel();
        String state = search.getState();
        String city = search.getCity();
        String type = search.getType();
        String gearBox = search.getGearBox();
        String color = search.getColor();
        String top = search.getTop();
        String priceFrom = search.getPriceFrom();
        String priceTo = search.getPriceTo();
        String currency = search.getCurrency();
        String countPage = search.getCountPage();

        if (Objects.isNull(countPage) || countPage.isEmpty()) {
            countPage = "10";
        }

        return String.format(templateRequestUrl,
                bodyStyle, brand, model, currency, state, city, type, gearBox, color, top, priceFrom, priceTo,
                countPage);
    }

    @Override
    public String requestUrlPage(String requestUrl, String prev, String next) {

        int page = Character.getNumericValue(requestUrl.charAt(requestUrl.length() - 1));
        String requestUrlPage = requestUrl.substring(0, requestUrl.length() - 1);

        if (Objects.nonNull(prev) && page == 0) {
            return requestUrl;
        }
        if (Objects.nonNull(prev)) {
            page--;
            requestUrlPage = requestUrlPage + page;
        }
        if (Objects.nonNull(next)) {
            page++;
            requestUrlPage = requestUrlPage + page;
        }
        return requestUrlPage;
    }

    @Value("${url}")
    public void setUrl(String url) {
        this.url = url;
    }

    @Value("${urlCar}")
    public void setUrlCar(String urlCar) {
        this.urlCar = urlCar;
    }

    @Value("${apiKey}")
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Value("${templateRequestUrl}")
    public void setTemplateRequestUrl(String templateRequestUrl) {
        this.templateRequestUrl = templateRequestUrl;
    }
}
