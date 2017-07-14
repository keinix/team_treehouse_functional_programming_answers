package com.teamtreehouse.challenges.homes;

import com.teamtreehouse.challenges.homes.model.HousingRecord;
import com.teamtreehouse.challenges.homes.service.HousingRecordService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

public class Main {
  public static void main(String[] args) {
    HousingRecordService service = new HousingRecordService();
    List<HousingRecord> records = service.getAllHousingRecords();
    System.out.printf("There are %d housing records. %n %n", records.size());

    System.out.println("Lowest home value index:");
    int lowest = getLowestHomeValueIndexImperatively(records);
    System.out.println("Imperatively:  " + lowest);
    OptionalInt lowestDeclaratively = getLowestHomeValueIndexDeclaratively(records);
    System.out.println("Declaratively:  " + lowestDeclaratively);

    System.out.printf("%n%nHighest housing record based on current value index:%n");
    HousingRecord record = getHighestValueIndexHousingRecordImperatively(records);
    System.out.println("Imperatively:  " + record);
    Optional<HousingRecord> recordDeclaratively = getHighestValueIndexHousingRecordDeclaratively(records);
    System.out.println("Declaratively:  " + recordDeclaratively);
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
    // TODO: Create a stream off the records
    // TODO: Map the stream to an IntStream on `HousingRecord.getCurrentHomeValueIndex` to expose new methods
    // TODO: Return the minimum value from the stream.  It should be an optional because records could be empty.
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
    // TODO: Create a stream off of the records
    // TODO: Find and return the maximum using a Comparator that compares the current home value index
    return records.stream()
      .max(Comparator.comparingInt(HousingRecord::getCurrentHomeValueIndex));
  }
}
