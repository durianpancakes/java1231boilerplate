package codeit.template.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genome {
    private String name;
    private String genome;
    private boolean isMutation = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        this.genome = genome;
    }

    public boolean isMutation() {
        return isMutation;
    }

    public void setMutation(boolean silentMutation) {
        isMutation = silentMutation;
    }

    @Override
    public String toString() {
        if (isMutation) {
            return name + "*";
        } else {
            return name;
        }
    }
}
