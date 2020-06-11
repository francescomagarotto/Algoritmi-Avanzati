package it.unipd.algoritmiavanzati.graph;

public class Edge {
    private Integer start;
    private Integer end;

    public Edge(Integer s, Integer e) {
        start = s;
        end = e;
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
}
