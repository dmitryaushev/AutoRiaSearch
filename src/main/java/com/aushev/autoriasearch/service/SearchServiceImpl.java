package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.Ads;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.search.Brand;
import com.aushev.autoriasearch.model.search.City;
import com.aushev.autoriasearch.model.search.Model;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchRepository searchRepository;
    private RestTemplate restTemplate;
    private static final String URL_CAR = "https://developers.ria.com/auto/info?api_key=";
    private static final String API_KEY = "u40zPliQ825IX3L5QSqJ4qswnDBEA6KIyueZ0T7y";

    @Autowired
    public SearchServiceImpl(RestTemplateBuilder restTemplateBuilder, SearchRepository searchRepository) {
        this.searchRepository = searchRepository;

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


        StringBuilder url = new StringBuilder(String.format(
                "https://developers.ria.com/auto/search?api_key=%s&category_id=1", API_KEY));
        url.append(String.format("&bodystyle=%s&marka_id=%s&model_id=%s&currency=%s",
                bodyStyle, brand, model, currency));

        if (!state.isEmpty()) {
            url.append("&state=");
            url.append(state);
        }
        if (!city.isEmpty()) {
            if (state.isEmpty()) {
                state = Arrays.stream(City.values())
                        .filter(x -> x.getCity().equals(city))
                        .findAny()
                        .get()
                        .getState()
                        .getState();
                search.setState(state);
                url.append("&state=");
                url.append(state);
            }
            url.append("&city=");
            url.append(city);
        }
        if (!type.isEmpty()) {
            url.append("&type=");
            url.append(type);
        }
        if (!gearBox.isEmpty()) {
            url.append("&gearbox=");
            url.append(gearBox);
        }
        if (!color.isEmpty()) {
            url.append("&color=");
            url.append(color);
        }
        if (!top.isEmpty()) {
            url.append("&top=");
            url.append(top);
        }
        if (!priceFrom.isEmpty()) {
            url.append("&price_ot=");
            url.append(priceFrom);
        }
        if (!priceTo.isEmpty()) {
            url.append("&price_do=");
            url.append(priceTo);
        }

        Ads ads = restTemplate.getForObject(url.toString(), Ads.class);

        List<Car> cars = new ArrayList<>();
        ads.getResult().getSearch_result().getIds().forEach(id ->
                cars.add(carDetails(id)));
        return cars;
    }

    @Override
    public Car carDetails(String id) {
        return restTemplate.getForObject(String.format("%s%s&auto_id=%s", URL_CAR, API_KEY, id), Car.class);
    }


    @Override
    public void saveSearch(Search search, User user) {
        search.setUser(user);
        searchRepository.save(search);
    }

    @Override
    public List<Search> findSearchListByUser(User user) {
        List<Search> searchList = searchRepository.findAllByUser(user);
        searchList.forEach(search -> {
            String brand = Arrays.stream(Brand.values())
                    .filter(x -> x.getBrand().equals(search.getBrand()))
                    .findAny()
                    .get()
                    .getTitle();
            String model = Arrays.stream(Model.values())
                    .filter(x -> x.getModel().equals(search.getModel()))
                    .findAny()
                    .get()
                    .getTitle();
            search.setTitle(String.format("%s %s", brand, model));
        });
        return searchList;
    }
}
