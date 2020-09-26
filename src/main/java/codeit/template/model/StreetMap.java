package codeit.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class StreetMap {
    @JsonProperty("salad_prices_street_map")
    private final Integer[][] streetMap;

    public StreetMap(@JsonProperty("salad_prices_street_map") Integer[][] streetMap){
        this.streetMap = streetMap;
    }

    public Integer[][] getInput() {
        return streetMap;
    }
}
