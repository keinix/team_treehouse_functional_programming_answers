package com.teamtreehouse.challenges.highscores;

import com.teamtreehouse.challenges.highscores.model.Score;
import com.teamtreehouse.challenges.highscores.service.ScoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    ScoreService service = new ScoreService();
    List<Score> scores = service.getAllScores();
    System.out.printf("There are %d total scores. %n %n", scores.size());
    System.out.println("Imperatively - First 3 'Nintendo Entertainment System' Scores");
    List<Score> nintendoScores = getFirstThreeNintendoScoresImperatively(scores);
    nintendoScores.forEach(System.out::println);
    System.out.println("Declaratively - First 3 'Nintendo Entertainment System' Scores");
    // NOTE:  Reassigning here
    nintendoScores = getFirstThreeNintendoScoresDeclaratively(scores);
    nintendoScores.forEach(System.out::println);
  }

  public static List<Score> getFirstThreeNintendoScoresImperatively(List<Score> scores) {
    List<Score> result = new ArrayList<>();
    // Four score and 7 years ago....#dadjoke
    for (Score score : scores) {
      if (score.getPlatform().equals("Nintendo Entertainment System")) {
         result.add(score);
         if (result.size() >= 3) {
           break;
         }
      }
    }
    return result;
  }

  public static List<Score> getFirstThreeNintendoScoresDeclaratively(List<Score> scores) {
    // TODO: Filter the scores stream to be of platform 'Nintendo Entertainment System'
    // TODO: Limit it to 3 scores
    // TODO: Return a newly collected list using the collect method
    return scores.stream()
      .filter(score -> score.getPlatform().equals("Nintendo Entertainment System"))
      .limit(3)
      .collect(Collectors.toList());
  }

  public static void printBurgerTimeScoresImperatively(List<Score> scores) {
    for (Score score : scores) {
      if (score.getGame().equals("Burger Time") && score.getAmount() >= 20000) {
        System.out.println(score);
      }
    }
  }

  public static void printBurgerTimeScoresDeclaratively(List<Score> scores) {
    scores.stream()
            .filter(score -> score.getGame().equals("Burger Time"))
            .filter(score -> score.getAmount() >= 20000)
            .forEach(System.out::println);
  }

}
