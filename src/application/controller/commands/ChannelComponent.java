package application.controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;

import application.model.ImageProcessor;

/**
 * The Channel component command is used to visualize a specific channel in  the image.
 */
public class ChannelComponent implements ImageCommand {
  private final String[] args;
  private final int channel;

  /**
   * Constructs a channel component command with the provided args and channel.
   *
   * @param args the args provided to the command.
   * @param channel the channel to visualize.
   */
  public ChannelComponent(String[] args, int channel) {
    if (args.length < 2) {
      throw new InputMismatchException("Invalid number of arguments in flip command");
    }

    this.args = args;
    this.channel = channel;
  }

  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.channelComponent(args, channel);
  }

}
