package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The brighten command is used to brighten the image.
 */
public class Brighten implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a new brighten command with the specified arguments.
   *
   * @param args the arguments for the brighten command.
   */
  public Brighten(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in flip command");
    }

    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.brighten(args);
  }


}

