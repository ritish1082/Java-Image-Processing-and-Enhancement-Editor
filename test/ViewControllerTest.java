import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import application.controller.Features;
import application.controller.ViewController;
import application.model.Image;
import application.model.ImageProcessor;
import application.view.IView;

import static org.junit.Assert.assertEquals;

/**
 * A Junit test class for view controller test.
 */
public class ViewControllerTest {

  private Features viewController;
  private StringBuilder modelLogger;
  private StringBuilder viewLogger;

  @Before
  public void setUp() {
    modelLogger = new StringBuilder();
    viewLogger = new StringBuilder();
    IView mockView = new MockView(viewLogger);
    ImageProcessor mockModel = new MockModel(modelLogger);
    viewController = new ViewController(mockModel, mockView);
  }

  @Test
  public void testLoad() throws IOException {
    viewController.load("./res/hatchShell.png");
    assertEquals("Load: current-image\nHistogram: current-imagehistogram\n" +
                    "Features: class application.controller.ViewController\n" +
                    "Refresh: current-imagehistogram\n",
            modelLogger.toString() + viewLogger.toString());
  }

  @Test
  public void testPPMLoad() throws IOException {
    viewController.load("./res/image.ppm");
    assertEquals("Load: current-image\n" +
            "Histogram: current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test(expected = IOException.class)
  public void testInvalidExtension() throws IOException {
    viewController.load("./res/hatchShell.xyz");
  }

  @Test(expected = IOException.class)
  public void testInvalidFilePath() throws IOException {
    viewController.load("./some/path/hatchShell.xyz");
  }


  @Test
  public void testSave() throws IOException {
    viewController.load("./res/hatchShell.png");
    viewController.save("./res/hatchShell.png");
    assertEquals("Load: current-image\n" +
                    "Histogram: current-imagehistogram\n" +
                    "Get image: current-image\n" +
                    "Histogram: current-imagehistogram\n" +
                    "Features: class application.controller.ViewController\n" +
                    "Refresh: current-imagehistogram\n" +
                    "Refresh: current-imagehistogram\n",
            modelLogger.toString() + viewLogger.toString());
  }

  @Test
  public void testPPMSave() throws IOException {
    viewController.load("./res/hatchShell.png");
    viewController.save("./res/hatchShell-ppm.ppm");
    assertEquals("Load: current-image\n" +
                    "Histogram: current-imagehistogram\n" +
                    "Get image: current-image\n" +
                    "Histogram: current-imagehistogram\n" +
                    "Features: class application.controller.ViewController\n" +
                    "Refresh: current-imagehistogram\n" +
                    "Refresh: current-imagehistogram\n",
            modelLogger.toString() + viewLogger.toString());

  }

  @Test(expected = IOException.class)
  public void testInvalidFormatSave() throws IOException {
    viewController.load("./res/hatchShell.png");
    viewController.save("./res/hatchShell.xyz");
  }

  @Test
  public void testBrighten() throws IOException {
    viewController.brighten(50);
    assertEquals("Brighten: 50 current-image current-image\nHistogram: " +
            "current-imagehistogram\n" + "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString()
            + viewLogger.toString());
  }


  @Test
  public void testDarken() throws IOException {
    viewController.brighten(-50);
    assertEquals("Brighten: -50 current-image current-image\n" +
            "Histogram: current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString()
            + viewLogger.toString());
  }


  @Test
  public void testVerticalFlip() throws IOException {
    viewController.verticalFlip();
    assertEquals("Vertical flip: current-image current-image\n" +
            "Histogram: current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString()
            + viewLogger.toString());
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    viewController.horizontalFlip();
    assertEquals("Horizontal flip: current-image current-image\n" +
            "Histogram: current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testLumaGreyscale() throws IOException {
    viewController.lumaGreyscale();
    assertEquals("Luma grayscale: current-image current-image\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString()
            + viewLogger.toString());
  }

  @Test
  public void testIntensityGreyscale() throws IOException {
    viewController.intensityGreyscale();
    assertEquals("Intensity grayscale: current-image current-image\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testValueGreyscale() throws IOException {
    viewController.valueGreyscale();
    assertEquals("Value grayscale: current-image current-image\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testRedComponent() throws IOException {
    viewController.channelComponent(0);
    assertEquals("Channel Component: current-image current-image 0\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testGreenComponent() throws IOException {
    viewController.channelComponent(1);
    assertEquals("Channel Component: current-image current-image 1\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testBlueComponent() throws IOException {
    viewController.channelComponent(2);
    assertEquals("Channel Component: current-image current-image 2\n" +
            "Histogram: current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString()
            + viewLogger.toString());

  }

  @Test
  public void testSepiaTone() throws IOException {
    viewController.sepia();
    assertEquals("Sepia: current-image current-image\nHistogram: current-imagehistogram\n"
            + "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testBlur() throws IOException {
    viewController.blur();
    assertEquals("Blur: current-image current-image\nHistogram: current-imagehistogram\n"
            + "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void testSharpen() throws IOException {
    viewController.sharpen();
    assertEquals("Sharpen: current-image current-image\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() +
            viewLogger.toString());
  }

  @Test
  public void colorCorrect() throws IOException {
    viewController.colorCorrect();
    assertEquals("Color correct: current-image current-image\nHistogram: " +
            "current-imagehistogram\n" +
            "Features: class application.controller.ViewController\n" +
            "Refresh: current-imagehistogram\n", modelLogger.toString() + viewLogger);
  }

  @Test
  public void levelAdjust() throws IOException {
    viewController.levelAdjust(20, 200, 255);
    assertEquals("level adjust: 20 200 255 current-image current-image\n" +
                    "Histogram: current-imagehistogram\n" +
                    "Features: class application.controller.ViewController\n" +
                    "Refresh: current-imagehistogram\n",
            modelLogger.toString() + viewLogger.toString());
  }


  private static class MockView implements IView {

    private final StringBuilder logger;

    public MockView(StringBuilder sb) {
      this.logger = sb;
    }


    @Override
    public void addFeatures(Features features) {
      logger.append("Features: ").append(features.getClass()).append("\n");
    }

    @Override
    public void refreshScreen(String imageName, String histogram) throws IOException {
      logger.append("Refresh: ").append(imageName).append(histogram).append("\n");
    }

    @Override
    public void showErrorDialog(String message) {
      logger.append("Error").append(message).append("\n");

    }

    @Override
    public void exitApplication(Features features) {
      logger.append("Exit: ").append(features.getClass()).append("\n");

    }
  }


  private static class MockModel implements ImageProcessor {

    private final StringBuilder logger;

    private final Map<String, Image> imagesMap;


    public MockModel(StringBuilder sb) {
      this.logger = sb;
      this.imagesMap = new HashMap<>();
    }


    @Override
    public void load(String imgName, Image imageName) {
      imagesMap.put(imgName, imageName);
      logger.append("Load: ").append(imgName).append("\n");
    }

    @Override
    public Image getImage(String imageName) throws IllegalArgumentException {
      logger.append("Get image: ").append(imageName).append("\n");
      return imagesMap.get(imageName);
    }

    @Override
    public void sepia(String[] args) {
      logger.append("Sepia: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void channelComponent(String[] args, int channel) {
      logger.append("Channel Component: ").append(String.join(" ", args)).
              append(" ").append(channel).append("\n");
    }

    @Override
    public void lumaGreyscale(String[] args) {
      logger.append("Luma grayscale: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void valueGreyscale(String[] args) {
      logger.append("Value grayscale: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void intensityGreyscale(String[] args) {
      logger.append("Intensity grayscale: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void brighten(String[] args) {
      logger.append("Brighten: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void blur(String[] args) {
      logger.append("Blur: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void sharpen(String[] args) {
      logger.append("Sharpen: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void horizontalFlip(String imgName, String destImgName) {
      logger.append("Horizontal flip: ").append(imgName).append(" ").append(destImgName).
              append("\n");
    }

    @Override
    public void verticalFlip(String imgName, String destImgName) {
      logger.append("Vertical flip: ").append(imgName).append(" ").append(destImgName).
              append("\n");

    }

    @Override
    public void rgbSplit(String imgName, String destRedImgName, String destGreenImageName,
                         String destBlueImgName) {

      logger.append("RGB split").append(imgName).append(destRedImgName).append(destGreenImageName).
              append(destBlueImgName).append("\n");
    }

    @Override
    public void rgbCombine(String redImgName, String greenImageName, String blueImgName,
                           String destImgName) {
      logger.append("RGB split").append(redImgName).append(greenImageName).append(blueImgName).
              append(destImgName).append("\n");

    }

    @Override
    public void colorCorrect(String[] args) {
      logger.append("Color correct: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void compress(double percentage, String imgName, String destImgName) {
      logger.append("Compress: ").append(percentage).append(imgName).append(destImgName).
              append("\n");
    }

    @Override
    public void histogram(String imgName, String destImgName) {
      logger.append("Histogram: ").append(imgName).append(destImgName).append("\n");
    }

    @Override
    public void levelsAdjust(String[] args) {
      logger.append("level adjust: ").append(String.join(" ", args)).append("\n");
    }

    @Override
    public void downScaling(int newHeight, int newWidth, String imageName,
                            String destinationImageName) {
      logger.append("Downscaling: ").append(newHeight).append(newWidth).append("\n");
    }
  }

}
