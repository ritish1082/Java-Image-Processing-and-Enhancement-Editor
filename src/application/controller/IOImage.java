package application.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import application.model.ExtendedRGB;
import application.model.Image;

/**
 * This class represents the loading and saving images which are supported by ImageIO.
 */
public class IOImage implements ImageUtil {


  /**
   * Loads an image from the image path.
   *
   * @param imagePath the path of the image path.
   * @return the loaded image.
   */
  @Override
  public Image load(String imagePath) throws IOException {

    try {

      BufferedImage bufferedImage = ImageIO.read(new File(imagePath));

      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();

      Image rgbImage = new ExtendedRGB(width, height);

      int[][][] image = rgbImage.getChannels();

      // Loop through the BufferedImage and extract RGB values
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {

          // Get RGB value in hex 0XAARRGGBB
          int rgb = bufferedImage.getRGB(j, i);

          // Extract color channels
          int red = (rgb >> 16) & 0xff;
          int green = (rgb >> 8) & 0xff;
          int blue = rgb & 0xff;

          // Store the RGB values in the image array
          image[0][i][j] = red;
          image[1][i][j] = green;
          image[2][i][j] = blue;

        }
      }

      return rgbImage;

    } catch (IOException e) {
      System.out.println("Error in loading IO image");
    }

    return null; // if image is not loaded properly
  }


  /**
   * Saves an image from the image path.
   *
   * @param imagePath the path of the image path.
   */
  @Override
  public void save(String imagePath, Image ioImage) {
    try {
      int width = ioImage.getWidth();
      int height = ioImage.getHeight();

      BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      int[][][] image = ioImage.getChannels();

      // Loop through the channels and construct the BufferedImage
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int red = image[0][i][j];
          int green = image[1][i][j];
          int blue = image[2][i][j];
          int rgb = (red << 16) | (green << 8) | blue; // Combine RGB components
          bufferedImage.setRGB(j, i, rgb);
        }
      }

      // Save the image to the specified path
      String formatName = imagePath.substring(imagePath.lastIndexOf(".") + 1);
      ImageIO.write(bufferedImage, formatName, new File(imagePath));
    } catch (IOException e) {
      System.err.println("Error saving the image: " + e.getMessage());
    }
  }

}

