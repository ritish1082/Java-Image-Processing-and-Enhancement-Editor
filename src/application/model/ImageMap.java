package application.model;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

/**
 * The image map stores a collection of images and offers methods for editing images.
 * This class is exposed to the controller for operations o the model.
 */
public class ImageMap implements ImageProcessor {


  private final Map<String, Image> imagesMap;


  /**
   * Constructs a map to store images with key as image identifiers and values as Image.
   */
  public ImageMap() {
    this.imagesMap = new HashMap<>();
  }


  @Override
  public Image getImage(String imageName) throws IllegalArgumentException {
    if (this.imagesMap.get(imageName) == null) {
      throw new IllegalArgumentException("Image Not Found: " + imageName);
    }
    return this.imagesMap.get(imageName);
  }

  /* Helper function to insert the image in the map */
  private void putImage(String imgName, Image image) throws IllegalArgumentException {
    this.imagesMap.put(imgName, image);
  }

  @Override
  public void load(String imgName, Image image) {

    this.putImage(imgName, image);
  }


  @Override
  public void sepia(String[] args) {
    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image sepiaImage;
    String destinationImage;

    if (args.length == 2) {
      sepiaImage = currentImage.sepia(null);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      sepiaImage = currentImage.sepia(maskImage);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("MAsk not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      sepiaImage = this.splitView(currentImage, currentImage.sepia(null), widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for sepia. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, sepiaImage);

  }


  @Override
  public void channelComponent(String[] args, int channel) {


    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image channelImage;
    String destinationImage;

    if (args.length == 2) {
      channelImage = currentImage.channelComponent(null, channel);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      channelImage = currentImage.channelComponent(maskImage, channel);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("MAsk not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      channelImage = this.splitView(currentImage,
              currentImage.channelComponent(null, channel), widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for sepia. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, channelImage);

  }

  @Override
  public void lumaGreyscale(String[] args) {

    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image lumaImage;
    String destinationImage;

    if (args.length == 2) {
      lumaImage = currentImage.lumaComponent(null);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      lumaImage = currentImage.lumaComponent(maskImage);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("MAsk not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      lumaImage = this.splitView(currentImage, currentImage.lumaComponent(null),
              widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for sepia. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, lumaImage);

  }

  @Override
  public void valueGreyscale(String[] args) {

    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image valueImage;
    String destinationImage;

    if (args.length == 2) {
      valueImage = currentImage.valueComponent(null);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      valueImage = currentImage.valueComponent(maskImage);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("MAsk not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      valueImage = this.splitView(currentImage, currentImage.valueComponent(null),
              widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for value. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, valueImage);

  }

  @Override
  public void intensityGreyscale(String[] args) {

    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image intensityImage;
    String destinationImage;

    if (args.length == 2) {
      intensityImage = currentImage.intensityComponent(null);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      intensityImage = currentImage.intensityComponent(maskImage);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("MAsk not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      intensityImage = this.splitView(currentImage, currentImage.intensityComponent(null),
              widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for sepia. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, intensityImage);

  }

  @Override
  public void brighten(String[] args) {


    int increment = Integer.parseInt(args[0]);
    String imageName = args[1];
    String destinationImage = args[2];

    Image currentImage = this.getImage(imageName);
    Image brightenImage = currentImage.brighten(increment);


    if (args.length > 3) {
      String split = args[3];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid arguments provided for split view in brighten.");
      }

      float widthPercentage = Float.parseFloat(args[4]);

      brightenImage = this.splitView(currentImage, brightenImage, widthPercentage);
    }


    this.putImage(destinationImage, brightenImage);

  }

  @Override
  public void blur(String[] args) {
    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image blurImage;
    String destinationImage;

    if (args.length == 2) {
      blurImage = currentImage.blur(null);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      blurImage = currentImage.blur(maskImage);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("Mask not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      blurImage = this.splitView(currentImage, currentImage.blur(null), widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for blur. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, blurImage);


  }

  @Override
  public void sharpen(String[] args) {

    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    Image sharpenImage;
    String destinationImage;

    if (args.length == 2) {
      sharpenImage = currentImage.sharpen(null);
      destinationImage = args[1];
    } else if (args.length == 3) {
      Image maskImage = getImage(args[1]);
      sharpenImage = currentImage.sharpen(maskImage);
      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("Mask not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      sharpenImage = this.splitView(currentImage, currentImage.sharpen(null), widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for blur. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, sharpenImage);


  }

  @Override
  public void horizontalFlip(String imgName, String destImgName) {
    this.putImage(destImgName, this.getImage(imgName).flip("horizontal"));

  }

  @Override
  public void verticalFlip(String imgName, String destImgName) {
    this.putImage(destImgName, this.getImage(imgName).flip("vertical"));

  }

  @Override
  public void rgbSplit(String imageName, String redImageName, String greenImageName,
                       String blueImageName) {

    Image currentImage = this.getImage(imageName);

    List<Image> images = currentImage.rgbSplit();

    this.putImage(redImageName, images.get(0));
    this.putImage(greenImageName, images.get(1));
    this.putImage(blueImageName, images.get(2));

  }

  @Override
  public void rgbCombine(String redImageName, String greenImageName, String blueImageName,
                         String destImgName) {

    Image redImage = this.getImage(redImageName);
    Image greenImage = this.getImage(greenImageName);
    Image blueImage = this.getImage(blueImageName);

    Image rgbCombined = redImage.rgbCombine(redImage, greenImage, blueImage);

    this.putImage(destImgName, rgbCombined);

  }

  @Override
  public void colorCorrect(String[] args) {

    String imageName = args[0];
    String destinationImage = args[1];

    Image sourceImage = this.getImage(imageName);

    ExtendedImage extendedRGB = this.getExtendedImage(sourceImage);
    Image colorCorrected = extendedRGB.colorCorrect();


    if (args.length > 2) {
      String split = args[2];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid arguments provided for split view in intensity.");
      }

      float widthPercentage = Float.parseFloat(args[3]);

      colorCorrected = this.splitView(sourceImage, colorCorrected, widthPercentage);
    }


    this.putImage(destinationImage, colorCorrected);

  }

  @Override
  public void compress(double percentage, String imgName, String destImgName) {

    Image sourceImage = this.getImage(imgName);
    ExtendedImage extendedRGB = this.getExtendedImage(sourceImage);

    this.putImage(destImgName, extendedRGB.compress(percentage));

  }

  @Override
  public void histogram(String imgName, String destImgName) {
    Image sourceImage = this.getImage(imgName);
    ExtendedImage extendedRGB = this.getExtendedImage(sourceImage);
    this.putImage(destImgName, extendedRGB.histogram());
  }

  @Override
  public void levelsAdjust(String[] args) {

    int black = 0;
    int mid = 0;
    int white = 0;
    try {
      black = Integer.parseInt(args[0]);
      mid = Integer.parseInt(args[1]);
      white = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      throw new InputMismatchException("Invalid arguments for black, mid or white.");
    }

    String imgName = args[3];
    String destImgName = args[4];

    Image currentImage = this.getImage(imgName);
    ExtendedImage imageV2 = this.getExtendedImage(currentImage);
    Image levelAdjusted = imageV2.levelsAdjust(black, mid, white);

    if (args.length > 5) {
      String split = args[5];
      if (!split.equals("split")) {
        throw new InputMismatchException("Invalid args for Split view for Levels adjust.");
      }
      float widthPercentage = Float.parseFloat(args[6]);
      levelAdjusted = this.splitView(currentImage, levelAdjusted, widthPercentage);
    }

    this.putImage(destImgName, levelAdjusted);

  }

  @Override
  public void downScaling(int newHeight, int newWidth, String imageName,
                          String destinationImageName) {

    Image sourceImage = this.getImage(imageName);
    ExtendedImage extendedRGB = this.getExtendedImage(sourceImage);
    this.putImage(destinationImageName, extendedRGB.downScaling(newHeight, newWidth));

  }


  public void dither(String[] args) {

    String imageName = args[0];
    Image currentImage = this.getImage(imageName);
    ExtendedImage extendedRGB = this.getExtendedImage(currentImage);

    Image ditherImage;
    String destinationImage;

    if (args.length == 2) {
      ditherImage = extendedRGB.dither();
      destinationImage = args[1];
//    } else if (args.length == 3) {
//      Image maskImage = getImage(args[1]);
//      lumaImage = currentImage.lumaComponent(maskImage);
//      destinationImage = args[2];
    } else if (args.length == 4) {
      if (!"split".equals(args[2])) {
        throw new InputMismatchException("Mask not found.");
      }
      float widthPercentage = Float.parseFloat(args[3]);
      ditherImage = this.splitView(currentImage, extendedRGB.dither(),
              widthPercentage);
      destinationImage = args[1];
    } else {
      throw new IllegalArgumentException("Invalid number of arguments for sepia. " +
              "Expected 2, 3, or 4 arguments.");
    }

    this.putImage(destinationImage, ditherImage);

  }


  /* Split function to preview image operations*/
  private Image splitView(Image currentImage, Image filteredImage, float widthPercentage) {

    ExtendedRGB previewImage = new ExtendedRGB(currentImage.getWidth(), filteredImage.getHeight());

    int percentageWidth = (int) (currentImage.getWidth() * (widthPercentage / 100));

    int[][][] filteredChannels = filteredImage.getChannels();
    int[][][] originalChannels = currentImage.getChannels();

    for (int i = 0; i < currentImage.getHeight(); i++) {
      for (int j = 0; j < currentImage.getWidth(); j++) {
        for (int channel = 0; channel < 3; channel++) {
          if (j == percentageWidth) {
            previewImage.setPixelValue(channel, i, j, 0);
          } else if (j < percentageWidth) {
            previewImage.setPixelValue(channel, i, j, filteredChannels[channel][i][j]);
          } else {
            previewImage.setPixelValue(channel, i, j, originalChannels[channel][i][j]);
          }
        }
      }
    }
    return previewImage;

  }


  /* Converts an Image to ExtendedImage when given an Image object  */
  private ExtendedImage getExtendedImage(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();

    int[][][] channels = image.getChannels();

    ExtendedRGB extendedImage = new ExtendedRGB(width, height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int c = 0; c < 3; c++) {
          extendedImage.setPixelValue(c, i, j, channels[c][i][j]);
        }
      }
    }
    return extendedImage;
  }


}
