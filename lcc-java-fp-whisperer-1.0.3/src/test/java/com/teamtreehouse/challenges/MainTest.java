package com.teamtreehouse.challenges;

import com.teamtreehouse.ast.Inspector;
import com.teamtreehouse.ast.predicates.Expressions;
import com.teamtreehouse.ast.rules.Sourcery;
import com.teamtreehouse.teachingassistant.rules.Consoler;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

public class MainTest {
  public static final String METHOD_NAME = "whisperSentencesDeclaratively";
  private Inspector inspector;

  @ClassRule
  public static Sourcery sourcery = new Sourcery();

  @Rule
  public Consoler consoler = new Consoler();


  @Before
  public void setUp() throws Exception {
    inspector = sourcery.inspectorFor(Main.class);
  }

  @Test
  public void methodUsesMethodReference() throws Exception {
    String msg = String.format("Method '%s' isn't using a reference to the method whisper", METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain ->
            chain.withExpression(Expressions.isMethodReference()
                    .and(Expressions.containing("whisper")))));
  }

  @Test
  public void printsProperly() throws Exception {
    Main.whisperSentencesDeclaratively(Arrays.asList("Example1!", "EXAMPLE2!!!"));
    Main.whisperSentencesDeclaratively(Arrays.asList("Example3"));
    consoler.addExpectedPrompt("example1.");
    consoler.addExpectedPrompt("example2...");
    consoler.addExpectedPrompt("example3");
    String msg = String.format("The output from your method '%s' seems wrong", METHOD_NAME);
    consoler.assertExpected(msg);
  }
}