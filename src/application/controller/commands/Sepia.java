package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;


/**
 * The command is used to get sepia tone from images.
 */
public class Sepia implements ImageCommand {

  private final String[] args;

  /**
   * Constructs a sepia command with args.
   *
   * @param args the args required by the sepia command.
   */
  public Sepia(String[] args) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in sepia command");
    }
    this.args = args;

  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.sepia(args);
  }
}

