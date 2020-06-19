package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.dto.SearchDto;
import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SearchService {

    List<Car> searchAds(String requestUrl);

    Car searchCar(String id);

    void saveSearch(Search search, User user);

    List<SearchDto> findSearchListByUser(User user, Pageable pageable);

    Map<String, String> findLatestRecords(User user);

    String requestUrl(Search search);

    String requestUrlPage(String requestUrl, String prev, String next);
}
