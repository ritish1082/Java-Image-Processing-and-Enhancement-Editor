package application.model;

/**
 * This interface represents an extended image.
 * An extended image has additional features on top of the existing image interface.
 */
public interface ExtendedImage extends Image {

  /**
   * Compress the image with the given percentage.
   *
   * @param percentage the percentage to compress
   * @return a compressed Image
   */
  Image compress(double percentage);


  /**
   * Returns the histogram of the image.
   *
   * @return a 256 x 256 image with red, green and blue channels as line graphs.
   */
  Image histogram();

  /**
   * Color correct an image by aligning the meaningful peaks of its histogram.
   *
   * @return a color corrected image.
   */
  Image colorCorrect();


  /**
   * Adjust the levels of an image.
   *
   * @param black the threshold for shadows, values below this will be set to 0.
   * @param mid   the mid reference point used to adjust the linear scaling.
   * @param white the threshold for highlights, values above this will be set to 255.
   * @return a level adjusted image.
   */
  Image levelsAdjust(int black, int mid, int white);


  /**
   * Downscale the image by the new height and width.
   *
   * @param newHeight the height of downscaled image.
   * @param newWidth  the width of downscaled image.
   * @return an Image of downscaled dimensions.
   */
  Image downScaling(int newHeight, int newWidth);

  /**
   * Apply dither filter on the image.
   *
   * @return an image with dither filter.
   */
  Image dither();


}
