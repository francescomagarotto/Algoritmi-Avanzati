package it.unipd.advancedalgorithms.graph;


import java.util.ArrayList;
import java.util.Comparator;

public class Heap {

  private int[] tree;
  private int size;
  private Comparator<Integer> comparator;

  public Heap(int maxsize, Comparator<Integer> comparator) {
    tree = new int[maxsize];
    size = 0;
    this.comparator = comparator;
  }

  public void add(int id) {
    tree[size] = id;
    fixHeap(size);
    size++;
  }

  public int poll() {
    int min = tree[0];
    size--;
    tree[0] = tree[size];
    heapify(0);
    return min;
  }

  public void update(int id) {
    for (int i = 0; i < size; i++) {
      if (tree[i] == id) {
        fixHeap(i);
      }
    }
  }

  private void heapify(int index)
  {
    if (!isLeaf(index)) {
      if (comparator.compare(tree[index], tree[leftChild(index)]) > 0 ||
          comparator.compare(tree[index], tree[rightChild(index)]) > 0) {

        if (comparator.compare(tree[leftChild(index)], tree[rightChild(index)]) < 0) {
          swap(index, leftChild(index));
          heapify(leftChild(index));
        } else {
          swap(index, rightChild(index));
          heapify(rightChild(index));
        }
      }
    }
  }

  private void fixHeap(int index) {
    while (index > 0 && comparator.compare(tree[index], tree[parent(index)]) < 0) {
      swap(index, parent(index));
      index = parent(index);
    }
  }

  private int parent(int index) {
    return (index - 1) / 2;
  }

  private int leftChild(int index)
  {
    return (2 * index) + 1;
  }

  private int rightChild(int index)
  {
    return (2 * index) + 2;
  }

  private boolean isLeaf(int index)
  {
    return index >= (size / 2) && index <= size;
  }

  private void swap(int index1, int index2) {
    int tmp = tree[index1];
    tree[index1] = tree[index2];
    tree[index2] = tmp;
  }

}
