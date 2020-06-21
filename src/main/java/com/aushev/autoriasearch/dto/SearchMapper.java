package com.aushev.autoriasearch.dto;

import com.aushev.autoriasearch.model.search.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SearchMapper {

    public SearchDto toDto(Search search) {
        SearchDto searchDto = new SearchDto();

        BodyStyle bodyStyle = Arrays.stream(BodyStyle.values())
                .filter(value -> value.getValue().equals(search.getBodyStyle()))
                .findAny()
                .get();
        Brand brand = Arrays.stream(Brand.values())
                .filter(value -> value.getValue().equals(search.getBrand()))
                .findAny()
                .get();
        Model model = Arrays.stream(Model.values())
                .filter(value -> value.getValue().equals(search.getModel()))
                .findAny()
                .get();
        State state = Arrays.stream(State.values())
                .filter(value -> value.getValue().equals(search.getState()))
                .findAny()
                .orElse(null);
        City city = Arrays.stream(City.values())
                .filter(value -> value.getValue().equals(search.getCity()))
                .findAny()
                .orElse(null);
        Type type = Arrays.stream(Type.values())
                .filter(value -> value.getValue().equals(search.getType()))
                .findAny()
                .orElse(null);
        GearBox gearBox = Arrays.stream(GearBox.values())
                .filter(value -> value.getValue().equals(search.getGearBox()))
                .findAny()
                .orElse(null);
        Color color = Arrays.stream(Color.values())
                .filter(value -> value.getValue().equals(search.getColor()))
                .findAny()
                .orElse(null);
        Top top = Arrays.stream(Top.values())
                .filter(value -> value.getValue().equals(search.getTop()))
                .findAny()
                .orElse(null);
        Currency currency = Arrays.stream(Currency.values())
                .filter(value -> value.getValue().equals(search.getCurrency()))
                .findAny()
                .get();
        String title = String.format("%s %s", brand.getName(), model.getName());

        searchDto.setId(search.getId());
        searchDto.setBodyStyle(bodyStyle);
        searchDto.setBrand(brand);
        searchDto.setModel(model);
        searchDto.setState(state);
        searchDto.setCity(city);
        searchDto.setType(type);
        searchDto.setGearBox(gearBox);
        searchDto.setColor(color);
        searchDto.setTop(top);
        searchDto.setCurrency(currency);
        searchDto.setPriceFrom(search.getPriceFrom());
        searchDto.setPriceTo(search.getPriceTo());
        searchDto.setDate(search.getDate());
        searchDto.setTitle(title);
        searchDto.setMailing(search.isMailing());
        return searchDto;
    }

}
