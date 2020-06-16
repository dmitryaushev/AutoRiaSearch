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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchRepository searchRepository;
    private RestTemplate restTemplate;
    private SearchMapper mapper;

    private String url;
    private String urlCar;
    private String apiKey;

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
    public List<Car> searchAds(Search search) {

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

        String requestUrl = String.format(url, apiKey,
                bodyStyle, brand, model, currency, state, city, type, gearBox, color, top, priceFrom, priceTo, countPage);

        Ads ads = restTemplate.getForObject(requestUrl, Ads.class);

        List<Car> cars = new ArrayList<>();
        ads.getResult().getSearch_result().getIds().forEach(id -> cars.add(carDetails(id)));
        return cars;
    }

    @Override
    public Car carDetails(String id) {
        return restTemplate.getForObject(String.format(urlCar, apiKey, id), Car.class);
    }


    @Override
    public void saveSearch(Search search, User user) {
        search.setUser(user);
        search.setDate(LocalDateTime.now().withNano(0));
        searchRepository.save(search);
    }

    @Override
    public List<SearchDto> findSearchListByUser(User user) {
        Pageable pageable = PageRequest.of(0, 50, Sort.by("date").descending());
        Page<Search> searchList = searchRepository.findAllByUser(user, pageable);
        List<SearchDto> searchDtoList = new ArrayList<>();
        searchList.forEach(search -> searchDtoList.add(mapper.toDto(search)));
        return searchDtoList;
    }

    @Override
    public List<SearchDto> findLatestRecords(User user) {
        List<Search> searchList = searchRepository.findTop5ByUserOrderByDateDesc(user);
        List<SearchDto> searchDtoList = new ArrayList<>();
        searchList.forEach(search -> searchDtoList.add(mapper.toDto(search)));
        return searchDtoList;
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
}
