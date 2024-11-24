package application.controller.commands;

import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The command is used to combine rgb images.
 */
public class RGBCombine implements ImageCommand {

  private final String imageName;
  private final String redImageName;
  private final String greenImageName;
  private final String blueImageName;

  /**
   * Creates an instance of RGBCombine.
   *
   * @param args the arguments sent by the controller.
   */
  public RGBCombine(String[] args) {

    if (args.length != 4) {
      throw new InputMismatchException("Invalid arguments in RGB combine command");
    }

    this.imageName = args[0];
    this.redImageName = args[1];
    this.greenImageName = args[2];
    this.blueImageName = args[3];
  }

  @Override
  public void execute(ImageProcessor model) {
    model.rgbCombine(redImageName, greenImageName, blueImageName, imageName);
  }
}
