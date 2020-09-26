package codeit.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class StreetMap {
    @JsonProperty("salad_prices_street_map")
    private ArrayList<Integer> streetMap = new ArrayList<>();

    public StreetMap(@JsonProperty("salad_prices_street_map") ArrayList<Integer> streetMap){
        this.streetMap = streetMap;
    }

    public ArrayList<Integer> getInput() {
        return streetMap;
    }
}
