package application.controller.commands;

import application.model.ImageProcessor;

import java.util.InputMismatchException;

/**
 * The command is used to split an image into R, G, B channels.
 */
public class RGBSplit implements ImageCommand {

  private String imageName;
  private String redImageName;
  private String greenImageName;
  private String blueImageName;

  /**
   * Creates an instance of RGB Split.
   *
   * @param args the arguments sent by the controller.
   */
  public RGBSplit(String[] args) {
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
    model.rgbSplit(imageName, redImageName, greenImageName, blueImageName);
  }
}
