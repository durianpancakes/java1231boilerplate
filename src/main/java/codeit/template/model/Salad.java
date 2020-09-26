package codeit.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Salad {
    @JsonProperty("number_of_salads")
    private final Integer input;

    public Salad(@JsonProperty("number_of_salads") Integer input){
        this.input = input;
    }

    public Integer getInput() {
        return input;
    }
}
