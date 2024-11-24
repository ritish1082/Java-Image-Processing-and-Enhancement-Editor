package application.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.JDialog;
import javax.swing.JTextField;


import javax.swing.filechooser.FileNameExtensionFilter;

import application.controller.Features;
import application.model.ViewImage;

import java.util.List;

/**
 * Graphical user interface (GUI) class representing the view of the application.
 */
public class GraphicalView extends JFrame implements IView {

  // Panels
  private final JPanel imagePanel;
  private final JPanel operationsPanel;

  // Labels
  private final JLabel imageLabel;
  private final JLabel histogramLabel;

  // Buttons
  private final JMenuItem openItem;
  private final JMenuItem saveItem;
  private final JMenuItem exitItem;
  private final JButton horizontalFlipButton;
  private final JButton verticalFlipButton;
  private final JButton compressButton;
  private final JButton downscaleButton;
  private final JComboBox grayscaleDropdown;
  private final JComboBox filterDropdown;
  private final JButton applyGreyscale;
  private final JButton applyFilter;
  private final JButton colorCorrectButton;
  private final JButton levelAdjustButton;
  private final JButton sepiaButton;
  private final JButton brightenButton;
  private final JButton ditherButton;

  // Model
  private final ViewImage model;

  // state variables
  private boolean isSaved = true;
  private int currentImageWidth = 1;
  private int currentImageHeight = 1;


  /**
   * Constructs a swing application layout.
   *
   * @param caption the title of the swing application.
   * @param model   the view model, adapter class to get buffered images.
   */
  public GraphicalView(String caption, ViewImage model) {

    super(caption);

    this.model = model;

    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setSize(1000, 800);
    this.setLayout(new BorderLayout());

    // Create the menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu helpMenu = new JMenu("Help");

    // Add menu items to the File menu
    openItem = new JMenuItem("Open");
    saveItem = new JMenuItem("Save");
    exitItem = new JMenuItem("Exit");
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.addSeparator(); // Adds a separator between Save and Exit
    fileMenu.add(exitItem);

    // Disable save if initially as no image is loaded.
    saveItem.setEnabled(false);

    menuBar.add(fileMenu);

    // Set the menu bar for the frame
    this.setJMenuBar(menuBar);

    // Left Panel (Image and Transformations)
    // Panels
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

    // Image Panel with Scrollbar
    imagePanel = new JPanel(new BorderLayout());
    imagePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Image Preview"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    imageLabel = new JLabel("<html>Load an image for editing<br>Operations visible when image" +
            " loaded<br>File > Open...</html>",
            JLabel.CENTER);
    imagePanel.add(imageLabel, BorderLayout.CENTER);

    JScrollPane imageScrollPane = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    leftPanel.add(imageScrollPane);

    // Transformation Panel (Buttons)
    JPanel transformationsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    transformationsPanel.setBorder(BorderFactory.createTitledBorder("Transform"));

    horizontalFlipButton = new JButton("Horizontal Flip");
    verticalFlipButton = new JButton("Vertical Flip");
    downscaleButton = new JButton("Downscale");
    compressButton = new JButton("Compress");

    transformationsPanel.add(horizontalFlipButton);
    transformationsPanel.add(verticalFlipButton);
    transformationsPanel.add(downscaleButton);
    transformationsPanel.add(compressButton);

    leftPanel.add(transformationsPanel);

    // Right Panel (Histogram and Operations)
    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

    // Histogram Panel
    JPanel histogramPanel = new JPanel(new BorderLayout());
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramLabel = new JLabel("Upload image to view histogram", JLabel.CENTER);
    histogramPanel.add(histogramLabel, BorderLayout.CENTER);
    rightPanel.add(histogramPanel); // Add histogram panel to right panel

