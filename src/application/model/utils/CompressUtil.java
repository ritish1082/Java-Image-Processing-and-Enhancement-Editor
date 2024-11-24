package application.model.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * A util class for compress.
 */
public class CompressUtil {

  /**
   * Creates a compress util object.
   */
  public CompressUtil() {
    // no parameters required.
  }

  private double[][] transform(double[][] data) {
    double[][] paddedData = this.pad(data);
    int c = paddedData.length;
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        double[] toCompress = new double[c];
        for (int j = 0; j < c; j++) {
          toCompress[j] = paddedData[i][j];
        }
        double[] compressed = transform(toCompress);
        for (int j = 0; j < c; j++) {
          paddedData[i][j] = compressed[j];
        }
      }
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = paddedData[i][j];
        }
        column = transform(column);
        for (int i = 0; i < c; i++) {
          paddedData[i][j] = column[i];
        }
      }
      c = c / 2;
    }
    return paddedData;
  }

  private double[] transform(double[] data) {
    double[] paddedData = this.pad(data);
    int totalLength = paddedData.length;
    double[] result = new double[totalLength];
    int mid = totalLength / 2;

    for (int i = 0; i < totalLength - 1; i += 2) {
      double a = paddedData[i];
      double b = paddedData[i + 1];

      double avg = (a + b) / Math.sqrt(2);
      double diff = (a - b) / Math.sqrt(2);

      result[i / 2] = avg;
      result[mid + i / 2] = diff;
    }
    return result;
  }

  private double[][] inverse(double[][] data) {
    int c = 2;
    double[][] paddedData = this.pad(data);
    while (c <= paddedData.length) {
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = paddedData[i][j];
        }
        column = inverse(column);
        for (int i = 0; i < c; i++) {
          paddedData[i][j] = column[i];
        }
      }
      for (int i = 0; i < c; i++) {
        double[] row = new double[c];
        for (int j = 0; j < c; j++) {
          row[j] = paddedData[i][j];
        }
        row = inverse(row);
        for (int j = 0; j < c; j++) {
          paddedData[i][j] = row[j];
        }
      }
      c *= 2;
    }
    return paddedData;
  }


  private double[] inverse(double[] data) {
    data = this.pad(data);
    int mid = data.length / 2;
    double[] result = new double[data.length];

    for (int i = 0, j = mid, k = 0; k < mid; i++, j++, k++) {
      double avg = data[i];
      double diff = data[j];

      double a = (avg + diff) / Math.sqrt(2);
      double b = (avg - diff) / Math.sqrt(2);

      result[2 * k] = a;
      result[2 * k + 1] = b;
    }
    return result;
  }


  private double[][] applyThreshold(double[][] transformedPaddedPixels, double threshold)
          throws IllegalArgumentException {
    if (transformedPaddedPixels == null) {
      return null;
    }
    if (threshold < 0 || threshold > 1) {
      throw new IllegalArgumentException("Threshold should be between 0 and 1");
    }
    int height = transformedPaddedPixels.length;
    int width = transformedPaddedPixels[0].length;
    double[][] result = new double[height][width];
    Map<Double, Double> uniqueValues = new HashMap<>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        uniqueValues.put((double) Math.round(transformedPaddedPixels[y][x]), 1.0);
      }
    }
    Double[] flatten = uniqueValues.keySet().toArray(new Double[0]);
    Arrays.sort(flatten);
    double thresholdValue = flatten[Math.max((int) (flatten.length * threshold) - 1, 0)];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (Math.round(transformedPaddedPixels[y][x]) < thresholdValue) {
          result[y][x] = 0;
        } else {
          result[y][x] = transformedPaddedPixels[y][x];
        }
      }
    }
    return result;
  }


  private double[] applyThreshold(double[] data, double threshold) throws IllegalArgumentException {
    if (data == null) {
      return null;
    }
    if (threshold < 0 || threshold > 1) {
      throw new IllegalArgumentException("Threshold should be between 0 and 1");
    }
    Map<Double, Double> dict = new HashMap<Double, Double>();
    for (double datum : data) {
      dict.put((double) Math.round(datum), 1.0);
    }
    Double[] values = dict.keySet().toArray(new Double[0]);
    Arrays.sort(values);
    double[] result = new double[data.length];
    int startLength = Math.max((int) (threshold * values.length) - 1, 0);
    double thresholdValue = values[startLength];
    for (int i = 0; i < data.length; i++) {
      if (Math.round(data[i]) <= thresholdValue) {
        result[i] = 0;
      } else {
        result[i] = data[i];
      }
    }
    return result;
  }


  private double[][] pad(double[][] data) {
    int height = data.length;
    int width = data[0].length;
    int s = Math.max(height, width);
    int nearestPow = this.getNearestPow(s);
    int padHeight = (int) Math.pow(2, nearestPow) - height;
    int padWidth = (int) Math.pow(2, nearestPow) - width;
    int totalHeight = height + padHeight;
    int totalWidth = width + padWidth;
    double[][] paddedData = new double[totalHeight][totalWidth];
    for (int i = 0; i < totalHeight; i++) {
      for (int j = 0; j < totalWidth; j++) {
        if (j >= width || i >= height) {
          // Outside of image range
          paddedData[i][j] = 0;
        } else {
          // copy Image data
          paddedData[i][j] = data[i][j];
        }
      }
    }
    return paddedData;
  }

  private double[] pad(double[] data) {
    if (data == null) {
      return null;
    }
    int nearestPow = (int) Math.max(Math.ceil(Math.log10(data.length) / Math.log10(2)), 1);
    int pad = (int) Math.pow(2, nearestPow) - data.length;
    int totalLength = data.length + pad;
    double[] paddedData = new double[totalLength];
    for (int i = 0; i < paddedData.length; i++) {
      if (i < data.length) {
        paddedData[i] = data[i];
      } else {
        paddedData[i] = 0;
      }
    }
    return paddedData;
  }

  private int getNearestPow(int number) {
    return (int) Math.max(Math.ceil(Math.log10(number) / Math.log10(2)), 1);
  }

  /**
   * Compresses the given channel matrix based on the specified compression percentage.
   *
   * @param channel    a 2D array representing the pixel values of a single image channel.
   * @param width      the width of the image.
   * @param height     the height of the image.
   * @param percentage the percentage to compress.
   * @return a 2D array of integers representing the compressed image channel.
   */
  public int[][] compress(double[][] channel, int width, int height, double percentage) {
    int maxPixelValue = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (channel[i][j] > maxPixelValue) {
          maxPixelValue = (int) channel[i][j];
        }
      }
    }

    double[][] transformedPixels = transform(channel);
    double[][] thresholdPixels = applyThreshold(transformedPixels, percentage / 100);

    double[][] inversePixels = inverse(thresholdPixels);


    int[][] compressedPixels = new int[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = (int) Math.round(inversePixels[y][x]);
        if (pixel < 0) {
          pixel = 0;
        }
        if (pixel > maxPixelValue) {
          pixel = maxPixelValue;
        }
        compressedPixels[y][x] = pixel;
      }
    }

    return compressedPixels;

  }

}
