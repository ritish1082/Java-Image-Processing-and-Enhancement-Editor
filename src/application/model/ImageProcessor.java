package application.model;

/**
 * This interface represents the core functionality for processing and manipulating images.
 */
public interface ImageProcessor {

  /**
   * Loads an image from the provided input stream and associates it with the given name.
   *
   * @param imgName   the name to associate with the loaded image.
   * @param imageName the input stream containing the image data.
   */
  void load(String imgName, Image imageName);


  /**
   * Get the image with specific image name provided.
   *
   * @param imageName the identifier of the image.
   * @return an Image mapped to the image identifier.
   * @throws IllegalArgumentException if the identifier has no image mapped to it.
   */
  Image getImage(String imageName) throws IllegalArgumentException;

  /**
   * Applies a sepia tone effect to the image specified in the arguments.
   *
   * @param args an array of strings containing image-related arguments.
   */
  void sepia(String[] args);

  /**
   * Extracts a specified channel component from the image.
   *
   * @param args    an array of strings containing image-related arguments.
   * @param channel the channel to extract.
   */
  void channelComponent(String[] args, int channel);

  /**
   * Converts the image to a grayscale based on the luma component.
   *
   * @param args an array of strings containing argument related to luma operation.
   */
  void lumaGreyscale(String[] args);

  /**
   * Converts the image to a grayscale based on the value component.
   *
   * @param args an array of strings containing argument related to value operation.
   */
  void valueGreyscale(String[] args);

  /**
   * Converts the image to a grayscale based on the intensity component.
   *
   * @param args an array of strings containing argument related to intensity operation.
   */
  void intensityGreyscale(String[] args);

  /**
   * Brightens or darkens the image by the specified increment.
   *
   * @param args an array of strings containing argument related to brighten operation.
   */
  void brighten(String[] args);

  /**
   * Applies a blur effect to the image specified in the arguments.
   *
   * @param args an array of strings containing argument related to blur operation.
   */
  void blur(String[] args);

  /**
   * Applies a sharpening effect to the image specified in the arguments.
   *
   * @param args an array of strings containing argument related to sharpen operation.
   */
  void sharpen(String[] args);

  /**
   * Flips the image horizontally.
   *
   * @param imgName     the name of the source image.
   * @param destImgName the name for the resulting image.
   */
  void horizontalFlip(String imgName, String destImgName);

  /**
   * Flips the image vertically.
   *
   * @param imgName     the name of the source image.
   * @param destImgName the name for the resulting image.
   */
  void verticalFlip(String imgName, String destImgName);

  /**
   * Splits the image into its red, green, and blue component images.
   *
   * @param imgName            the name of the source image.
   * @param destRedImgName     the name for the red component image.
   * @param destGreenImageName the name for the green component image.
   * @param destBlueImgName    the name for the blue component image.
   */
  void rgbSplit(String imgName, String destRedImgName, String destGreenImageName,
                String destBlueImgName);

  /**
   * Combines red, green, and blue component images into a single image.
   *
   * @param redImgName     the name of the red component image.
   * @param greenImageName the name of the green component image.
   * @param blueImgName    the name of the blue component image.
   * @param destImgName    the name for the combined image.
   */
  void rgbCombine(String redImgName, String greenImageName, String blueImgName,
                  String destImgName);

  /**
   * Applies a color correction to the image specified in the arguments.
   *
   * @param args an array of strings containing argument related to color correct operation.
   */
  void colorCorrect(String[] args);

  /**
   * Compresses the image to the specified percentage.
   *
   * @param percentage  the compression percentage (0-100).
   * @param imgName     the name of the source image.
   * @param destImgName the name for the resulting compressed image.
   */
  void compress(double percentage, String imgName, String destImgName);

  /**
   * Generates a histogram for the given image and outputs it as a separate image.
   *
   * @param imgName     the name of the source image.
   * @param destImgName the name for the histogram image.
   */
  void histogram(String imgName, String destImgName);

  /**
   * Adjusts the levels of the image based on the provided arguments.
   *
   * @param args an array of strings containing argument related to level adjust operation.
   */
  void levelsAdjust(String[] args);

  /**
   * Downscale an image to the given width and height.
   *
   * @param newHeight            the height to be downscaled to.
   * @param newWidth             the width to be downscaled to.
   * @param imageName            the name of the image to be downscaled.
   * @param destinationImageName the downscaled image.
   */
  void downScaling(int newHeight, int newWidth, String imageName, String destinationImageName);


  /**
   * Apply a dither tone to the image.
   *
   * @param args an array of strings containing argument related to dither operation.
   */
  void dither(String[] args);


}
