package application.model;

import java.awt.Image;

/**
 * This interface defines methods used by the view to display and retrieve processed images.
 */
public interface ViewImage {

  /**
   * Retrieves a buffered image based on the given image identifier.
   *
   * @param imageName the identifier of the image to be retrieved.
   * @return an awt Image representing the buffered image.
   */
  Image getBufferedImage(String imageName);


  /**
   * Get the width of the current image in view.
   *
   * @return the width of the image.
   */
  int getWidth(String imageName);


  /**
   * Get the height of the current image in view.
   *
   * @return the height of the image.
   */
  int getHeight(String imageName);


}
