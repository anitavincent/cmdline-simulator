package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import output.Output;

public class OutputTest {

  private Output out;

  @Before
  public void setup() {
    out = new Output();
  }

  @Test
  public void testAppendStringToStdOutput() {
    out.appendToStdOutput("output");
    assertEquals("output", out.getStdOutput());
  }

  @Test
  public void testAppendStringToStdError() {
    out.appendToStdError("error");
    assertEquals("error", out.getStdError());
  }

  @Test
  public void testConsumeOutput() {
    out.appendToStdOutput("output");
    assertEquals("output", out.consumeStdOutput());
    assertNull(out.getStdOutput());
  }

  @Test
  public void testConsumeError() {
    out.appendToStdError("error");
    assertEquals("error", out.consumeStdError());
    assertNull(out.getStdError());
  }

}
