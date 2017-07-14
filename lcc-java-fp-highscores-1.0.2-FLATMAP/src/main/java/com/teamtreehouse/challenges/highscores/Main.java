package com.teamtreehouse.challenges.highscores;

import com.teamtreehouse.challenges.highscores.model.Score;
import com.teamtreehouse.challenges.highscores.service.ScoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    ScoreService service = new ScoreService();
    List<Score> scores = service.getAllScores();
    System.out.printf("There are %d total scores. %n %n", scores.size());
    System.out.println("Imperatively - Word count of all game titles");
    Map<String, Long> wordCounts = getWordCountFromGameTitlesImperatively(scores);
    wordCounts.forEach((key, value) -> System.out.printf("%s occurs %d times %n", key, value));
    System.out.println();
    System.out.println("Declaratively - Word count of all game titles");
    // NOTE: Reassignment
    wordCounts = getWordCountFromGameTitlesDeclaratively(scores);
    wordCounts.forEach((key, value) -> System.out.printf("%s occurs %d times %n", key, value));
  }

  public static Map<String,Long> getWordCountFromGameTitlesImperatively(List<Score> scores) {
    Map<String, Long> wordByCount = new HashMap<>();
    for (Score score : scores) {
      String title = score.getGame().toLowerCase();
      String[] words = title.split("\\W+");
      for (String word : words) {
        if (word.isEmpty()) {
          continue;
        }
        long count = wordByCount.getOrDefault(word, 0L);
        wordByCount.put(word, ++count);
      }
    }
    return wordByCount;
  }

  public static Map<String,Long> getWordCountFromGameTitlesDeclaratively(List<Score> scores) {
    // TODO: Open a stream on scores
    // TODO: Map the stream to the game title
    // TODO: Map that to it's lowercase version, so things are case insensitive
    // TODO: Map that to a new String array of each word in the title (See imperative implementation)
    // TODO: Flatten those words into the stream
    // TODO: Collect a grouping of the word to count
    return scores.stream()
      .map(Score::getGame)
      .map(String::toLowerCase)
      .map(words -> words.split("\\W+"))
      .flatMap(Stream::of)
      .collect(Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
      ));
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
