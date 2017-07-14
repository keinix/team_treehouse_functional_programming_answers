package com.teamtreehouse.challenges.homes.model;

public class HousingRecord {

  private final String regionID;
  private final String date;
  private final String regionName;
  private final String state;
  private final String sizeRank;
  private final String zhvi;
  private final String moM;
  private final String qoQ;
  private final String yoY;
  private final String fiveYear;
  private final String tenYear;
  private final String peakMonth;
  private final String peakQuarter;
  private final String peakZHVI;
  private final String pctFallFromPeak;
  private final String lastTimeAtCurrZHVI;

  public HousingRecord(String regionID,
                       String date,
                       String regionName,
                       String state,
                       String sizeRank,
                       String zhvi,
                       String moM,
                       String qoQ,
                       String yoY,
                       String fiveYear,
                       String tenYear,
                       String peakMonth,
                       String peakQuarter,
                       String peakZHVI,
                       String pctFallFromPeak,
                       String lastTimeAtCurrZHVI) {

    this.regionID = regionID;
    this.date = date;
    this.regionName = regionName;
    this.state = state;
    this.sizeRank = sizeRank;
    this.zhvi = zhvi;
    this.moM = moM;
    this.qoQ = qoQ;
    this.yoY = yoY;
    this.fiveYear = fiveYear;
    this.tenYear = tenYear;
    this.peakMonth = peakMonth;
    this.peakQuarter = peakQuarter;
    this.peakZHVI = peakZHVI;
    this.pctFallFromPeak = pctFallFromPeak;
    this.lastTimeAtCurrZHVI = lastTimeAtCurrZHVI;
  }

  public int getSizeRank() {
    return Integer.parseInt(sizeRank);
  }

  public int getCurrentHomeValueIndex() {
    return Integer.parseInt(zhvi);
  }

  public int getPeakHomeValueIndex() {
    return Integer.parseInt(peakZHVI);
  }

  public String getRegionName() {
    return regionName;
  }

  public String getState() {
    return state;
  }

  @Override
  public String toString() {
    return "HousingRecord{" +
      "regionName='" + regionName + '\'' +
      ", state='" + state + '\'' +
      ", zhvi='" + zhvi + '\'' +
      '}';
  }
}
