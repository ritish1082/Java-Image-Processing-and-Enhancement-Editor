package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The compress command is used to compress the image by a percentage.
 */
public class Compress implements ImageCommand {

  private final double percentage;
  private final String imagName;
  private final String destinationName;

  /**
   * Constructs a compress command with the arguments.
   *
   * @param args the args required for the compress command.
   */
  public Compress(String[] args) {

    if (args.length != 3) {
      throw new InputMismatchException("Invalid number of arguments in compress command");
    }

    this.percentage = Double.parseDouble(args[0]);

    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Compression percentage must be between 0 and 100");
    }

    this.imagName = args[1];
    this.destinationName = args[2];

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.compress(percentage, imagName, destinationName);
  }

}
