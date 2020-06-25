package it.unipd.algoritmiavanzati.karger;

//custom return type for Karger
public class KargerResult {
  public int min;
  public long discoveryTime;
  public long totalTime;

  public KargerResult(int min, long discoveryTime, long totalTime) {
    this.min = min;
    this.discoveryTime = discoveryTime;
    this.totalTime = totalTime;
  }
}