    // Operations Panel (Image Editing)
    operationsPanel = new JPanel(new GridBagLayout());
    operationsPanel.setBorder(BorderFactory.createTitledBorder("Image Editing"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Greyscale Panel
    JPanel greyscalePanel = new JPanel(new GridLayout(1, 3, 10, 10));
    JLabel grayscaleLabel = new JLabel("Greyscale Type: ");
    grayscaleDropdown = new JComboBox<>(new String[]{
            "Select Option",
            "Red Component",
            "Green Component",
            "Blue Component",
            "Luma",
            "Intensity",
            "Value"
    });
    applyGreyscale = new JButton("Apply Grayscale");
    greyscalePanel.add(grayscaleLabel);
    greyscalePanel.add(grayscaleDropdown);
    greyscalePanel.add(applyGreyscale);

    gbc.gridy = 0;
    operationsPanel.add(greyscalePanel, gbc);

    // Filter Panel
    JPanel filterPanel = new JPanel(new GridLayout(1, 3, 10, 10));
    filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JLabel filterLabel = new JLabel("Filter Type: ");
    filterDropdown = new JComboBox<>(new String[]{"Select Option", "Blur", "Sharpen"});
    applyFilter = new JButton("Apply Filter");
    filterPanel.add(filterLabel);
    filterPanel.add(filterDropdown);
    filterPanel.add(applyFilter);

    gbc.gridy = 1;
    operationsPanel.add(filterPanel, gbc);

    // Editing Panel (Buttons)
    JPanel editingPanel = new JPanel(new GridLayout(1, 5, 10, 10));
    editingPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
    brightenButton = new JButton("Brighten");
    sepiaButton = new JButton("Sepia");
    levelAdjustButton = new JButton("Level Adjust");
    colorCorrectButton = new JButton("Color Correct");
    ditherButton = new JButton("Dither");
    editingPanel.add(brightenButton);
    editingPanel.add(sepiaButton);
    editingPanel.add(levelAdjustButton);
    editingPanel.add(colorCorrectButton);
    editingPanel.add(ditherButton);

    gbc.gridy = 2;
    operationsPanel.add(editingPanel, gbc);

    rightPanel.add(operationsPanel);

    // Main Split Panel (Left and Right)

    JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imageScrollPane,
            transformationsPanel);
    leftSplitPane.setDividerLocation(0.99);
    leftSplitPane.setResizeWeight(0.99);
    leftSplitPane.setContinuousLayout(true);
    leftSplitPane.setDividerSize(0);

    JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, histogramPanel,
            operationsPanel);
    rightSplitPane.setDividerLocation(0.6);
    rightSplitPane.setResizeWeight(0.6);
    rightSplitPane.setContinuousLayout(true);
    rightSplitPane.setDividerSize(0);

