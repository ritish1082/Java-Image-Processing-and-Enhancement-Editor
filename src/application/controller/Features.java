package application.controller;

import java.io.IOException;

/**
 * An interface that defines all the features available in the view application.
 */
public interface Features {

  /**
   * Loads an image from the specified file path.
   *
   * @param filepath the path to the image file to be loaded.
   * @throws IOException if an error occurs during file loading.
   */
  void load(String filepath) throws IOException;

  /**
   * Saves the current image to the specified file path.
   *
   * @param filepath the path where the image will be saved.
   * @throws IOException if an error occurs during file saving.
   */
  void save(String filepath) throws IOException;

  /**
   * Applies a channel component operation to the image.
   *
   * @param channel the channel to be extracted (e.g., 0 for red, 1 for green, 2 for blue).
   * @throws IOException if an error occurs during processing.
   */
  void channelComponent(int channel) throws IOException;

  /**
   * Flips the image vertically.
   *
   * @throws IOException if an error occurs during processing.
   */
  void verticalFlip() throws IOException;

  /**
   * Flips the image horizontal.
   *
   * @throws IOException if an error occurs during processing.
   */
  void horizontalFlip() throws IOException;

  /**
   * Applies a blur filter to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void blur() throws IOException;


  /**
   * Brightens the image by given value.
   *
   * @throws IOException if an error occurs during processing.
   */
  void brighten(int value) throws IOException;

  /**
   * Applies a sharpen filter to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void sharpen() throws IOException;


  /**
   * Applies a luma grayscale filter to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void lumaGreyscale() throws IOException;


  /**
   * Applies a sepia tone filter to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void sepia() throws IOException;

  /**
   * Compresses the image by a given percentage.
   *
   * @param percentage the percentage to compress the image by.
   * @throws IOException if an error occurs during processing.
   */
  void compress(double percentage) throws IOException;

  /**
   * Applies color correction to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void colorCorrect() throws IOException;


  /**
   * Adjusts the levels of the image using black, mid, and white points.
   *
   * @param black the black point value.
   * @param mid   the mid point value.
   * @param white the white point value.
   * @throws IOException if an error occurs during processing.
   */
  void levelAdjust(int black, int mid, int white) throws IOException;


  /**
   * Downscale the current image to the specified height and width using interpolation.
   *
   * @param newHeight the target height of the downscaled image
   * @param newWidth  the target width of the downscaled image
   * @throws IOException if the image matrix is not properly initialized or is empty
   */
  void downScaling(int newHeight, int newWidth) throws IOException;


  /**
   * Refreshes the view to reflect any changes made.
   *
   * @throws IOException if error occurs.
   */
  void reloadView() throws IOException;


  /**
   * Exits the application.
   *
   * @throws IOException if an error occurs during closing.
   */
  void exitProgram() throws IOException;


  void preview(String command, String[] args) throws IOException;

  void defaultView() throws IOException;


  /**
   * Applies a value grayscale filter to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void valueGreyscale() throws IOException;

  /**
   * Applies an intensity grayscale filter to the image.
   *
   * @throws IOException if an error occurs during processing.
   */
  void intensityGreyscale() throws IOException;

  /**
   * Apply a dither to the  image
   *
   * @throws IOException if an error occurs during processing.
   */
  void dither() throws IOException;


}
