package it.unipd.algoritmiavanzati.karger;

//custom return type for Karger
public class KargerResult {
  public int min;
  public long discoveryTime;
  public long totalTime;
  public double fullContractionMeanTime;
  public KargerResult(int min, long discoveryTime, long totalTime, double fullContractionMeanTime) {
    this.min = min;
    this.discoveryTime = discoveryTime;
    this.totalTime = totalTime;
    this.fullContractionMeanTime = fullContractionMeanTime;
  }
}