    // Main Split Panel (Left and Right)
    JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplitPane,
            rightSplitPane);
    mainSplitPane.setDividerLocation(0.9);
    mainSplitPane.setResizeWeight(0.9);
    mainSplitPane.setContinuousLayout(true);
    mainSplitPane.setDividerSize(0);

    // Add the main split pane to the frame
    this.add(mainSplitPane, BorderLayout.CENTER);

    this.setVisible(true);

    buttonsVisibility(false);
  }


  @Override
  public void addFeatures(Features features) {

    openItem.addActionListener(evt -> loadImage(features));

    saveItem.addActionListener(evt -> saveImage(features));

    horizontalFlipButton.addActionListener(evt -> {
      try {
        features.horizontalFlip();
      } catch (Exception e) {
        showErrorDialog("Error occurred while horizontal flip. Try again");
      }
    });

    verticalFlipButton.addActionListener(evt -> {
      try {
        features.verticalFlip();
      } catch (Exception e) {
        showErrorDialog("Error occurred while horizontal flip. Try again");
      }
    });

    downscaleButton.addActionListener(evt -> downscaleImage(features));

    compressButton.addActionListener(evt -> compressImage(features));

    brightenButton.addActionListener(evt -> brightenImage(features));

    levelAdjustButton.addActionListener(evt -> levelAdjustImage(features));


    ditherButton.addActionListener(evt -> previewDialog(features,
            "Dither Image", "DITHER", scale -> {
              try {
                features.dither();
              } catch (Exception e) {
                showErrorDialog(e.getMessage());
              }
            })
    );

    sepiaButton.addActionListener(evt -> previewDialog(features,
            "Sepia Image", "SEPIA", scale -> {
              try {
                features.sepia();
              } catch (Exception e) {
                showErrorDialog(e.getMessage());
              }
            })
    );

    colorCorrectButton.addActionListener(evt -> previewDialog(features,
            "Color Correct Image", "COLOR-CORRECT", scale -> {
              try {
                features.colorCorrect();
              } catch (Exception e) {
                showErrorDialog(e.getMessage());
              }
            })
    );

    applyFilter.addActionListener(evt -> {
      String selectedOption = (String) filterDropdown.getSelectedItem();

      switch (selectedOption) {
        case "Select Option":
          showErrorDialog("Please select a valid filter type.");
          break;

        case "Blur":
          previewDialog(features, "Blur Image", "BLUR", scale -> {
            try {
              features.blur();
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        case "Sharpen":
          previewDialog(features, "Sharpen Image", "SHARPEN", scale -> {
            try {
              features.sharpen();
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        default:
          throw new IllegalArgumentException("No filter type ");
      }
    });

    applyGreyscale.addActionListener(evt -> {
      String selectedOption = (String) grayscaleDropdown.getSelectedItem();

      switch (selectedOption) {
        case "Select Option":
          showErrorDialog("Please select a valid grayscale type.");
          break;

        case "Red Component":
          previewDialog(features, "Red component image", "RED-COMPONENT", scale -> {
            try {
              features.channelComponent(0);
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        case "Green Component":
          previewDialog(features, "Green component image", "GREEN-COMPONENT", scale -> {
            try {
              features.channelComponent(1);
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        case "Blue Component":
          previewDialog(features, "Blue component image", "BLUE-COMPONENT", scale -> {
            try {
              features.channelComponent(2);
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        case "Luma":
          previewDialog(features, "Luma grayscale image", "LUMA", scale -> {
            try {
              features.lumaGreyscale();
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        case "Intensity":
          previewDialog(features, "Intensity greyscale image", "INTENSITY", scale -> {
            try {
              features.intensityGreyscale();
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        case "Value":
          previewDialog(features, "Value grayscale image", "VALUE", scale -> {
            try {
              features.valueGreyscale();
            } catch (Exception e) {
              showErrorDialog(e.getMessage());
            }
          });
          break;

        default:
          throw new IllegalArgumentException("No grayscale type ");
      }
    });


    exitItem.addActionListener(evt -> exitApplication(features));

    // Overriding the default close operation
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        exitApplication(features);
      }
    });
  }


  /* Get path of the image using file picker and load it using features */
  private void loadImage(Features features) {

    final JFileChooser fileChooser = new JFileChooser();
    final FileNameExtensionFilter filter = new
            FileNameExtensionFilter("JPG, PNG, & PPM Images", "jpg", "png", "ppm");

    fileChooser.setFileFilter(filter);
    int option = fileChooser.showOpenDialog(this);
    if (option == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        features.load(file.getPath());

        imagePanel.setBorder(BorderFactory.createTitledBorder(file.getPath()));
        buttonsVisibility(true);
        isSaved = false;
      } catch (Exception e) {
        showErrorDialog("Provide a valid file. Only PNG, JPG, PPM file formats supported");
      }
    }
  }

  /* Save the image by getting the path from file chooser and using the features */
  private void saveImage(Features features) {

    final JFileChooser fileChooser = new JFileChooser();
    int option = fileChooser.showSaveDialog(this);

    if (option == JFileChooser.APPROVE_OPTION) {

      File file = fileChooser.getSelectedFile();

      try {
        features.save(file.getPath());
        isSaved = true;
      } catch (Exception e) {
        showErrorDialog("Can save only PNG, JPG, PPM file formats");
      }

    }

  }


  private JSlider createSlider(int min, int max, int initial, int majorTick, int minorTick) {
    JSlider slider = new JSlider(min, max, initial);
    slider.setMajorTickSpacing(majorTick);
    slider.setMinorTickSpacing(minorTick);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    return slider;
  }

  private JPanel createLabeledSliderPanel(String labelText, JSlider slider) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    JLabel label = new JLabel(labelText);
    JTextField textField = new JTextField(String.valueOf(slider.getValue()), 5);
    textField.setEditable(false);
    slider.addChangeListener(e -> textField.setText(String.valueOf(slider.getValue())));
    panel.add(label, BorderLayout.WEST);
    panel.add(slider, BorderLayout.CENTER);
    panel.add(textField, BorderLayout.EAST);
    return panel;
  }

  private JDialog showDialog(String title, List<JPanel> panels, List<JButton> buttons,
                             Runnable onCloseAction) {
    JDialog dialog = new JDialog();

    dialog.setTitle(title);
    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    dialog.setLayout(new GridLayout(panels.size() + 1, 1, 10, 10));
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(operationsPanel);

    panels.forEach(dialog::add);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttons.forEach(buttonPanel::add);
    dialog.add(buttonPanel);

    dialog.pack();
    dialog.setVisible(true);

    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (onCloseAction != null) {
          onCloseAction.run();
        }
        dialog.dispose();
      }
    });
    return dialog;
  }

  private void downscaleImage(Features features) {
    JSlider widthSlider = createSlider(1, currentImageWidth, currentImageWidth / 2,
            currentImageWidth / 4, currentImageWidth / 8);
    JSlider heightSlider = createSlider(1, currentImageHeight, currentImageHeight / 2,
            currentImageHeight / 4, currentImageHeight / 8);

    JPanel widthPanel = createLabeledSliderPanel("Width:", widthSlider);
    JPanel heightPanel = createLabeledSliderPanel("Height:", heightSlider);

    JButton downscaleButton = new JButton("Downscale");
    JDialog dialog = showDialog("Downscale Image", List.of(widthPanel, heightPanel),
            List.of(downscaleButton), null);

    downscaleButton.addActionListener(evt -> {
      try {
        features.downScaling(heightSlider.getValue(), widthSlider.getValue());
        dialog.dispose();

      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });

  }


  private void levelAdjustImage(Features features) {
    JSlider black = createSlider(0, 255, 20, 50, 25);
    JSlider mid = createSlider(0, 255, 20, 50, 25);
    JSlider white = createSlider(0, 255, 20, 50, 25);
    JSlider preview = createSlider(0, 100, 50, 25, 5);


    JPanel blackPanel = createLabeledSliderPanel("Black:", black);
    JPanel midPanel = createLabeledSliderPanel("Mid:", mid);
    JPanel whitePanel = createLabeledSliderPanel("White:", white);
    JPanel previewPanel = createLabeledSliderPanel("Preview (in %):", preview);


    JButton applyButton = new JButton("Apply");
    applyButton.addActionListener(evt -> {
      try {
        features.levelAdjust(black.getValue(), mid.getValue(), white.getValue());
      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });

    JButton previewButton = new JButton("Preview");
    previewButton.addActionListener(evt -> {
      try {
        features.preview("LEVEL-ADJUST", new String[]{
                String.valueOf(black.getValue()), String.valueOf(mid.getValue()),
                String.valueOf(white.getValue()), String.valueOf(preview.getValue())
        });
      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });

    showDialog(
            "Level Adjust Image",
            List.of(blackPanel, midPanel, whitePanel, previewPanel),
            List.of(applyButton, previewButton),
            () -> {
              try {
                features.defaultView();
              } catch (IOException ex) {
                showErrorDialog(ex.getMessage());
              }
            }
    );

  }

  private void compressImage(Features features) {
    JSlider compressSlider = createSlider(1, 100, 50, 10, 5);

    JPanel compressPanel = createLabeledSliderPanel("Compress (in %):", compressSlider);

    JButton compressButton = new JButton("Compress");
    System.out.println("Compress clicked");
    compressButton.addActionListener(evt -> {
      try {
        features.compress(compressSlider.getValue());
      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });

    showDialog("Compress Image", List.of(compressPanel), List.of(compressButton), null);
  }

  private void brightenImage(Features features) {
    JSlider brightenSlider = createSlider(-255, 255, 0, 100, 50);
    JSlider previewSlider = createSlider(0, 100, 0, 25, 5);

    JPanel brightenPanel = createLabeledSliderPanel("Brightness value:", brightenSlider);
    JPanel previewPanel = createLabeledSliderPanel("Preview (in %):", previewSlider);

    JButton brightenButton = new JButton("Brighten");
    JButton previewButton = new JButton("Preview");

    previewButton.addActionListener(evt -> {
      try {
        features.preview("BRIGHTEN", new String[]{
                String.valueOf(brightenSlider.getValue()),
                String.valueOf(previewSlider.getValue())
        });
      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });

    JDialog dialog = showDialog(
            "Brighten Image",
            List.of(brightenPanel, previewPanel),
            List.of(brightenButton, previewButton),
            () -> {
              try {
                features.defaultView();
              } catch (IOException ex) {
                showErrorDialog(ex.getMessage());
              }
            }
    );


    brightenButton.addActionListener(evt -> {
      try {
        features.brighten(brightenSlider.getValue());
        dialog.dispose();
      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });
  }


  private void previewDialog(Features features, String title, String action,
                             Consumer<Integer> applyAction) {


    // Create sliders for preview percentage
    JSlider previewSlider = createSlider(0, 100, 0, 25, 50);
    JPanel previewPanel = createLabeledSliderPanel("Preview (%)", previewSlider);

    // Create buttons and add listeners
    JButton applyButton = new JButton("Apply");
    JButton previewButton = new JButton("Preview");

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.add(applyButton);
    buttonPanel.add(previewButton);

    JDialog dialog = showDialog(
            title,
            List.of(previewPanel),
            List.of(applyButton, previewButton),
            () -> {
              try {
                features.defaultView();
              } catch (IOException ex) {
                showErrorDialog(ex.getMessage());
              }
            }
    );


    applyButton.addActionListener(evt -> {
      applyAction.accept(100);
      dialog.dispose();
    });

    previewButton.addActionListener(evt -> {
      try {
        features.preview(action, new String[]{String.valueOf(previewSlider.getValue())});
      } catch (Exception e) {
        showErrorDialog(e.getMessage());
      }
    });

  }

  @Override
  public void refreshScreen(String imageName, String histogram) throws IOException {

    try {

      // get updated image
      Image bufferedImage = model.getBufferedImage(imageName);
      imageLabel.setText("");
      imageLabel.setIcon(new ImageIcon(bufferedImage));

      // get updated histogram
      Image histogramBufferedImage = model.getBufferedImage(histogram);
      histogramLabel.setText("");
      histogramLabel.setIcon(new ImageIcon(histogramBufferedImage));

      // get updated width and height
      currentImageWidth = model.getWidth(imageName);
      currentImageHeight = model.getHeight(imageName);

    } catch (Exception e) {
      showErrorDialog("Unable to display. Provide a valid file");
    }

  }

  @Override
  public void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);

  }

  @Override
  public void exitApplication(Features features) {

    if (isSaved) {
      System.exit(0);
    } else {
      Object[] options = {"Save", "Don't Save", "Cancel"};
      int userChoice = JOptionPane.showOptionDialog(
              this,
              "Image not saved. Do you want to save before exiting?",
              "Exit Application",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.WARNING_MESSAGE,
              null,
              options,
              options[0]
      );

      if (userChoice == 0) { // Save option
        saveImage(features);
        System.exit(0);
      } else if (userChoice == 1) { // Don't Save option
        System.exit(0);
      }
    }
  }


  /* Enable operations */
  private void buttonsVisibility(boolean visible) {
    saveItem.setEnabled(visible);
    horizontalFlipButton.setEnabled(visible);
    verticalFlipButton.setEnabled(visible);
    downscaleButton.setEnabled(visible);
    compressButton.setEnabled(visible);
    applyGreyscale.setEnabled(visible);
    applyFilter.setEnabled(visible);
    brightenButton.setEnabled(visible);
    sepiaButton.setEnabled(visible);
    levelAdjustButton.setEnabled(visible);
    colorCorrectButton.setEnabled(visible);
    ditherButton.setEnabled(visible);
  }

}
