package application.controller.commands;

import java.io.IOException;

import application.model.ImageProcessor;

/**
 * Downscale the image by the required height and width.
 */
public class Downscaling implements ImageCommand {

  private final String imageName;
  private final String destinationImage;
  private final int newHeight;
  private final int newWidth;


  /**
   * Constructs a new downscaling command with the specified arguments.
   *
   * @param args the arguments for the downscaling command.
   */
  public Downscaling(String[] args) {

    this.newHeight = Integer.parseInt(args[0]);
    this.newWidth = Integer.parseInt(args[1]);
    this.imageName = args[2];
    this.destinationImage = args[3];

  }


  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.downScaling(newHeight, newWidth, imageName, destinationImage);
  }
}
