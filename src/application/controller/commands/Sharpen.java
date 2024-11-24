package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The command is used to sharpen the images.
 */
public class Sharpen implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a sharpen command with given args.
   *
   * @param args the args required by the sharpen command.
   */
  public Sharpen(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in flip command");
    }
    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.sharpen(args);
  }

}

