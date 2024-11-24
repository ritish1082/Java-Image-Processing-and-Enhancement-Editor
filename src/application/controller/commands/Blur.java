package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The blur command is used to blur the image.
 */
public class Blur implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a new Blur command with the specified arguments.
   *
   * @param args the arguments for the blur command.
   */
  public Blur(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in flip command");
    }

    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.blur(args);
  }
}

