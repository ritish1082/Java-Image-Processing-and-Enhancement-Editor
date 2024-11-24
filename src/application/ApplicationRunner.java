package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import application.controller.ImageController;
import application.controller.ViewController;
import application.model.ImageMap;
import application.model.ImageProcessor;
import application.model.ViewImage;
import application.model.ViewModel;
import application.view.GraphicalView;
import application.view.IView;

/**
 * This class is used to start the image processing application.
 * The controller object is used to call the run application method.
 */
public class ApplicationRunner {
  /**
   * The main method of the application which calls the run application method of the controller.
   *
   * @param args arguments passed in the command line.
   */
  public static void main(String[] args) {

    String filePath = null;
    boolean textOption = false;
    boolean fileOption = false;

    for (int i = 0; i < args.length; i++) {

      if (args[i].equals("-file")) {
        fileOption = true;
        if (i + 1 < args.length) {
          filePath = args[++i];
        }
      } else if (args[i].equals("-text")) {
        textOption = true;
      } else {
        System.out.println("Arguments should either be -file or -text option");
      }
    }

    try {
      ImageController controller;

      ImageProcessor model = new ImageMap();

      // -text for CLI
      if (textOption) {
        controller = new ImageController(new InputStreamReader(System.in), System.out);
        System.out.println("Using text option");
        controller.runApplication(model);

      } else if (fileOption) {
        // -file for file scripting
        String commands = readFile(filePath);
        System.out.println("Using file option");
        Readable input = new StringReader(commands);
        controller = new ImageController(input, System.out);
        controller.runApplication(model);
      } else {
        // default graphical view by swing
        ViewImage image = new ViewModel(model);

        IView view = new GraphicalView("Image Editing and Manipulation Application ", image);

        new ViewController(model, view);

      }
    } catch (IllegalArgumentException | IOException e) {
      System.out.println(e.getMessage());
    }

  }

  /* Read file method to read a script file */
  private static String readFile(String filepath) throws IOException {
    File file = new File(filepath);
    FileInputStream fis = new FileInputStream(file);
    Scanner sc = new Scanner(fis);
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();

      // ignore empty lines
      if (s.isEmpty()) {
        continue;
      } // ignore any comments
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    return builder.toString();
  }

}
