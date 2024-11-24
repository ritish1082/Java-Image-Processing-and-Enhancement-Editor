package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.controller.IOImage;
import application.controller.ImageUtil;
import application.controller.PPMImage;
import application.model.Image;
import application.model.ImageProcessor;

/**
 * This command is used to load an image.
 */
public class Load implements ImageCommand {

  private final String imageName;
  private final String imagePath;

  /**
   * Constructs an instance of load.
   *
   * @param args the arguments sent by the controller.
   */
  public Load(String[] args) {
    if (args.length != 2) {
      throw new InputMismatchException("Invalid arguments in flip command");
    }

    this.imagePath = args[0];
    this.imageName = args[1];
  }


  @Override
  public void execute(ImageProcessor model) throws IOException {

    Image imageData;

    if (imagePath.endsWith(".ppm")) {
      ImageUtil ppmLoader = new PPMImage();
      imageData = ppmLoader.load(imagePath);
    } else if (imagePath.endsWith(".jpg") || imagePath.endsWith(".png")
            || imagePath.endsWith(".bmp")) {
      ImageUtil ioLoader = new IOImage();
      imageData = ioLoader.load(imagePath);
    } else {
      throw new IllegalArgumentException("Only ppm, png, jpg and bmp formats can be loaded");
    }

    model.load(imageName, imageData);

  }
}
