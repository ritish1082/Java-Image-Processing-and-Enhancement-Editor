package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * Flips the image in the orientation given.
 */
public class Flip implements ImageCommand {

  private final String imageName;
  private final String destinationImage;
  private final String direction;

  /**
   * Constructs a Flip command with required args.
   *
   * @param args      the args requored by the commnad.
   * @param direction the direction to be flipped.
   */
  public Flip(String[] args, String direction) {
    if (args.length != 2) {
      throw new InputMismatchException("Invalid arguments in flip command");
    }

    this.imageName = args[0];
    this.destinationImage = args[1];
    this.direction = direction;
  }


  @Override
  public void execute(ImageProcessor model) throws IOException {
    if (direction.equals("horizontal")) {
      model.horizontalFlip(imageName, destinationImage);

    } else if (direction.equals("vertical")) {
      model.verticalFlip(imageName, destinationImage);
    }

  }
}
