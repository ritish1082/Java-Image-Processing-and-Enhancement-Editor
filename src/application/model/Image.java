package application.model;

import java.util.List;

/**
 * This interface describes all the operation which can be done on an image.
 * It contains various methods such as flips, filtering, transformations.
 */
public interface Image {

  /**
   * Used to visualize the red component of an image.
   * A channel component in this application is defined where the pixel values of the channel
   * are set to the value of a particular channel.
   *
   * @param mask    an optional mask that defines the regions where the operation is applied.
   * @param channel the channel index to extract (e.g., 0 for red, 1 for green, 2 for blue).
   * @return an Image which represents a red component of an image.
   */
  Image channelComponent(Image mask, int channel);

  /**
   * Flips the image along a direction.
   *
   * @param direction the direction to flip the image ("horizontal" or "vertical").
   * @return a new image that is flipped by direction.
   */
  Image flip(String direction);

  /**
   * Returns the width of the image.
   *
   * @return the width of the image.
   */
  int getWidth();

  /**
   * Returns the height of the image.
   *
   * @return the height of the image.
   */
  int getHeight();

  /**
   * Retrieves the individual channels of the image.
   *
   * @return a 3D array containing the individual channels of the image.
   */
  int[][][] getChannels();

  /**
   * Returns an image that contains the maximum value of the three components of each pixel.
   *
   * @param mask an optional mask that defines the regions where the operation is applied.
   * @return a greyscale image from the maximum component values.
   */
  Image valueComponent(Image mask);

  /**
   * Returns an image that contains the average of the three components of each pixel.
   *
   * @param mask an optional mask that defines the regions where the operation is applied.
   * @return a greyscale image containing the average of the component values.
   */
  Image intensityComponent(Image mask);

  /**
   * Transform the image by applying the transformation r = g = b = 0.2126r + 0.7152g + 0.0722b.
   *
   * @param mask an optional mask that defines the regions where the operation is applied.
   * @return a greyscale image containing the transformed image.
   */
  Image lumaComponent(Image mask);

  /**
   * Brighten the image by the given increment.
   * The increment can be positive (brightening) or negative (darkening).
   *
   * @param increment the value to be incremented.
   * @return an image with incremented values.
   */
  Image brighten(int increment);

  /**
   * Split the given image into three images containing its red, green and blue components.
   *
   * @return a list of images containing the red, blue and green components respectively.
   */
  List<Image> rgbSplit();

  /**
   * Combines the three images that are individually red, green and blue into a single image.
   *
   * @param red   a red component image.
   * @param green a green component image.
   * @param blue  a blue component image.
   * @return an image that combines the red, green and blue components.
   */
  Image rgbCombine(Image red, Image green, Image blue);

  /**
   * Blur an image using a 3 x 3 gaussian kernel.
   *
   * @param mask an optional mask that defines the regions where the operation is applied.
   * @return a blurred image.
   */
  Image blur(Image mask);

  /**
   * Sharpen the image using a sharpening filter.
   *
   * @param mask an optional mask that defines the regions where the operation is applied.
   * @return a sharpened image.
   */
  Image sharpen(Image mask);

  /**
   * Apply a sepia tone to the image.
   *
   * @param mask an optional mask that defines the regions where the operation is applied.
   * @return an image with sepia tone.
   */
  Image sepia(Image mask);


}
