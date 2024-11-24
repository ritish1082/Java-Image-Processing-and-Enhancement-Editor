package application.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//import application.model.ExtendedRGB;
import application.model.Image;
import application.model.RGBImage;

/**
 * This class handles te load and save of a PPM image.
 */
public class PPMImage implements ImageUtil {

  @Override
  public Image load(String imagePath) {
    try {
      File file = new File(imagePath);
      Scanner scanner = new Scanner(file);

      // Validate PPM header
      String header = scanner.nextLine().trim();
      if (!header.equals("P3")) {
        throw new IllegalArgumentException("Unsupported PPM format: " + header);
      }

      // Read dimensions and max color value
      int width = scanner.nextInt();
      int height = scanner.nextInt();
      int maxColorValue = scanner.nextInt();
      if (maxColorValue != 255) {
        throw new IllegalArgumentException("Unsupported max color value: " + maxColorValue);
      }

      // Initialize image
      RGBImage ppmImage = new RGBImage(width, height);
      int[][][] image = ppmImage.getChannels();

      // Read and store pixel data
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          image[0][i][j] = scanner.nextInt();
          image[1][i][j] = scanner.nextInt();
          image[2][i][j] = scanner.nextInt();
        }
      }

      return ppmImage;
    } catch (IOException e) {
      System.err.println("Error loading PPM file: " + e.getMessage());
    }

    return null;
  }


  @Override
  public void save(String imagePath, Image image) {
    try {
      FileWriter writer = new FileWriter(imagePath);

      int width = image.getWidth();
      int height = image.getHeight();

      // Write PPM header
      writer.write("P3\n");
      writer.write(width + " " + height + "\n");
      writer.write("255\n");

      // Write pixel data
      int[][][] channels = image.getChannels();
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          writer.write(channels[0][i][j] + " " +
                  channels[1][i][j] + " " +
                  channels[2][i][j] + "\n");
        }
      }

      writer.close();
    } catch (IOException e) {
      System.err.println("Error saving PPM file: " + e.getMessage());
    }
  }


}
