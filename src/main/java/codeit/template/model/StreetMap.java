package codeit.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StreetMap {
    @JsonProperty("salad_prices_street_map")
    private final Integer input;

    public StreetMap(@JsonProperty("salad_prices_street_map") Integer streetMap){
        this.input = streetMap;
    }

    public Integer getInput() {
        return input;
    }
}
