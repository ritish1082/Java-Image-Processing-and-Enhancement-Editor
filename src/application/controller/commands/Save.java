package application.controller.commands;

import java.io.IOException;

import application.controller.IOImage;
import application.controller.ImageUtil;
import application.controller.PPMImage;
import application.model.Image;
import application.model.ImageProcessor;

import java.util.InputMismatchException;


/**
 * The command is used to save images.
 */
public class Save implements ImageCommand {

  private String imageName;
  private String imagePath;

  /**
   * Constructs an instance of save.
   *
   * @param args the arguments sent by the controller.
   */
  public Save(String[] args) {
    if (args.length != 2) {
      throw new InputMismatchException("Invalid arguments in save command");
    }

    this.imagePath = args[0];
    this.imageName = args[1];

  }


  @Override
  public void execute(ImageProcessor model) throws IOException {

    Image imageData;

    imageData = model.getImage(imageName);

    if (imagePath.endsWith(".ppm")) {
      ImageUtil ppmLoader = new PPMImage();
      ppmLoader.save(imagePath, imageData);
    } else if (imagePath.endsWith(".jpg") || imagePath.endsWith(".png")
            || imagePath.endsWith(".bmp")) {
      ImageUtil ioLoader = new IOImage();
      ioLoader.save(imagePath, imageData);
    } else {
      throw new IllegalArgumentException("Application can only save JPG, PNG, PPM file formats.");
    }


  }
}
