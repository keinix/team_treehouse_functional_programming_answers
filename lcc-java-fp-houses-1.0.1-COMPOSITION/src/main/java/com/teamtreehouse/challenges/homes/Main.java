package com.teamtreehouse.challenges.homes;

import com.teamtreehouse.challenges.homes.model.HousingRecord;
import com.teamtreehouse.challenges.homes.service.HousingRecordService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    HousingRecordService service = new HousingRecordService();
    List<HousingRecord> records = service.getAllHousingRecords();

    getPricesConvertedForArgentina(records)
      .forEach(System.out::println);
  }

  public static List<String> getPricesConvertedForArgentina(List<HousingRecord> records) {
    Locale argLocale = Locale.forLanguageTag("es-AR");
    // This fluctuates and is hardcoded temporarily
    BigDecimal argPesoToUsdRate = new BigDecimal("15.48");

    // TODO: These functions are in working order, but separately they don't match the single Function signature expected
    Function<Integer, BigDecimal> usdToArgentinePesoConverter = usd -> argPesoToUsdRate.multiply(new BigDecimal(usd));

    Function<BigDecimal, String> argentineCurrencyFormatter = price -> {
      Currency currentCurrency = Currency.getInstance(argLocale);
      NumberFormat currencyFormatter =
        NumberFormat.getCurrencyInstance(argLocale);
      return String.format("%s (%s)",
        currencyFormatter.format(price),
        currentCurrency.getDisplayName()
      );
    };

    Function<Integer, String> convertAndFormatPrice
      = usdToArgentinePesoConverter.andThen(argentineCurrencyFormatter);

    return getConvertedPriceStatements(records,
      // TODO: Correct this.  It should to convert AND format.  However, there is room parameter wise for only one Function...hmmm.
      convertAndFormatPrice
    );
  }

  public static String getPriceConversionForRecord(HousingRecord record, Function<Integer, String> priceConverter) {
    NumberFormat usFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    return String.format("%s is %s (USD) which is %s",
      record.getRegionName(),
      usFormatter.format(record.getCurrentHomeValueIndex()),
      priceConverter.apply(record.getCurrentHomeValueIndex())
    );
  }

  public static List<String> getConvertedPriceStatements(List<HousingRecord> records, Function<Integer, String> priceConverter) {
    return records.stream()
      .sorted(Comparator.comparingInt(HousingRecord::getCurrentHomeValueIndex))
      .map(record -> getPriceConversionForRecord(record, priceConverter))
      .collect(Collectors.toList());
  }

  public static List<String> getStateCodesFromRecords(List<HousingRecord> records) {
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
    IntStream.rangeClosed(1, stateCodes.size())
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
