package application.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

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
import application.controller.commands.RGBCombine;
import application.controller.commands.RGBSplit;
import application.controller.commands.Save;
import application.controller.commands.Sepia;
import application.controller.commands.Sharpen;
import application.controller.commands.Value;
import application.model.ImageProcessor;


/**
 * This class represents the controller which handles the images.
 * It processes various image operations by coordinating with the model and managing user commands.
 */

public class ImageController implements ImageControllerInterface {

  private final Readable in;
  private final Appendable out;

  private Map<String, Function<String[], ImageCommand>> knownCommands;

  /**
   * Creates an instance of image controller.
   *
   * @param in  a readable object
   * @param out an appendable object
   */
  public ImageController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;


    knownCommands = new HashMap<>();

    knownCommands.put("load", args -> new Load(args));
    knownCommands.put("save", args -> new Save(args));
    knownCommands.put("brighten", args -> new Brighten(args));
    knownCommands.put("horizontal-flip", args -> new Flip(args, "horizontal"));
    knownCommands.put("vertical-flip", args -> new Flip(args, "vertical"));
    knownCommands.put("red-component", args -> new ChannelComponent(args, 0));
    knownCommands.put("green-component", args -> new ChannelComponent(args, 1));
    knownCommands.put("blue-component", args -> new ChannelComponent(args, 2));
    knownCommands.put("rgb-split", args -> new RGBSplit(args));
    knownCommands.put("luma-component", args -> new Luma(args));
    knownCommands.put("value-component", args -> new Value(args));
    knownCommands.put("intensity-component", args -> new Intensity(args));
    knownCommands.put("rgb-combine", args -> new RGBCombine(args));
    knownCommands.put("sepia", args -> new Sepia(args));
    knownCommands.put("blur", args -> new Blur(args));
    knownCommands.put("sharpen", args -> new Sharpen(args));
    knownCommands.put("compress", args -> new Compress(args));
    knownCommands.put("histogram", args -> new Histogram(args));
    knownCommands.put("color-correct", args -> new ColorCorrect(args));
    knownCommands.put("levels-adjust", args -> new LevelsAdjust(args));
    knownCommands.put("downscale", args -> new Downscaling(args));
    knownCommands.put("dither", args -> new Dither(args));


  }


  @Override
  public void runApplication(ImageProcessor model) throws IOException {
    Scanner scan = new Scanner(this.in);

    while (scan.hasNextLine()) {
      try {
        ImageCommand command;
        String input = scan.nextLine();

        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
          out.append("Exiting application");
          return;
        }

        String[] tokens = input.split("\\s+");

        String getCommand = tokens[0];

        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        Function<String[], ImageCommand> cmd =
                knownCommands.getOrDefault(getCommand, null);

        if (cmd == null) {
          throw new IllegalArgumentException("Please provide a valid command.");
        } else {
          command = cmd.apply(args);
          command.execute(model);
          out.append("Command performed: ").append(input).append("\n");
        }
      } catch (Exception e) {
        out.append(e.getMessage());
      }
    }

  }


}

