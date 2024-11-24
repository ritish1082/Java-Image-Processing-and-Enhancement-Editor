package application.view;

import java.io.IOException;

import application.controller.Features;

/**
 * Interface that describes the features in the view of the application.
 */
public interface IView {

  /**
   * Adds the features to the view, allowing the view to interact with the controller.
   *
   * @param features the features to be added to the view, provided by the controller.
   */
  void addFeatures(Features features);

  /**
   * Refreshes the screen with the updated image and its histogram.
   *
   * @param imageName the name of the image to display.
   * @param histogram the histogram data to display alongside the image.
   * @throws IOException if an error occurs while updating the view.
   */
  void refreshScreen(String imageName, String histogram) throws IOException;

  /**
   * Displays an error dialog to the user with given message.
   *
   * @param message the error message to be shown.
   */
  void showErrorDialog(String message);


  /**
   * Exit the application.
   * Prompt the user if te image is not saved.
   */
  void exitApplication(Features features);
}
