package com.teamtreehouse.challenges;

import java.util.Arrays;
import java.util.List;

public class Main {

  public static void whisper(String sentence) {
    System.out.println(sentence.toLowerCase().replace('!', '.'));
  }


  public static void main(String[] args) {
    List<String> sentences = Arrays.asList(
      "A little bit softer now!",
      "CARELESS!!!",
      "When I'm out walkin' I strut my stuff!",
      "Justify MY LOVE!",
      "IT'S OH SO QUIET!!!"
    );
    System.out.println("Imperatively whispering:");
    whisperSentencesImperatively(sentences);
    System.out.println("-------------------------");
    System.out.println("Declaratively whispering:");
    whisperSentencesDeclaratively(sentences);
  }

  public static void whisperSentencesImperatively(List<String> sentences) {
    for (String sentence : sentences) {
      Main.whisper(sentence);
    }
  }

  public static void whisperSentencesDeclaratively(List<String> sentences) {
    sentences.forEach(Main::whisper);
    // TODO: Use a method reference for Main.whisper in the forEach method of sentences

  }


}
