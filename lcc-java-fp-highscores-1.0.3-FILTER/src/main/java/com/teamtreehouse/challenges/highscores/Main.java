package com.teamtreehouse.challenges.highscores;

import com.teamtreehouse.challenges.highscores.model.Score;
import com.teamtreehouse.challenges.highscores.service.ScoreService;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    ScoreService service = new ScoreService();
    List<Score> scores = service.getAllScores();
    System.out.printf("There are %d total scores. %n %n", scores.size());
    System.out.println("Imperative Burger Time Scores Greater than 20,000");
    printBurgerTimeScoresImperatively(scores);
    System.out.println("Declarative Burger Time Scores Greater than 20,000");
    printBurgerTimeScoresDeclaratively(scores);
  }


  public static void printBurgerTimeScoresImperatively(List<Score> scores) {
    for (Score score : scores) {
      if (score.getGame().equals("Burger Time") && score.getAmount() >= 20000) {
        System.out.println(score);
      }
    }
  }

  public static void printBurgerTimeScoresDeclaratively(List<Score> scores) {
    // TODO: Open a stream off of scores and filter "Burger Time" scores that are larger than 20,000
    // TODO: Print out those scores using the forEach method off of the Stream
    scores.stream()
      .filter(score -> score.getGame().equals("Burger Time"))
      .filter(score -> score.getAmount() > 20000L)
      .forEach(System.out::println);



  }

}
