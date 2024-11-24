package application.controller;

import java.io.IOException;

import application.model.Image;

/**
 * This interface defines utility methods for loading and saving various image formats.
 */
public interface ImageUtil {

  /**
   * Loads an image from the specified file path.
   *
   * @param imagePath the file path of the image to load, as a String.
   * @return an Image object representing the loaded image data.
   * @throws IllegalArgumentException if the file path is invalid or if the image cannot be loaded.
   */
  Image load(String imagePath) throws IOException;

  /**
   * Saves the provided image to the specified file path.
   *
   * @param imagePath the file path where the image should be saved, as a String.
   * @param image the {@link Image} object to save.
   * @throws IllegalArgumentException if the file path is invalid or if the image cannot be saved.
   */
  void save(String imagePath, Image image);

}
