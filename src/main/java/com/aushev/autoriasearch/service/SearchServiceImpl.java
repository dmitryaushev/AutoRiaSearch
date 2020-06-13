package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.dto.SearchDto;
import com.aushev.autoriasearch.dto.SearchMapper;
import com.aushev.autoriasearch.model.Ads;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.repository.SearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
    private Gson gson;
    private static final String URL = "https://developers.ria.com/auto/search?api_key=%s&category_id=1" +
            "&bodystyle=%s&marka_id=%s&model_id=%s&currency=%s&state=%s&city=%s" +
            "&type=%s&gearbox=%s&color=%s&top=%s&price_ot=%s&price_do=%s";
    private static final String URL_CAR = "https://developers.ria.com/auto/info?api_key=%s&auto_id=%s";
    private static final String API_KEY = "u40zPliQ825IX3L5QSqJ4qswnDBEA6KIyueZ0T7y";

    @Autowired
    public SearchServiceImpl(RestTemplateBuilder restTemplateBuilder, SearchRepository searchRepository,
                             SearchMapper mapper, Gson gson) {
        this.searchRepository = searchRepository;
        this.mapper = mapper;
        this.gson = gson;

        this.restTemplate = restTemplateBuilder.build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
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

        String url = String.format(URL,
                API_KEY, bodyStyle, brand, model, currency, state, city, type, gearBox, color, top, priceFrom, priceTo);

        Ads ads = restTemplate.getForObject(url, Ads.class);

        List<Car> cars = new ArrayList<>();
        ads.getResult().getSearch_result().getIds().forEach(id ->
                cars.add(carDetails(id)));
        return cars;
    }

    @Override
    public Car carDetails(String id) {
        JsonNode node = restTemplate.getForObject(String.format(URL_CAR, API_KEY, id), JsonNode.class);
        return gson.fromJson(node.toString(), Car.class);
    }


    @Override
    public void saveSearch(Search search, User user) {

        search.setUser(user);
        search.setDate(LocalDateTime.now().withNano(0));
        searchRepository.save(search);
    }

    @Override
    public List<SearchDto> findSearchListByUser(User user) {
        List<Search> searchList = searchRepository.findAllByUser(user);
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
}
