package application.controller;

import java.io.IOException;

import application.controller.commands.Blur;
import application.controller.commands.Brighten;
import application.controller.commands.ChannelComponent;
import application.controller.commands.ColorCorrect;
import application.controller.commands.Compress;
import application.controller.commands.Dither;
import application.controller.commands.Downscaling;
import application.controller.commands.Flip;
import application.controller.commands.Histogram;
import application.controller.commands.ImageCommand;
import application.controller.commands.Intensity;
import application.controller.commands.LevelsAdjust;
import application.controller.commands.Load;
import application.controller.commands.Luma;
import application.controller.commands.Save;
import application.controller.commands.Sepia;
import application.controller.commands.Sharpen;
import application.controller.commands.Value;
import application.model.ImageProcessor;
import application.view.IView;

/**
 * This class represents a controller for view.
 */
public class ViewController implements Features {

  //  private final ExtendedImageProcessor processor;
  private final String currentImage;
  private final String splitImage;
  private final String splitHistogram;
  private final String histogram;
  private final IView view;
  private final ImageProcessor model;

  /**
   * Constructs a new ViewController to manage interactions between the model and the view.
   * This controller facilitates user commands and updates the view based on model operations.
   *
   * @param model the model that performs image processing operations.
   * @param view  the  instance that displays the user interface and receives user input.
   */
  public ViewController(ImageProcessor model, IView view) {

    this.model = model;
    this.view = view;
    this.currentImage = "current-image";
    this.splitImage = "split-image";
    this.histogram = "histogram";
    this.splitHistogram = "split-histogram";

    view.addFeatures(this);

  }

  /* Executes commands via the CMD pattern */
  private void executeCommand(ImageCommand command) throws IOException {
    try {
      command.execute(model);
      String[] args = {currentImage, histogram};
      ImageCommand histogram = new Histogram(args);
      histogram.execute(model);
      reloadView();
    } catch (Exception e) {
      throw new IOException(e);
    }
  }


  @Override
  public void load(String filepath) throws IOException {
    String[] args = {filepath, currentImage};
    executeCommand(new Load(args));
  }

  @Override
  public void save(String filepath) throws IOException {
    String[] args = {filepath, currentImage};
    executeCommand(new Save(args));
  }

  @Override
  public void channelComponent(int channel) throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ChannelComponent(args, channel));
  }

  @Override
  public void verticalFlip() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Flip(args, "vertical"));
  }

  @Override
  public void horizontalFlip() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Flip(args, "horizontal"));
  }

  @Override
  public void downScaling(int newHeight, int newWidth) throws IOException {
    String[] args = {
            String.valueOf(newHeight),
            String.valueOf(newWidth),
            currentImage,
            currentImage
    };

    executeCommand(new Downscaling(args));
  }


  @Override
  public void blur() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Blur(args));
  }

  @Override
  public void brighten(int value) throws IOException {
    String[] args = {String.valueOf(value), currentImage, currentImage};
    executeCommand(new Brighten(args));
  }


  @Override
  public void sharpen() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Sharpen(args));
  }

  @Override
  public void lumaGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Luma(args));
  }


  @Override
  public void valueGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Value(args));
  }

  @Override
  public void intensityGreyscale() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Intensity(args));
  }

  @Override
  public void dither() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Dither(args));
  }

  @Override
  public void sepia() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new Sepia(args));
  }

  @Override
  public void compress(double percentage) throws IOException {
    String[] args = {String.valueOf(percentage), currentImage, currentImage};
    executeCommand(new Compress(args));

  }

  @Override
  public void colorCorrect() throws IOException {
    String[] args = {currentImage, currentImage};
    executeCommand(new ColorCorrect(args));
  }

  @Override
  public void levelAdjust(int b, int m, int w) throws IOException {
    String[] args = {
            String.valueOf(b),
            String.valueOf(m),
            String.valueOf(w),
            currentImage,
            currentImage
    };

    executeCommand(new LevelsAdjust(args));
  }


  @Override
  public void reloadView() throws IOException {
    view.refreshScreen(currentImage, histogram);
  }

  @Override
  public void exitProgram() throws IOException {
    System.exit(0);
  }


  @Override
  public void preview(String command, String[] args) throws IOException {
    ImageCommand cmd;
    String[] commandArgs = new String[]{currentImage, splitImage, "split", args[0]};

    switch (command) {
      case "BRIGHTEN":
        commandArgs = new String[]{args[0], currentImage, splitImage, "split", args[1]};
        cmd = new Brighten(commandArgs);
        break;

      case "SEPIA":
        cmd = new Sepia(commandArgs);
        break;

      case "COLOR-CORRECT":
        cmd = new ColorCorrect(commandArgs);
        break;

      case "BLUR":
        cmd = new Blur(commandArgs);
        break;

      case "SHARPEN":
        cmd = new Sharpen(commandArgs);
        break;

      case "RED-COMPONENT":
        cmd = new ChannelComponent(commandArgs, 0);
        break;

      case "GREEN-COMPONENT":
        cmd = new ChannelComponent(commandArgs, 1);
        break;

      case "BLUE-COMPONENT":
        cmd = new ChannelComponent(commandArgs, 2);
        break;

      case "LUMA":
        cmd = new Luma(commandArgs);
        break;

      case "VALUE":
        cmd = new Value(commandArgs);
        break;

      case "INTENSITY":
        cmd = new Intensity(commandArgs);
        break;

      case "DITHER":
        cmd = new Dither(commandArgs);
        break;

      case "LEVEL-ADJUST":
        commandArgs = new String[]{
                args[0],
                args[1],
                args[2],
                currentImage,
                splitImage,
                "split",
                args[3]
        };

        cmd = new LevelsAdjust(commandArgs);
        break;

      default:
        throw new IllegalArgumentException("No command");
    }

    // Execute the main command
    cmd.execute(model);

    // Execute histogram and update the view
    new Histogram(new String[]{splitImage, splitHistogram}).execute(model);
    view.refreshScreen(splitImage, splitHistogram);
  }


  public void defaultView() throws IOException {
    reloadView();
  }

}
