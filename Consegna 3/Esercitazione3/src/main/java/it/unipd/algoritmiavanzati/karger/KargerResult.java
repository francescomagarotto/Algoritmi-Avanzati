package it.unipd.algoritmiavanzati.karger;

//custom return type for Karger
public class KargerResult {
  public int min;
  public long discoveryTime;

  public KargerResult(int m, long d) {
    min = m;
    discoveryTime = d;
  }
}