package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.dto.SearchDto;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;

import java.util.List;

public interface SearchService {

    List<Car> searchAds(String requestUrl);

    Car searchCar(String id);

    void saveSearch(Search search, User user);

    List<SearchDto> findSearchListByUser(User user);

    List<SearchDto> findLatestRecords(User user);

    String requestUrl(Search search);

    String requestUrlPage(String requestUrl, String prev, String next);
}
