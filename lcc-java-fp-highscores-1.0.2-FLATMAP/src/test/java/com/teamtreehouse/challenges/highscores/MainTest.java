package com.teamtreehouse.challenges.highscores;

import com.teamtreehouse.ast.Inspector;
import com.teamtreehouse.ast.predicates.Expressions;
import com.teamtreehouse.ast.predicates.Methods;
import com.teamtreehouse.ast.rules.Sourcery;
import com.teamtreehouse.challenges.highscores.model.Score;
import com.teamtreehouse.teachingassistant.rules.Consoler;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;


public class MainTest {
  public static final String METHOD_NAME = "printBurgerTimeScoresDeclaratively";
  public static final String MAP_METHOD_NAME = "getFirstFiveAmazingPlayersDeclaratively";
  public static final String FLATMAP_METHOD_NAME = "getWordCountFromGameTitlesDeclaratively";
  private Inspector inspector;

  @ClassRule
  public static Sourcery sourcery = new Sourcery();

  @Rule
  public Consoler consoler = new Consoler();
  private List<Score> filterScores;
  private List<Score> collectScores;
  private List<Score> mapScores;
  private List<Score> flatMapScores;


  @Before
  public void setUp() throws Exception {
    inspector = sourcery.inspectorFor(Main.class);
    filterScores = Arrays.asList(
            new Score("Burger Time", "", "", "1", "NOT_ENOUGH_POINTS"),
            new Score("Burger Time", "", "", "20,001", "VALID_1"),
            new Score("Dig Dug", "", "", "20,001", "WRONG_NAME")
    );
    collectScores = Arrays.asList(
            new Score("", "", "Nintendo Entertainment System", "", "FIRST"),
            new Score("", "", "Nintendo Entertainment System", "", "SECOND"),
            new Score("", "", "Sega Genesis", "", "WRONG"),
            new Score("", "", "Nintendo Entertainment System", "", "THIRD"),
            new Score("", "", "Nintendo Entertainment System", "", "TOO_MANY")

    );
    mapScores = Arrays.asList(
      new Score("", "", "", "100,001", "Alice"),
      new Score("", "", "", "1", "NOT_ENOUGH"),
      new Score("", "", "", "120,000", "Bob"),
      new Score("", "", "", "130,000", "Carla"),
      new Score("", "", "", "130,000", "Dwayne"),
      new Score("", "", "", "140,000", "Elizabeth"),
      new Score("", "", "", "140,000", "TOO_MANY")
    );

    flatMapScores = Arrays.asList(
      new Score("Super Mario Brothers", "","","", ""),
      new Score("Super Mario Brothers 2", "","","", ""),
      new Score("super mario brothers 3", "","","", ""),
      new Score("Super Mario World", "","","", "")
    );
  }

  @Test
  public void wordCountReturnsCorrectData() throws Exception {
    Map<String, Long> actual = Main.getWordCountFromGameTitlesDeclaratively(flatMapScores);

    String msg = String.format("Hmmm...the result from your '%s' method isn't what was expected",
      FLATMAP_METHOD_NAME);
    assertThat(msg, actual, allOf(
      hasEntry("super", 4L),
      hasEntry("world", 1L),
      hasEntry("brothers", 3L)
    ));
  }

