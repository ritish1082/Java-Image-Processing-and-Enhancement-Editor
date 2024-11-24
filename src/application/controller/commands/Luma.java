package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The command is used for luma intensity.
 */
public class Luma implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a luma command with given args.
   *
   * @param args the args required by the luma command.
   */
  public Luma(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in flip command");
    }
    this.args = args;

  }


  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.lumaGreyscale(args);
  }
}

