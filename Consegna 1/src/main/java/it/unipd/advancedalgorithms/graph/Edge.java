package it.unipd.advancedalgorithms.graph;

public class Edge {
    private Integer start;
    private Integer end;
    private Integer weight;

    public Edge(int s, int e, int w) {
        start = s;
        end = e;
        weight = w;
    }

    public Edge() {
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
