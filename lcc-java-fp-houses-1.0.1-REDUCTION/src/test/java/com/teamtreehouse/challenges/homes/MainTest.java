package com.teamtreehouse.challenges.homes;

import com.teamtreehouse.ast.Inspector;
import com.teamtreehouse.ast.predicates.Expressions;
import com.teamtreehouse.ast.predicates.Methods;
import com.teamtreehouse.ast.rules.Sourcery;
import com.teamtreehouse.challenges.homes.model.HousingRecord;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {
  private static final String MIN_METHOD_NAME = "getLowestHomeValueIndexDeclaratively";
  private static final String MAX_METHOD_NAME = "getHighestValueIndexHousingRecordDeclaratively";
  private Inspector inspector;

  @ClassRule
  public static Sourcery sourcery = new Sourcery();
  private List<HousingRecord> reductionRecords;

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