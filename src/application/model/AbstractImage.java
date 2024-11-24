package application.model;

/**
 * An abstract class which can represent an N-channel image.
 * In this application, an image can contain multiple channels each represented as a 2D matrix of
 * integer values.
 */
public abstract class AbstractImage implements Image {

  public int[][][] channels;
  private int width;
  private int height;
  private int numberOfChannels;

  /**
   * Constructor initializes an abstract image with the given parameters.
   *
   * @param numberOfChannels the number of channels this image contains.
   * @param width            the width of the image.
   * @param height           the height of the image.
   */
  public AbstractImage(int numberOfChannels, int width, int height) {
    this.numberOfChannels = numberOfChannels;
    this.width = width;
    this.height = height;
    channels = new int[numberOfChannels][height][width];
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int[][][] getChannels() {
    return channels;
  }

  /**
   * Returns the number of channels in an image.
   *
   * @return the number of channels
   */
  protected int getNumberOfChannels() {
    return numberOfChannels;
  }

  /**
   * Returns the specific pixel value at the given channel, row and column.
   *
   * @param channel the channel
   * @param row     the row of the image
   * @param col     the column of the image
   * @return the pixel value at the given channel, row, col
   */


  public int getPixelValue(int channel, int row, int col) {
    return channels[channel][row][col];
  }


  /**
   * Sets the value at the given channel, row and column.
   *
   * @param channel the channel
   * @param row     the row of the image
   * @param col     the column of the image
   * @param value   the value to be set at the given channel,row, col
   */
  public void setPixelValue(int channel, int row, int col, int value) {
    channels[channel][row][col] = value;
  }


}
