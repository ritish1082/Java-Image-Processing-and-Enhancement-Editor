package application.controller;

import java.io.IOException;

import application.model.ImageProcessor;

/**
 * This interface is implemented by controller which run the application.
 */
public interface ImageControllerInterface {

  /**
   * This method is used to run the application.
   *
   * @param model the model can be used to edit and manipulate images.
   * @throws IOException if an IO error occurs during execution.
   */
  void runApplication(ImageProcessor model) throws IOException;
}
