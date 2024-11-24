package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The dither command is used to dither effect the image.
 */
public class Dither implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a new dither command with the specified arguments.
   *
   * @param args the arguments for the dither command.
   */
  public Dither(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in dither command");
    }

    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.dither(args);
  }
}

