package com.teamtreehouse.challenges.homes;

import com.teamtreehouse.ast.Inspector;
import com.teamtreehouse.ast.predicates.Expressions;
import com.teamtreehouse.ast.predicates.Methods;
import com.teamtreehouse.ast.rules.Sourcery;
import com.teamtreehouse.challenges.homes.model.HousingRecord;
import com.teamtreehouse.teachingassistant.rules.Consoler;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MainTest {
  private static final String MIN_METHOD_NAME = "getLowestHomeValueIndexDeclaratively";
  private static final String MAX_METHOD_NAME = "getHighestValueIndexHousingRecordDeclaratively";
  private static final String STATE_CODE_METHOD_NAME = "getStateCodesFromRecords";
  private static final String DISPLAY_MENU_METHOD_NAME = "displayStateCodeMenuDeclaratively";
  private static final String ARGENTINA_METHOD_NAME = "getArgentinaPriceConverter";
  private static final String CONVERTER_METHOD_NAME = "createPriceConverter";
  private Inspector inspector;
  private List<HousingRecord> reductionRecords;
  private List<HousingRecord> rangeRecords;
  private List<HousingRecord> compositionRecords;

  @ClassRule
  public static Sourcery sourcery = new Sourcery();

  @Rule
  public Consoler consoler = new Consoler();

  @Before
  public void setUp() throws Exception {
    inspector = sourcery.inspectorFor(Main.class);
    reductionRecords = Arrays.asList(
            new HousingRecord("1","","", "",
                    "","99","","","","","",
                    "", "", "","",""),

            new HousingRecord("2","","", "",
                    "","100","","","","","",
                    "", "", "","","")
    );
    rangeRecords = Arrays.asList(
      new HousingRecord("","","", "WY",
        "","","","","","","",
        "", "", "","",""),
      new HousingRecord("","","", "AZ",
        "","","","","","","",
        "", "", "","",""),
      new HousingRecord("","","", "AZ",
        "","","","","","","",
        "", "", "","",""),
      new HousingRecord("","","", "AZ",
        "","","","","","","",
        "", "", "","",""),
      new HousingRecord("","","", "",
      "","","","","","","",
      "", "", "","","")
    );
    compositionRecords = Arrays.asList(
      new HousingRecord("","","First", "",
        "","1","","","","","",
        "", "", "","",""),
      new HousingRecord("","","Second", "",
        "","2","","","","","",
        "", "", "","","")
      );
  }

  @Test
  public void pricingConverterRemovedFixMe() throws Exception {
    Locale korean = Locale.KOREA;
    BigDecimal rate = BigDecimal.ONE;

    Function<Integer, String> converter = Main.createPriceConverter(korean, rate);
    String actual = converter.apply(1);

    String msg = String.format("Make sure you modify the '%s' method to be the dynamic version of '%s' ",
      CONVERTER_METHOD_NAME,
      ARGENTINA_METHOD_NAME);
    assertThat(msg, actual, not(containsString("FIX")));
  }

  @Test
  public void creatingPriceConverterWorksForAllCountries() throws Exception {
    Locale korean = Locale.ITALY;
    BigDecimal rate = BigDecimal.ONE;

    Function<Integer, String> converter = Main.createPriceConverter(korean, rate);
    String actual = converter.apply(1);

    String msg = String.format("Hmm...double check your '%s' method.  I'm getting different results",
      CONVERTER_METHOD_NAME);
    assertThat(msg, actual, containsString("1,00 (Euro)"));
  }

  @Test
  public void argentinaRecordsAreFormattedAndConverted() throws Exception {
    String statement = Main.getPriceConversionForRecord(
      compositionRecords.get(0),
      Main.getArgentinaPriceConverter());

    String msg = String.format("Hmmm..it seems your code in '%s' isn't returning the expected results.  Are you using the supplied functions?",
      ARGENTINA_METHOD_NAME);
    assertThat(statement, is("First is $1.00 (USD) which is $15,48 (Argentine Peso)"));
  }

  @Test
  public void aRangeIsUsed() throws Exception {
    String msg = String.format("Please make sure to use a range method in your '%s' method",
      DISPLAY_MENU_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(DISPLAY_MENU_METHOD_NAME))
      .withExpression(Expressions.isMethodCall()
        .and(Expressions.containing("range")))
      )
    );
  }

  @Test
  public void menuIsInclusive() throws Exception {
    // Using two states because could be a range problem
    List<String> stateCodes = Arrays.asList("HI", "MOM");
    consoler.addExpectedPrompt("1");
    consoler.addExpectedPrompt("2");

    Main.displayStateCodeMenuDeclaratively(stateCodes);

    String msg = String.format("Ensure your range is inclusive of the last value in your '%s' method",
      DISPLAY_MENU_METHOD_NAME);
    consoler.assertExpected(msg);
  }

  @Test
  public void menuStartsAt1() throws Exception {
    // Using two states because could be a range problem
    List<String> stateCodes = Arrays.asList("HI", "MOM");
    consoler.addExpectedPrompt("1");

    Main.displayStateCodeMenuDeclaratively(stateCodes);

    String msg = String.format("Make sure you start with 1 in your '%s' method",
      DISPLAY_MENU_METHOD_NAME);
    consoler.assertExpected(msg);
    consoler.assertNotPresent(msg, "0");
  }

  @Test
  public void stateCodesUseDistinctMethod() throws Exception {
    String msg = String.format("Make sure to call the distinct method on your stream in '%s'",
      STATE_CODE_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(STATE_CODE_METHOD_NAME))
      .withExpression(Expressions.isMethodCall()
        .and(Expressions.containing("distinct")))
    ));
  }

  @Test
  public void stateCodesAreSorted() throws Exception {
    List<String> actual = Main.getStateCodesFromRecords(rangeRecords);

    String msg = String.format("Did you forget to sort the stream in the method '%s'",
      STATE_CODE_METHOD_NAME);
    // This is IsIterableContainingInOrder.contains
    assertThat(msg, actual, contains("AZ", "WY"));
  }

  @Test
  public void stateCodesIgnoreBlanks() throws Exception {
    List<String> actual = Main.getStateCodesFromRecords(rangeRecords);

    String msg = String.format("Make sure you to filter out the empty state codes in your '%s' method",
      STATE_CODE_METHOD_NAME);
    assertFalse(msg, actual.size() == 3);
  }

  @Test
  public void minimumHomeIndexReturned() throws Exception {
    OptionalInt actual = Main.getLowestHomeValueIndexDeclaratively(reductionRecords);

    String msg = String.format("It seems like your method '%s' is not returning the correct value.  You can validate using the imperative results.",
            MIN_METHOD_NAME);
    assertTrue(msg, actual.isPresent());
    assertEquals(msg,99, actual.getAsInt());
  }

  @Test
  public void usesMapToIntToGetMinimum() throws Exception {
    String msg = String.format("Make sure you use the mapToInt method to convert your stream in method '%s'",
            MIN_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
            .withMethod(Methods.named(MIN_METHOD_NAME))
            .withExpression(Expressions.isMethodCall()
                    .and(Expressions.containing("mapToInt")))
    ));
  }

  @Test
  public void maximumHousingRecordReturned() throws Exception {
    Optional<HousingRecord> actual = Main.getHighestValueIndexHousingRecordDeclaratively(reductionRecords);

    String msg = String.format("The method '%s', is returning the wrong value.  You can validate using the imperative results.",
      MAX_METHOD_NAME);
    assertTrue(msg, actual.isPresent());
    assertEquals(msg, 100, actual.get().getCurrentHomeValueIndex());
  }

  @Test
  public void usesMaxToGatherHousingRecord() throws Exception {
    String msg = String.format("You should use the max method off of the stream in '%s'",
      MAX_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(MAX_METHOD_NAME))
      .withExpression(Expressions.isMethodCall().and(Expressions.containing("max")))
      )
    );
  }
}