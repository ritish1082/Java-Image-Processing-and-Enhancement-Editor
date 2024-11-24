import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import application.controller.ImageController;

import static org.junit.Assert.assertEquals;


/**
 * A junit test for controller.
 */
public class ControllerTest {


  private ImageController controller;
  private Reader reader;
  private Writer writer;

  @Test
  public void testCommandInvalid() throws IOException {

    String testCommand = "laod ";

    reader = new StringReader(testCommand);
    writer = new StringWriter();

    controller = new ImageController(reader, writer);

    assertEquals(writer.toString(), "Please provide a valid command.");


  }

  /* Controller takes a script and generates ia=mages */
  @Test
  public void testScript() throws IOException {

    String testCommand = "-file script.txt";

    reader = new StringReader(testCommand);
    writer = new StringWriter();

    controller = new ImageController(reader, writer);

    assertEquals(writer.toString(), "Exit application.");
  }


}
