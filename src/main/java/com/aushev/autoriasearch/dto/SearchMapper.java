package com.aushev.autoriasearch.dto;

import com.aushev.autoriasearch.dto.SearchDto;
import com.aushev.autoriasearch.model.search.BodyStyle;
import com.aushev.autoriasearch.model.search.Brand;
import com.aushev.autoriasearch.model.search.Model;
import com.aushev.autoriasearch.model.search.Search;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SearchMapper {

    public SearchDto toDto(Search search) {
        SearchDto searchDto = new SearchDto();

        BodyStyle bodyStyle = Arrays.stream(BodyStyle.values())
                .filter(value -> value.getBodyStyle().equals(search.getBodyStyle()))
                .findAny()
                .get();
        Brand brand = Arrays.stream(Brand.values())
                .filter(value -> value.getBrand().equals(search.getBrand()))
                .findAny()
                .get();
        Model model = Arrays.stream(Model.values())
                .filter(value -> value.getModel().equals(search.getModel()))
                .findAny()
                .get();
        String title = String.format("%s %s", brand.getTitle(), model.getTitle());

        searchDto.setPriceFrom(search.getPriceFrom());
        searchDto.setPriceTo(search.getPriceTo());
        searchDto.setBodyStyle(bodyStyle);
        searchDto.setBrand(brand);
        searchDto.setModel(model);
        searchDto.setDate(search.getDate());
        searchDto.setTitle(title);
        return searchDto;
    }

}
