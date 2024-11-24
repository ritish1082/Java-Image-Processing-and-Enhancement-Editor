package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The color correct command is used to color correct by aligning the peaks of the image.
 */
public class ColorCorrect implements ImageCommand {
  private final String[] args;

  /**
   * Constructs a new color correct command with the specified arguments.
   *
   * @param args the arguments for the color correct command.
   */
  public ColorCorrect(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in color correct command");
    }
    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.colorCorrect(args);
  }
}
