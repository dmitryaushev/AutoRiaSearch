package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.Car;
import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;

import java.util.List;

public interface SearchService {

    List<Car> searchAds(Search search);

    Car carDetails(String id);

    void saveSearch(Search search, User user);

    List<Search> findSearchListByUser(User user);
}