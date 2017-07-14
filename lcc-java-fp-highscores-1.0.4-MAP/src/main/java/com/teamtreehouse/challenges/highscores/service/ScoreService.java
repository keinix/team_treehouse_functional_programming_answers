package com.teamtreehouse.challenges.highscores.service;

import com.teamtreehouse.challenges.highscores.model.Score;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.commons.csv.CSVFormat.RFC4180;

public class ScoreService {

  private CSVParser getParser() throws IOException {
    URL resourceUrl = getClass().getClassLoader().getResource("scores.csv");
    return CSVParser.parse(resourceUrl, Charset.defaultCharset(), RFC4180);
  }

  private Stream<CSVRecord> getCSVStream() throws IOException {
    return StreamSupport.stream(getParser().spliterator(), false);
  }

  private Score recordToScore(CSVRecord record) {
    return new Score(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4));
  }

  public static Predicate<CSVRecord> columnContains(int column, String text) {
    return record -> record.get(column).contains(text);
  }

  public void saveSubset(String filename, Predicate<CSVRecord> checker) throws IOException {
    try (Writer out = new BufferedWriter(new FileWriter(filename))) {
      CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT);
      getCSVStream()
              .filter(checker)
              .forEach(record -> {
                try {
                  printer.printRecord(record);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
    }
  }

  public Stream<Score> getScoreStream() throws IOException {
    return getCSVStream()
            .map(this::recordToScore);
  }

  public List<Score> getAllScores() {
    try {
      return getScoreStream().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
