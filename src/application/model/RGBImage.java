package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * A class representing an RGB image.
 * An RGB image consists of three channels: red, green, and blue,
 * where each channel is a 2D matrix of integer values. The channels
 * are organized in that order to represent colors in a standard RGB color space.
 */
public class RGBImage extends AbstractImage {

  /**
   * Constructs an RGB image with 3 channels in the order red, green, blue .
   */
  public RGBImage(int width, int height) {
    super(3, width, height);
  }


  /**
   * Processes the pixels of an image channel based on mask condition and processing function.
   *
   * @param pixelProcessor takes the coordinate of pixel and returns the value depending
   *                       on the condition.
   * @param maskCondition  determines if a specific coordinate (x,y) should be processed.
   * @param channel        the channel of the image to be processed.
   * @return a 2D array representing the processed pixel values of the specified channel.
   */
  protected int[][] pixelProcessor(BiFunction<Integer, Integer, Integer> pixelProcessor,
                                   BiPredicate<Integer, Integer> maskCondition, int channel) {
    int[][] pixels = new int[getHeight()][getWidth()];

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        if (maskCondition.test(x, y)) {
          pixels[y][x] = pixelProcessor.apply(x, y);
        } else {
          pixels[y][x] = this.getPixelValue(channel, y, x);
        }
      }
    }
    return pixels;
  }


  @Override
  public Image channelComponent(Image mask, int channel) {
    RGBImage channelComponent = new RGBImage(getWidth(), getHeight());

    int[][][] channels = channelComponent.getChannels();

    BiPredicate<Integer, Integer> maskCondition = (x, y) -> {
      if (mask == null) {
        return true;
      }
      RGBImage gray = (RGBImage) mask;
      return !(gray.getPixelValue(0, y, x) == 255 &&
              gray.getPixelValue(1, y, x) == 255 &&
              gray.getPixelValue(2, y, x) == 255);
    };

    channels[0] = pixelProcessor((x, y) -> this.getPixelValue(channel, y, x), maskCondition, 0);
    channels[1] = pixelProcessor((x, y) -> this.getPixelValue(channel, y, x), maskCondition, 1);
    channels[2] = pixelProcessor((x, y) -> this.getPixelValue(channel, y, x), maskCondition, 2);

    return channelComponent;

  }


  @Override
  public Image flip(String direction) {
    int width = this.getWidth();
    int height = this.getHeight();

    RGBImage flippedImage = new RGBImage(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        if (direction.equals("horizontal")) {
          // Flip along the horizontal axis (reverse columns)
          flippedImage.setPixelValue(0, y, width - 1 - x, this.getPixelValue(0, y, x));
          flippedImage.setPixelValue(1, y, width - 1 - x, this.getPixelValue(1, y, x));

          flippedImage.setPixelValue(2, y, width - 1 - x, this.getPixelValue(2, y, x));


        } else if (direction.equals("vertical")) {
          // Flip along the vertical axis (reverse rows)
          flippedImage.setPixelValue(0, height - 1 - y, x, this.getPixelValue(0, y, x));
          flippedImage.setPixelValue(1, height - 1 - y, x, this.getPixelValue(1, y, x));
          flippedImage.setPixelValue(2, height - 1 - y, x, this.getPixelValue(2, y, x));


        }
      }
    }

    return flippedImage;
  }


  @Override
  public Image valueComponent(Image mask) {
    RGBImage valueComponent = new RGBImage(getWidth(), getHeight());

    int[][][] channels = valueComponent.getChannels();

    BiPredicate<Integer, Integer> maskCondition = (x, y) -> {
      if (mask == null) {
        return true;
      }
      RGBImage gray = (RGBImage) mask;
      return !(gray.getPixelValue(0, y, x) == 255 &&
              gray.getPixelValue(1, y, x) == 255 &&
              gray.getPixelValue(2, y, x) == 255);
    };

    channels[0] = pixelProcessor((x, y) -> Math.max(
            this.getPixelValue(0, y, x),
            Math.max(this.getPixelValue(1, y, x), this.getPixelValue(2, y, x))
    ), maskCondition, 0);

    channels[1] = channels[0];
    channels[2] = channels[0];

    return valueComponent;

  }

  @Override
  public Image intensityComponent(Image mask) {

    RGBImage intensityComponent = new RGBImage(getWidth(), getHeight());

    int[][][] channels = intensityComponent.getChannels();

    BiPredicate<Integer, Integer> maskCondition = (x, y) -> {
      if (mask == null) {
        return true;
      }
      RGBImage gray = (RGBImage) mask;
      return !(gray.getPixelValue(0, y, x) == 255 &&
              gray.getPixelValue(1, y, x) == 255 &&
              gray.getPixelValue(2, y, x) == 255);
    };

    channels[0] = pixelProcessor((x, y) -> (this.getPixelValue(0, y, x) +
            this.getPixelValue(1, y, x) +
            this.getPixelValue(2, y, x)) / 3, maskCondition, 0);

    channels[1] = channels[0];
    channels[2] = channels[0];

    return intensityComponent;


  }


  @Override
  public Image sepia(Image mask) {

    RGBImage sepiaImage = new RGBImage(getWidth(), getHeight());

    int[][][] channels = sepiaImage.getChannels();

    // Mask condition for grayscale check
    BiPredicate<Integer, Integer> maskCondition = (x, y) -> {
      if (mask == null) {
        return true;
      }
      RGBImage gray = (RGBImage) mask;
      return !(gray.getPixelValue(0, y, x) == 255 &&
              gray.getPixelValue(1, y, x) == 255 &&
              gray.getPixelValue(2, y, x) == 255);
    };

    // Apply the sepia transformation with the mask
    channels[0] = pixelProcessor((x, y) -> clamp((int) (
            0.393 * this.getPixelValue(0, y, x) +
                    0.769 * this.getPixelValue(1, y, x) +
                    0.189 * this.getPixelValue(2, y, x)
    )), maskCondition, 0);

    channels[1] = pixelProcessor((x, y) -> clamp((int) (
            0.349 * this.getPixelValue(0, y, x) +
                    0.686 * this.getPixelValue(1, y, x) +
                    0.168 * this.getPixelValue(2, y, x)
    )), maskCondition, 1);

    channels[2] = pixelProcessor((x, y) -> clamp((int) (
            0.272 * this.getPixelValue(0, y, x) +
                    0.534 * this.getPixelValue(1, y, x) +
                    0.131 * this.getPixelValue(2, y, x)
    )), maskCondition, 2);

    return sepiaImage;

  }


  @Override
  public Image lumaComponent(Image mask) {

    RGBImage lumaComponent = new RGBImage(getWidth(), getHeight());

    int[][][] channels = lumaComponent.getChannels();

    BiPredicate<Integer, Integer> maskCondition = (x, y) -> {
      if (mask == null) {
        return true;
      }
      RGBImage gray = (RGBImage) mask;
      return !(gray.getPixelValue(0, y, x) == 255 &&
              gray.getPixelValue(1, y, x) == 255 &&
              gray.getPixelValue(2, y, x) == 255);
    };

    channels[0] = pixelProcessor((x, y) -> (int) (
            0.2126 * this.getPixelValue(0, y, x) +
                    0.7152 * this.getPixelValue(1, y, x) +
                    0.0722 * this.getPixelValue(2, y, x)
    ), maskCondition, 0);

    channels[1] = channels[0];
    channels[2] = channels[0];

    return lumaComponent;


  }

  /* Clamp the values between 0 and 255 */
  private int clamp(int value) {
    return Math.max(0, Math.min(255, value));
  }


  @Override
  public Image brighten(int increment) {

    if (increment > 255 || increment < -255) {
      throw new IllegalArgumentException("Increment must be between -255 and 255");
    }


    int width = this.getWidth();
    int height = this.getHeight();

    RGBImage brightenImage = new RGBImage(width, height);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        for (int k = 0; k < 3; k++) {
          int newValue = clamp(this.getPixelValue(k, j, i) + increment);
          brightenImage.setPixelValue(k, j, i, newValue);
        }
      }
    }

    return brightenImage;

  }

  @Override
  public List<Image> rgbSplit() {

    List<Image> rgbSplitImages = new ArrayList<Image>();

    rgbSplitImages.add(channelComponent(null, 0));
    rgbSplitImages.add(channelComponent(null, 1));
    rgbSplitImages.add(channelComponent(null, 2));

    return rgbSplitImages;
  }

  @Override
  public Image rgbCombine(Image red, Image green, Image blue) {
    // Cast the input images to RGBImage
    RGBImage redImage = (RGBImage) red;
    RGBImage greenImage = (RGBImage) green;
    RGBImage blueImage = (RGBImage) blue;

    // Create a new RGBImage to store the combined result
    RGBImage rgbCombined = new RGBImage(redImage.getWidth(), redImage.getHeight());

    int width = rgbCombined.getWidth();
    int height = rgbCombined.getHeight();

    // Loop through each pixel in the images
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        // Get pixel values from the respective channel images
        int[] pixelValues = {
                redImage.getPixelValue(0, j, i),
                greenImage.getPixelValue(0, j, i),
                blueImage.getPixelValue(0, j, i)
        };

        // Set the combined pixel values into the new image
        for (int channel = 0; channel < 3; channel++) {
          rgbCombined.setPixelValue(channel, j, i, pixelValues[channel]);
        }
      }
    }

    return rgbCombined;
  }


  @Override
  public Image blur(Image mask) {
    int height = this.getHeight();
    int width = this.getWidth();

    RGBImage maskImage = (RGBImage) mask;

    // Create a new RGBImage to store the blurred result
    RGBImage blurredImage = new RGBImage(width, height);

    // Define a box blur kernel (3x3)
    double[][] kernel = {
            {1 / 16.0, 1 / 8.0, 1 / 16.0},
            {1 / 8.0, 1 / 4.0, 1 / 8.0},
            {1 / 16.0, 1 / 8.0, 1 / 16.0}
    };

    int kernelSize = 3; // 3x3 kernel
    int halfKernel = kernelSize / 2;
    for (int y = halfKernel; y < height - halfKernel; y++) {
      for (int x = halfKernel; x < width - halfKernel; x++) {
        double[] colorSum = new double[3]; // For R, G, B

        boolean shouldBlur = true;

        // If grayImg is not null, check the corresponding grayscale pixel
        if (maskImage != null) {
          // Assuming the grayscale image has only one channel (0 is grayscale)
          if (maskImage.getPixelValue(0, y, x) == 255 &&
                  maskImage.getPixelValue(1, y, x) == 255
                  && maskImage.getPixelValue(2, y, x) == 255) {
            for (int channel = 0; channel < 3; channel++) {
              blurredImage.setPixelValue(channel, y, x, getPixelValue(channel, y, x));
            }
            shouldBlur = false; // Skip sharpening if it's black
          }
        }

        if (shouldBlur) {
          // Loop through the kernel and apply the blur effect
          for (int ky = -halfKernel; ky <= halfKernel; ky++) {
            for (int kx = -halfKernel; kx <= halfKernel; kx++) {
              int newY = y + ky;
              int newX = x + kx;

              // Check if the new pixel is within image bounds
              if (newY >= 0 && newY < height && newX >= 0 && newX < width) {
                for (int channel = 0; channel < 3; channel++) {
                  colorSum[channel] += this.getPixelValue(channel, newY, newX)
                          * kernel[ky + halfKernel][kx + halfKernel];
                }
              }
            }
          }

          // Set the clamped color values to the blurred image
          for (int channel = 0; channel < 3; channel++) {
            blurredImage.setPixelValue(channel, y, x, clamp((int) Math.round(colorSum[channel])));
          }
        } else {
          // If no blur is applied, retain the original pixel values from the original image
          for (int channel = 0; channel < 3; channel++) {
            blurredImage.setPixelValue(channel, y, x, this.getPixelValue(channel, y, x));
          }
        }
      }
    }

    return blurredImage;
  }


  @Override
  public Image sharpen(Image mask) {

    int height = this.getHeight();
    int width = this.getWidth();

    RGBImage gray = (RGBImage) mask;

    // Create a new RGBImage to store the sharpened result
    RGBImage sharpenedImage = new RGBImage(width, height);

    // Define the sharpening kernel
    double[][] kernel = {
            {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0}
    };

    int kernelSize = 5; // 5x5 kernel
    int halfKernel = kernelSize / 2;

    for (int y = halfKernel; y < height - halfKernel; y++) {
      for (int x = halfKernel; x < width - halfKernel; x++) {
        boolean shouldSharpen = true;
        if (gray != null) {
          // Check if grayscale pixel is black (0, 0, 0)
          if (gray.getPixelValue(0, y, x) == 255 && gray.getPixelValue(1, y, x) == 255
                  && gray.getPixelValue(2, y, x) == 255) {
            for (int channel = 0; channel < 3; channel++) {
              sharpenedImage.setPixelValue(channel, y, x, getPixelValue(channel, y, x));
            }
            shouldSharpen = false; // Skip sharpening if it's black
          }
        }

        if (shouldSharpen) {
          double[] colorSum = new double[3]; // For R, G, B

          // Loop through the kernel
          for (int ky = -halfKernel; ky <= halfKernel; ky++) {
            for (int kx = -halfKernel; kx <= halfKernel; kx++) {
              int newY = y + ky;
              int newX = x + kx;

              // Check if the new pixel is within image bounds
              if (newY >= 0 && newY < getHeight() && newX >= 0 && newX < getWidth()) {
                for (int channel = 0; channel < 3; channel++) {
                  colorSum[channel] += getPixelValue(channel, newY, newX)
                          * kernel[ky + halfKernel][kx + halfKernel];
                }
              }
            }
          }

          // Set the clamped color values to the sharpened image
          for (int channel = 0; channel < 3; channel++) {
            sharpenedImage.setPixelValue(channel, y, x, clamp((int) Math.round(colorSum[channel])));
          }
        }

      }
    }

    return sharpenedImage;
  }

}

