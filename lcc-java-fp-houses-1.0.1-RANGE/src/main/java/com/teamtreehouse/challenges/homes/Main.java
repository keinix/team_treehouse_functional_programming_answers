package com.teamtreehouse.challenges.homes;

import com.teamtreehouse.challenges.homes.model.HousingRecord;
import com.teamtreehouse.challenges.homes.service.HousingRecordService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    HousingRecordService service = new HousingRecordService();
    List<HousingRecord> records = service.getAllHousingRecords();

    List<String> stateCodes = getStateCodesFromRecords(records);
    System.out.println("Imperatively:");
    displayStateCodeMenuImperatively(stateCodes);
    System.out.println("Declaratively:");
    displayStateCodeMenuDeclaratively(stateCodes);

  }

  public static List<String> getStateCodesFromRecords(List<HousingRecord> records) {
    // TODO: Open a stream on records
    // TODO: Map the stream to the state code
    // TODO: Filter out any records without a state
    // TODO: There are duplicate state codes in the records, make sure we have a unique representation
    // TODO: Sort them alphabetically
    // TODO: Collect them into a new list.
    return records.stream()
      .map(HousingRecord::getState)
      .filter(state -> !state.isEmpty())
      .distinct()
      .sorted()
      .collect(Collectors.toList());
  }

  public static void displayStateCodeMenuImperatively(List<String> stateCodes) {
    for (int i = 0; i < stateCodes.size(); i++) {
      System.out.printf("%d. %s %n", i + 1, stateCodes.get(i));
    }
  }

  public static void displayStateCodeMenuDeclaratively(List<String> stateCodes) {
    // TODO: Use a range to display a numbered list of the states, starting at 1.
    IntStream.range(1, stateCodes.size() + 1)
      .mapToObj(i -> String.format("%d. %s", i, stateCodes.get(i - 1)))
      .forEach(System.out::println);


  }

  public static int getLowestHomeValueIndexImperatively(List<HousingRecord> records) {
    int lowest = -1;
    for (HousingRecord record : records) {
      int index = record.getCurrentHomeValueIndex();
      if (lowest == -1) {
        lowest = index;
      } else {
        if (index < lowest) {
          lowest = index;
        }
      }
    }
    return lowest;
  }

  public static OptionalInt getLowestHomeValueIndexDeclaratively(List<HousingRecord> records) {
    return records.stream()
      .mapToInt(HousingRecord::getCurrentHomeValueIndex)
      .min();
  }

  public static HousingRecord getHighestValueIndexHousingRecordImperatively(List<HousingRecord> records) {
    HousingRecord maxRecord = null;
    for (HousingRecord record : records) {
      if (maxRecord == null) {
        maxRecord = record;
      } else {
        if (record.getCurrentHomeValueIndex() > maxRecord.getCurrentHomeValueIndex()) {
          maxRecord = record;
        }
      }
    }
    return maxRecord;
  }

  public static Optional<HousingRecord> getHighestValueIndexHousingRecordDeclaratively(List<HousingRecord> records) {
    return records.stream()
      .max(Comparator.comparingInt(HousingRecord::getCurrentHomeValueIndex));
  }
}
