package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The command is used to level adjust an image by using the black, mid , white parameters.
 */
public class LevelsAdjust implements ImageCommand {

  private final String[] args;

  /**
   * Create an instance of the level adjust.
   *
   * @param args the arguments sent by the controller.
   */
  public LevelsAdjust(String[] args) {
    if (args.length <  2) {
      throw new InputMismatchException("Invalid number of arguments in level adjust command");
    }
    this.args = args;
  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.levelsAdjust(args);
  }
}
