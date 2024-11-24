package application.controller.commands;

import java.io.IOException;

import application.model.ImageProcessor;

/**
 * The interface that defines the structure of the command.
 */
public interface ImageCommand {

  /**
   * Execute the command using the given image processor model.
   *
   * @param model the command uses this model to execute the operations.
   * @throws IOException throws an IO exception if occurred.
   */
  void execute(ImageProcessor model) throws IOException;


}
