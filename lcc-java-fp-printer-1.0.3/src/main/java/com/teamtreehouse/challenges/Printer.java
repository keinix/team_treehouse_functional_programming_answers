package com.teamtreehouse.challenges;

import java.util.Arrays;
import java.util.List;

public class Printer {

  public static void main(String[] args) {
    List<String> chipmunks = Arrays.asList("Alvin", "Simon", "Theodore");
    System.out.println("Imperative results:");
    printChipmunksImperatively(chipmunks);
    System.out.println("Declarative results:");
    printChipmunksDeclaratively(chipmunks);
  }

  public static void printChipmunksImperatively(List<String> chipmunks) {
    for (String chipmunk : chipmunks) {
      System.out.printf("%s! %n", chipmunk);
    }
  }

  public static void printChipmunksDeclaratively(List<String> chipmunks) {
    // TODO: Print out each of the chipmunks using the forEach method on Iterable
    chipmunks.forEach(chipmunk -> System.out.printf("%s! %n", chipmunk));

  }


}
