public class Node {
  int id;
  int pi;
  int key;

  public Node(int id) {
    this.id = id;
    this.pi = Integer.MIN_VALUE;
    this.key = Integer.MIN_VALUE;
  }

  public Node(int id, int pi, int key) {
    this.id = id;
    this.pi = pi;
    this.key = key;
  }


}
