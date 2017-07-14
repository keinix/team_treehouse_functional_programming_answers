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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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
  private Inspector inspector;
  private List<HousingRecord> reductionRecords;
  private List<HousingRecord> rangeRecords;

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