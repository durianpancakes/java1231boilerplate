package codeit.template.model;

import java.util.ArrayList;

public class ContactTracing {
    private Genome infected;
    private Genome origin;
    private ArrayList<Genome> cluster;

    public Genome getInfected() {
        return infected;
    }

    public void setInfected(Genome infected) {
        this.infected = infected;
    }

    public Genome getOrigin() {
        return origin;
    }

    public void setOrigin(Genome origin) {
        this.origin = origin;
    }

    public ArrayList<Genome> getCluster() {
        return cluster;
    }

    public void setCluster(ArrayList<Genome> cluster) {
        this.cluster = cluster;
    }
}
