package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The intensity command that is used to increase the intensity of an image by an increment.
 */
public class Intensity implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a intensity command with args.
   *
   * @param args the args required by the intensity command.
   */
  public Intensity(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in intensity command");
    }
    this.args = args;
  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.intensityGreyscale(args);
  }
}

