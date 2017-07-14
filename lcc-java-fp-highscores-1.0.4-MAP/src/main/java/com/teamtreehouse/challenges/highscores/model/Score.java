package com.teamtreehouse.challenges.highscores.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Score {
  private static final NumberFormat usFormat = NumberFormat.getInstance(Locale.US);
  private final String game;
  private final String measuringUnit;
  private final String amountAsString;
  private final String player;
  private final String platform;

  public Score(String game, String measuringUnit, String platform, String amountAsString, String player) {
    this.game = game;
    this.measuringUnit = measuringUnit;
    this.amountAsString = amountAsString;
    this.platform = platform;
    this.player=  player;
  }

  public String getGame() {
    return game;
  }

  public String getMeasuringUnit() {
    return measuringUnit;
  }

  public long getAmount() {
    try {
      return usFormat.parse(amountAsString).longValue();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public String getAmountAsString() {
    return amountAsString;
  }

  public String getPlayer() {
    return player;
  }

  public String getPlatform() {
    return platform;
  }

  @Override
  public String toString() {
    return "Score{" +
            "game='" + game + '\'' +
            ", measuringUnit='" + measuringUnit + '\'' +
            ", amountAsString='" + amountAsString + '\'' +
            ", player='" + player + '\'' +
            ", platform='" + platform + '\'' +
            '}';
  }
}
