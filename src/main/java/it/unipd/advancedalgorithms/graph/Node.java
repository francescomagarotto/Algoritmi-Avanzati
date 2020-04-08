package it.unipd.advancedalgorithms.graph;
public class Node implements Comparable<Node> {
  private Integer id;
  private Integer pi;
  private Integer key;

  public Node(int id, int pi, int key) {
    this.id = id;
    this.key = key;
    this.pi = pi;
  }

  public int compare(Node a, Node b) {
    return a.getKey() - b.getKey();
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @return the pi
   */
  public int getPi() {
    return pi;
  }

  /**
   * @return the key
   */
  public int getKey() {
    return key;
  }

  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + (this.key != null ? this.key.hashCode() : 0);
    return hash;
  }

  public boolean equals(Object o) {
    if (o instanceof Node) {
      Node n = (Node) o;
      return this.id == n.getId();
    }
    return false;
  }

  public int compareTo(Node n) {
    return this.id.compareTo(n.getId());
  }

  public void setPi(int pi) {
    this.pi = pi;
  }

  public void setKey(int key) {
    this.key = key;
  }
}