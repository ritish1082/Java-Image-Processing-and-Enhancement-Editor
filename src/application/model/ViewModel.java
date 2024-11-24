package application.model;

import java.awt.Image;
import java.awt.image.BufferedImage;


/**
 * An adapter class which converts the image into a buffered image.
 */
public class ViewModel implements ViewImage {


  private final ImageProcessor model;

  /**
   * Creates an adapter class constructor to convert model image to buffered image.
   *
   * @param model the model which contains all the operations ].
   */
  public ViewModel(ImageProcessor model) {
    this.model = model;
  }


  @Override
  public Image getBufferedImage(String imageName) {

    int width = getWidth(imageName);
    int height = getHeight(imageName);
    int[][][] channels = model.getImage(imageName).getChannels();

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = channels[0][i][j];
        int green = channels[1][i][j];
        int blue = channels[2][i][j];
        int rgb = (red << 16) | (green << 8) | blue;
        image.setRGB(j, i, rgb);
      }
    }

    return image;
  }

  @Override
  public int getWidth(String imageName) {
    return model.getImage(imageName).getWidth();
  }

  @Override
  public int getHeight(String imageName) {
    return model.getImage(imageName).getHeight();
  }
}
