package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The histogram command is used to plot a histogram of an image.
 */
public class Histogram implements ImageCommand {

  private final String imageName;
  private final String destinationImage;

  /**
   * Create an instance of histogram.
   *
   * @param args the arguments sent by the controller.
   */
  public Histogram(String[] args) {

    if (args.length != 2) {
      throw new InputMismatchException("Invalid number of arguments in histogram command");
    }
    this.imageName = args[0];
    this.destinationImage = args[1];
  }


  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.histogram(imageName, destinationImage);
  }
}
