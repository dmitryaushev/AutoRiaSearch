package com.aushev.autoriasearch.controller;

import com.aushev.autoriasearch.config.UserPrincipal;
import com.aushev.autoriasearch.model.search.*;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/searchForm")
    public String showSearchForm(Model model, Authentication authentication) {
        model.addAttribute("searchList", searchService.findLatestRecords(getUser(authentication)));
        enumValues(model);
        return "search_form";
    }

    @PostMapping("/searchForm")
    public String search(@ModelAttribute("search") @Valid Search search, BindingResult result, Model model,
                         Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("searchList", searchService.findLatestRecords(getUser(authentication)));
            enumValues(model);
            return "search_form";
        }

        String requestUrl = searchService.requestUrl(search);
        model.addAttribute("cars", searchService.searchAds(requestUrl));
        model.addAttribute("requestUrl", requestUrl);
        searchService.saveSearch(search, getUser(authentication));
        enumValues(model);
        return "search_form";
    }

    @GetMapping("car")
    public String carDetails(@RequestParam("id") String id, Model model) {
        model.addAttribute("car", searchService.searchCar(id));
        return "car_details";
    }

    @GetMapping("/history")
    public String showHistory(Authentication authentication, Model model) {
        model.addAttribute("searchList", searchService.findSearchListByUser(getUser(authentication)));
        return "history";
    }

    @PostMapping("/page")
    public String page(@RequestParam(value = "prev", required = false) String prev,
                       @RequestParam(value = "next", required = false) String next,
                       @RequestParam("requestUrl") String url, Model model) {
        String requestUrlPage = searchService.requestUrlPage(url, prev, next);
        model.addAttribute("cars", searchService.searchAds(requestUrlPage));
        model.addAttribute("requestUrl", requestUrlPage);
        enumValues(model);
        return "search_form";
    }

    @ModelAttribute("search")
    public Search getDefaultSearch() {
        return new Search();
    }

    private void enumValues(Model model) {
        model.addAttribute("bodyStyle", BodyStyle.values());
        model.addAttribute("brand", Brand.values());
        model.addAttribute("model", com.aushev.autoriasearch.model.search.Model.values());
        model.addAttribute("state", State.values());
        model.addAttribute("city", City.values());
        model.addAttribute("type", Type.values());
        model.addAttribute("gearBox", GearBox.values());
        model.addAttribute("color", Color.values());
        model.addAttribute("top", Top.values());
        model.addAttribute("currency", Currency.values());
    }

    private User getUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        return user;
    }
}
