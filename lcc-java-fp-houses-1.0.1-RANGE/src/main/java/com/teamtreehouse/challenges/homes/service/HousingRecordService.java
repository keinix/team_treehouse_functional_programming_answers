package com.teamtreehouse.challenges.homes.service;

import com.teamtreehouse.challenges.homes.model.HousingRecord;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.commons.csv.CSVFormat.RFC4180;

public class HousingRecordService {
  private CSVParser getParser() throws IOException {
    URL resourceUrl = getClass().getClassLoader().getResource("Metro_Zhvi_Summary_AllHomes.csv");
    return CSVParser.parse(resourceUrl, Charset.defaultCharset(), RFC4180.withHeader());
  }

  private Stream<CSVRecord> getCSVStream() throws IOException {
    return StreamSupport.stream(getParser().spliterator(), false);
  }

  private HousingRecord recordToScore(CSVRecord record) {
    return new HousingRecord(
            record.get("RegionID"),
            record.get("Date"),
            record.get("RegionName"),
            record.get("State"),
            record.get("SizeRank"),
            record.get("Zhvi"),
            record.get("MoM"),
            record.get("QoQ"),
            record.get("YoY"),
            record.get("5Year"),
            record.get("10Year"),
            record.get("PeakMonth"),
            record.get("PeakQuarter"),
            record.get("PeakZHVI"),
            record.get("PctFallFromPeak"),
            record.get("LastTimeAtCurrZHVI")
    );
  }

  public Stream<HousingRecord> getHousingRecordStream() throws IOException {
    return getCSVStream()
            .map(this::recordToScore);
  }

  public List<HousingRecord> getAllHousingRecords() {
    try {
      return getHousingRecordStream().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
