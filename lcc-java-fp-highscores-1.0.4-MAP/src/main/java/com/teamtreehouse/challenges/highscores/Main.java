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
    System.out.println("Imperatively - First 5 over 100,000 Players");
    List<String> players = getFirstFiveAmazingPlayersImperatively(scores);
    players.forEach(System.out::println);
    System.out.println();
    System.out.println("Declaratively - First 5 over 100,000 Players");
    // NOTE: Reassignment
    players = getFirstFiveAmazingPlayersDeclaratively(scores);
    players.forEach(System.out::println);
  }

  public static List<String> getFirstFiveAmazingPlayersImperatively(List<Score> scores) {
    List<String> players = new ArrayList<>();
    for (Score score : scores) {
      if (score.getAmount() > 100000) {
        players.add(score.getPlayer());
        if (players.size() >= 5) {
          break;
        }
      }
    }
    return players;
  }

  public static List<String> getFirstFiveAmazingPlayersDeclaratively(List<Score> scores) {
    // TODO: Filter where amount is greater than 100,000
    // TODO: Map to the player's name
    // TODO: Limit to 5
    // TODO: And collect those strings and return them
    return scores.stream()
      .filter(score -> score.getAmount() > 100000)
      .map(Score::getPlayer)
      .limit(5)
      .collect(Collectors.toList());
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
