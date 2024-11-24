package application.model.utils;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import application.model.ExtendedImage;
import application.model.ExtendedRGB;

/**
 * A util class which contains all the methods helpful for histograms.
 */
public class HistogramUtil {

  private ExtendedRGB image;

  /**
   * Creates an instance of histogram util with given image.
   *
   * @param image an extendedRGB image
   */
  public HistogramUtil(ExtendedRGB image) {
    this.image = image;
  }

  /**
   * Plot a histogram .
   *
   * @return an image with histogram plot.
   */
  public ExtendedImage plotHistogram() {
    BufferedImage histogramImage = new BufferedImage(256, 256,
            BufferedImage.TYPE_INT_RGB);
    Graphics g = histogramImage.getGraphics();

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 256, 256);

    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Calculate the histograms
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        redHistogram[image.getPixelValue(0, y, x)]++;
        greenHistogram[image.getPixelValue(1, y, x)]++;
        blueHistogram[image.getPixelValue(2, y, x)]++;
      }
    }

    int maxValue = Math.max(Math.max(getMax(redHistogram), getMax(greenHistogram)),
            getMax(blueHistogram));

    // Draw the grid
    g.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i <= 256; i += 32) {
      g.drawLine(i, 0, i, 256);
      g.drawLine(0, i, 256, i);
    }

    // Draw the histograms as line graphs
    drawHistogramLines(g, redHistogram, maxValue, Color.RED);
    drawHistogramLines(g, greenHistogram, maxValue, Color.GREEN);
    drawHistogramLines(g, blueHistogram, maxValue, Color.BLUE);

    g.dispose();

    ExtendedRGB resultImage = new ExtendedRGB(256, 256);
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        int rgb = histogramImage.getRGB(j, i);
        resultImage.setPixelValue(0, i, j, (rgb >> 16) & 0xff);
        resultImage.setPixelValue(1, i, j, (rgb >> 8) & 0xff);
        resultImage.setPixelValue(2, i, j, rgb & 0xff);
      }
    }

    return resultImage;
  }


  /* Method for drawing histogram lines */
  private void drawHistogramLines(Graphics g, int[] histogram, int maxValue, Color color) {
    g.setColor(color);
    double scale = (double) 256 / maxValue;

    // Start drawing from the first point
    for (int i = 0; i < histogram.length - 1; i++) {
      int x1 = i;
      int y1 = 256 - (int) (histogram[i] * scale);
      int x2 = i + 1;
      int y2 = 256 - (int) (histogram[i + 1] * scale);

      g.drawLine(x1, y1, x2, y2);
    }
  }

  /* Method for getting max value in a histogram chanel */
  private int getMax(int[] histogram) {
    int max = 0;
    for (int value : histogram) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  /**
   * Method used to generate offsets.
   *
   * @return an array of offsets used for color correct and level adjust.
   */
  public int[] computeOffsets() {
    int[][] histograms = new int[3][256];
    int[] peaks = new int[3];
    int sumPeaks = 0;

    for (int channel = 0; channel < 3; channel++) {
      histograms[channel] = computeHistogram(channel);
      peaks[channel] = findPeak(histograms[channel]);
      sumPeaks += peaks[channel];
    }

    int averagePeak = sumPeaks / 3;
    return new int[]{averagePeak - peaks[0], averagePeak - peaks[1], averagePeak - peaks[2]};
  }

  /* Method to compute histogram for a single channel */
  private int[] computeHistogram(int channel) {
    int[] histogram = new int[256];
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        histogram[image.getPixelValue(channel, y, x)]++;
      }
    }
    return histogram;
  }

  /* Method to find peak in a histogram channel */
  private int findPeak(int[] histogram) {
    int peak = 0;
    int maxCount = 0;
    // Exclude the extreme values (0-10 and 245-255)
    for (int i = 10; i < 245; i++) {
      if (histogram[i] > maxCount) {
        maxCount = histogram[i];
        peak = i;
      }
    }
    return peak;
  }


}