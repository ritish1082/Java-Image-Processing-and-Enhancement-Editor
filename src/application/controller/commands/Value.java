package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The command is used to get value intensity of images.
 */
public class Value implements ImageCommand {
  private final String[] args;

  /**
   * Constructs a value command with args.
   *
   * @param args the args required by the  value command.
   */
  public Value(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in flip command");
    }
    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.valueGreyscale(args);
  }
}

