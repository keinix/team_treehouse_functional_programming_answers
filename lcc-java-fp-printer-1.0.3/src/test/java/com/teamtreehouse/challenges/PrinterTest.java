package com.teamtreehouse.challenges;

import com.teamtreehouse.ast.Inspector;
import com.teamtreehouse.ast.predicates.Expressions;
import com.teamtreehouse.ast.predicates.Methods;
import com.teamtreehouse.ast.rules.Sourcery;
import com.teamtreehouse.teachingassistant.rules.Consoler;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class PrinterTest {
  public static final String METHOD_NAME = "printChipmunksDeclaratively";
  private Inspector inspector;

  @ClassRule
  public static Sourcery sourcery = new Sourcery();

  @Rule
  public Consoler consoler = new Consoler();


  @Before
  public void setUp() throws Exception {
    inspector = sourcery.inspectorFor(Printer.class);
  }

  @Test
  public void declarativeMethodExists() throws Exception {
    String msg = String.format("Whoops! Looks like you accidentally removed the '%s' method",
            METHOD_NAME);
    assertTrue(msg, inspector.hasMethodNamed(METHOD_NAME));
  }

  @Test
  public void declarativelyUsesForEach() throws Exception {
    String msg = String.format("Ensure you use forEach in the more declarative '%s' method",
            METHOD_NAME);
    assertTrue(msg, inspector.matchingChain(chain -> chain
            .withMethod(Methods.named(METHOD_NAME))
            .withExpression(Expressions.isMethodCall().and(
              Expressions.containing("forEach")))));
  }

  @Test
  public void printsProperly() throws Exception {
    Printer.printChipmunksDeclaratively(Arrays.asList("Example1", "Example2"));
    Printer.printChipmunksDeclaratively(Arrays.asList("Example3"));
    consoler.addExpectedPrompt("Example1!");
    consoler.addExpectedPrompt("Example2!");
    consoler.addExpectedPrompt("Example3!");
    String msg = String.format("The output from your method '%s' seems wrong", METHOD_NAME);
    consoler.assertExpected(msg);
  }
}