  @Test
  public void wordCountsUseFlatMap() throws Exception {
    String msg = String.format("Please use the flatMap method to stream the stream of words in your '%s' method",
      FLATMAP_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(FLATMAP_METHOD_NAME))
      .withExpression(Expressions.isMethodCall()
        .and(Expressions.containing("flatMap")))
    ));
  }

  @Test
  public void wordCountIsCollectedUsingGrouping() throws Exception {
    String msg = String.format("Please ensure you use the collect method with the groupingBy collector in your '%s' method",
      FLATMAP_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(FLATMAP_METHOD_NAME))
      .withExpression(Expressions.isMethodCall()
        .and(Expressions.containing("groupingBy")))
    ));
  }

  @Test
  public void mapIsUsedWithMethodReference() throws Exception {
    String msg = String.format("Make sure you call the map method in the '%s' method.",
      MAP_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(MAP_METHOD_NAME))
      .withExpression(Expressions.isMethodCall().and(Expressions.containing("map")))
    ));

    msg = String.format(
      "Please make use of the method reference in your '%s' method to get the player",
      MAP_METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withMethod(Methods.named(MAP_METHOD_NAME))
      .withExpression(Expressions.isMethodReference().and(
        Expressions.containing("getPlayer")))
    ));

  }

  @Test
  public void playersNeedToBeFilteredByPoints() throws Exception {
    List<String> players = Main.getFirstFiveAmazingPlayersDeclaratively(mapScores);

    assertThat("Ensure you have filtered to players with scores greater than 100,000 points",
      players,
      not(hasItem("NOT_ENOUGH")));
  }

  @Test
  public void playersNeedToBeLimitedTo5() throws Exception {
    List<String> players = Main.getFirstFiveAmazingPlayersDeclaratively(mapScores);

    assertThat("Ensure you have limited the returned players to 5",
      players.size(),
      is(5)
    );
  }


  @Test
  public void properPlayersReturned() throws Exception {
    List<String> players = Main.getFirstFiveAmazingPlayersDeclaratively(mapScores);

    assertThat("Hmmm..you seem to be returning the wrong players",
      players,
      hasItems("Alice", "Bob", "Carla", "Dwayne", "Elizabeth")
    );
  }

  @Test
  public void limitsCollectedResults() throws Exception {
    List<Score> actualScores = Main.getFirstThreeNintendoScoresDeclaratively(collectScores);

    assertThat("Ensure you are limiting the stream", actualScores.size(), is(3));
  }

  @Test
  public void collectedOnlyNESGames() throws Exception {
    List<Score> actualScores = Main.getFirstThreeNintendoScoresDeclaratively(collectScores);

    for (Score score : actualScores) {
      assertThat("Ensure you are only gathering 'Nintendo Entertainment System' scores",
              score.getPlatform(),
              is("Nintendo Entertainment System"));

    }
  }

  @Test
  public void collectedTheFirstThree() throws Exception {
    List<Score> actualScores = Main.getFirstThreeNintendoScoresDeclaratively(collectScores);

    List<String> players = actualScores.stream()
            .map(Score::getPlayer)
            .collect(Collectors.toList());
    assertThat("Make sure you are returning the correct scores", players, allOf(
            hasItems("FIRST", "SECOND", "THIRD"),
            not(hasItem("TOO_MANY"))
    ));
  }

  @Test
  public void streamIsUsed() throws Exception {
    String msg = String.format("Ensure you open a stream off of the scores collection in the '%s' method",
            METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
            .withExpression(Expressions.isMethodCall().and(Expressions.containing("stream")))
    ));
  }

  @Test
  public void filterIsCalled() throws Exception {
    String msg = String.format("Make sure you use the filter method off of your stream in the method '%s'",
            METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
      .withExpression(Expressions.isMethodCall()
              .and(Expressions.containing("filter")))
    ));
  }

  @Test
  public void appropriateBurgerTimeAppears() throws Exception {
    consoler.addExpectedPrompt("VALID_1");

    Main.printBurgerTimeScoresDeclaratively(filterScores);

    consoler.assertExpected("Expected to see Burger Time entry with 'VALID_1' for a name");
  }

  @Test
  public void onlyHighScores() throws Exception {
    Main.printBurgerTimeScoresDeclaratively(filterScores);

    consoler.assertNotPresent(
            "Make sure you display only scores with an amount greater or equal to 20,000",
            "NOT_ENOUGH_POINTS");
  }

  @Test
  public void onlyBurgerTime() throws Exception {
    Main.printBurgerTimeScoresDeclaratively(filterScores);

    consoler.assertNotPresent(
            "Make sure you display only scores with games named 'Burger Time'",
            "Dig Dug");

  }
}