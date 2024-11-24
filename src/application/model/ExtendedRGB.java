package application.model;

import application.model.utils.CompressUtil;
import application.model.utils.HistogramUtil;

/**
 * This class represents an extendedRGB.
 * This class has additional features as compared to the RGB image.
 */
public class ExtendedRGB extends RGBImage implements ExtendedImage {

  /**
   * Construct an instance of extendedRGB which has features of Image and ExtendedImage features.
   *
   * @param width  the width of the image
   * @param height the height of the image
   */
  public ExtendedRGB(int width, int height) {
    super(width, height);
  }

  @Override
  public Image compress(double percentage) {
    int width = this.getWidth();
    int height = this.getHeight();

    double[][] reds = new double[height][width];
    double[][] greens = new double[height][width];
    double[][] blues = new double[height][width];


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        reds[i][j] = this.getPixelValue(0, i, j);
        greens[i][j] = this.getPixelValue(1, i, j);
        blues[i][j] = this.getPixelValue(2, i, j);
      }
    }


    CompressUtil util = new CompressUtil();

    int[][][] compressedChannels = new int[3][height][width];


    compressedChannels[0] = util.compress(reds, width, height, percentage);
    System.out.println("RED");

    compressedChannels[1] = util.compress(greens, width, height, percentage);
    System.out.println("green");

    compressedChannels[2] = util.compress(blues, width, height, percentage);
    System.out.println("blue");


    ExtendedRGB compressedImage = new ExtendedRGB(width, height);


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int c = 0; c < 3; c++) {
          compressedImage.setPixelValue(c, i, j, compressedChannels[c][i][j]);
        }
      }
    }


    return compressedImage;
  }


  @Override
  public Image histogram() {

    HistogramUtil histogram = new HistogramUtil(this);

    return histogram.plotHistogram();

  }

  @Override
  public Image colorCorrect() {
    HistogramUtil histogram = new HistogramUtil(this);

    int[] offsets = histogram.computeOffsets();

    ExtendedRGB correctedImage = new ExtendedRGB(getWidth(), getHeight());

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        for (int channel = 0; channel < 3; channel++) {
          int adjustedValue = clamp(getPixelValue(channel, y, x) + offsets[channel]);
          correctedImage.setPixelValue(channel, y, x, adjustedValue);
        }
      }
    }

    return correctedImage;

  }

  /**
   * The method to give levels adjust.
   *
   * @param black the threshold for shadows, values below this will be set to 0.
   * @param mid   the mid reference point used to adjust the linear scaling.
   * @param white the threshold for highlights, values above this will be set to 255.
   * @return
   */
  public Image levelsAdjust(int black, int mid, int white) {

    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("All values must be between 0 and 255");
    }
    if (!(black < mid && mid < white)) {
      throw new IllegalArgumentException("Values must be in ascending order: black < mid < white");
    }

    double normalizationFactor = Math.pow(black, 2) * (mid - white) - black *
            (Math.pow(mid, 2) - Math.pow(white, 2))
            + white * Math.pow(mid, 2) - mid * Math.pow(white, 2);

    double coefficientX = -black * (128 - 255) + 128 * white - 255 * mid;
    double coefficientY = Math.pow(black, 2) * (128 - 255) + 255 * Math.pow(mid, 2) - 128 *
            Math.pow(white, 2);
    double coefficientZ = Math.pow(black, 2) * (255 * mid - 128 * white) - black
            * (255 * Math.pow(mid, 2) - 128 * Math.pow(white, 2));

    double coeffA = coefficientX / normalizationFactor;
    double coeffB = coefficientY / normalizationFactor;
    double coeffC = coefficientZ / normalizationFactor;

    ExtendedRGB adjustedImage = new ExtendedRGB(getWidth(), getHeight());

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        for (int channel = 0; channel < 3; channel++) {
          int originalValue = getPixelValue(channel, y, x);

          double result;

          if (originalValue <= black) {
            result = 0;
          } else if (originalValue >= white) {
            result = 255;
          } else {
            result = coeffA * Math.pow(originalValue, 2) + coeffB * originalValue + coeffC;
            result = Math.max(0, Math.min(255, result));
          }
          adjustedImage.setPixelValue(channel, y, x, (int) Math.round(result));
        }
      }
    }
    return adjustedImage;
  }


  @Override
  public Image downScaling(int newHeight, int newWidth) {
    if (newHeight <= 0 || newWidth <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than 0");
    }

    int originalWidth = this.getWidth();
    int originalHeight = this.getHeight();

    if (newHeight > originalHeight || newWidth > originalWidth) {
      throw new IllegalArgumentException("Width and height must be less than original" +
              " for downscaling");
    }

    int targetWidth = newWidth;
    int targetHeight = newHeight;

    ExtendedRGB downscaledImage = new ExtendedRGB(targetWidth, targetHeight);

    for (int row = 0; row < targetHeight; row++) {
      for (int col = 0; col < targetWidth; col++) {

        float x = col * (float) originalWidth / targetWidth;  // Horizontal mapping
        float y = row * (float) originalHeight / targetHeight; // Vertical mapping

        for (int channel = 0; channel < 3; channel++) {

          int x1 = (int) Math.floor(x);
          int y1 = (int) Math.floor(y);
          int x2 = Math.min((int) Math.ceil(x), originalWidth - 1);
          int y2 = Math.min((int) Math.ceil(y), originalHeight - 1);

          int cA = this.getPixelValue(channel, y1, x1);
          int cB = this.getPixelValue(channel, y1, x2);
          int cC = this.getPixelValue(channel, y2, x1);
          int cD = this.getPixelValue(channel, y2, x2);

          float dx = x - x1;
          float dy = y - y1;

          int m = (int) ((1 - dx) * cA + dx * cB);
          int n = (int) ((1 - dx) * cC + dx * cD);

          int finalColorValue = (int) ((1 - dy) * m + dy * n);

          finalColorValue = Math.max(0, Math.min(255, finalColorValue));

          downscaledImage.setPixelValue(channel, row, col, finalColorValue);
        }
      }
    }
    return downscaledImage;
  }


  @Override
  public Image dither() {
    int height = this.getHeight();
    int width = this.getWidth();
    Image image = this.channelComponent(null, 0);
    int[][][] channels = image.getChannels();

    ExtendedRGB ditherImage = new ExtendedRGB(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int oldColor = channels[0][y][x]; // You can adjust this for each channel (red, green, blue)

        // Calculate new color (either 0 or 255, whichever is closer)
        int newColor = (Math.abs(oldColor - 0) <= Math.abs(oldColor - 255)) ? 0 : 255;

        // Set the new color for all channels
        ditherImage.setPixelValue(0, y, x, newColor);
        ditherImage.setPixelValue(1, y, x, newColor); // Apply for green channel
        ditherImage.setPixelValue(2, y, x, newColor); // Apply for blue channel

        // Calculate the error
        int error = oldColor - newColor;

        // Diffuse error to adjacent pixels

        // (r, c+1) - Next pixel to the right
        if (x + 1 < width) {
          ditherImage.setPixelValue(0, y, x + 1, ditherImage.getPixelValue(0, y, x + 1) + (7 * error) / 16);
          ditherImage.setPixelValue(1, y, x + 1, ditherImage.getPixelValue(1, y, x + 1) + (7 * error) / 16);
          ditherImage.setPixelValue(2, y, x + 1, ditherImage.getPixelValue(2, y, x + 1) + (7 * error) / 16);
        }

        // (r+1, c-1) - Next-row-left
        if (y + 1 < height && x - 1 >= 0) {
          ditherImage.setPixelValue(0, y + 1, x - 1, ditherImage.getPixelValue(0, y + 1, x - 1) + (3 * error) / 16);
          ditherImage.setPixelValue(1, y + 1, x - 1, ditherImage.getPixelValue(1, y + 1, x - 1) + (3 * error) / 16);
          ditherImage.setPixelValue(2, y + 1, x - 1, ditherImage.getPixelValue(2, y + 1, x - 1) + (3 * error) / 16);
        }

        // (r+1, c) - Below
        if (y + 1 < height) {
          ditherImage.setPixelValue(0, y + 1, x, ditherImage.getPixelValue(0, y + 1, x) + (5 * error) / 16);
          ditherImage.setPixelValue(1, y + 1, x, ditherImage.getPixelValue(1, y + 1, x) + (5 * error) / 16);
          ditherImage.setPixelValue(2, y + 1, x, ditherImage.getPixelValue(2, y + 1, x) + (5 * error) / 16);
        }

        // (r+1, c+1) - Next-row-right
        if (y + 1 < height && x + 1 < width) {
          ditherImage.setPixelValue(0, y + 1, x + 1, ditherImage.getPixelValue(0, y + 1, x + 1) + (1 * error) / 16);
          ditherImage.setPixelValue(1, y + 1, x + 1, ditherImage.getPixelValue(1, y + 1, x + 1) + (1 * error) / 16);
          ditherImage.setPixelValue(2, y + 1, x + 1, ditherImage.getPixelValue(2, y + 1, x + 1) + (1 * error) / 16);
        }
      }
    }

    return ditherImage;
  }


  /* Clamp the values between 0 and 255 */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }

}
