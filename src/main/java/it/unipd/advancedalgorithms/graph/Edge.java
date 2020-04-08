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

  public Edge() {}

  public Integer getStart() {
    return start;
  }

  public Integer getEnd() {
    return end;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public void setEnd(Integer end) {
    this.end = end;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }
}
