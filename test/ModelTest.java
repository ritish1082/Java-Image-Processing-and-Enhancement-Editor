import static org.junit.Assert.assertEquals;


import org.junit.Test;

import application.model.ExtendedRGB;
import application.model.Image;

/**
 * A Junit test for testing model.
 * The images generated in this test are present in the test folder.
 */
public class ModelTest {

  private ExtendedRGB model;


  int[][][] image = new int[][][]{

          {{0, 64, 128, 192}, {64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}},
          {{255, 192, 128, 64}, {192, 128, 64, 0}, {128, 64, 0, 255}, {64, 0, 255, 192}},
          {{64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}, {255, 0, 64, 128}}
  };


  @Test
  public void compressTest() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image compressedImage = model.compress(80);

    int[][][] array = compressedImage.getChannels();

    int[][][] actual = new int[][][]{
            {{132, 132, 132, 132}, {132, 132, 132, 132}, {132, 132, 255, 52}, {132, 132, 52, 116}},
            {{215, 215, 128, 64}, {152, 152, 64, 0}, {128, 64, 184, 184}, {64, 0, 184, 184}},
            {{168, 168, 232, 136}, {168, 168, 136, 40}, {232, 136, 104, 104}, {136, 40, 104, 104}}
    };

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(actual[k][i][j], array[k][i][j]);
        }
      }
    }

  }

  @Test
  public void levelsAdjust() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }
    Image compressedImage = model.levelsAdjust(20, 120, 255);

    int[][][] expectedChannels = compressedImage.getChannels();


    int[][][] actual = new int[][][]{
            {{0, 60, 137, 202}, {60, 137, 202, 255}, {137, 202, 255, 0}, {202, 255, 0, 60}},
            {{255, 202, 137, 60}, {202, 137, 60, 0}, {137, 60, 0, 255}, {60, 0, 255, 202}},
            {{60, 137, 202, 255}, {137, 202, 255, 0}, {202, 255, 0, 60}, {255, 0, 60, 137}},
    };


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(actual[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }

  }

  @Test
  public void colorCorrect() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }
    Image compressedImage = model.colorCorrect();

    int[][][] expectedChannels = compressedImage.getChannels();


    int[][][] actual = new int[][][]{
            {{0, 0, 42, 106}, {0, 42, 106, 169}, {42, 106, 169, 0}, {106, 169, 0, 0}},
            {{255, 234, 170, 106}, {234, 170, 106, 42}, {170, 106, 42, 255}, {106, 42, 255, 234}},
            {{106, 170, 234, 255}, {170, 234, 255, 42}, {234, 255, 42, 106}, {255, 42, 106, 170}},
    };


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(actual[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }

  }


  @Test
  public void testHistogram() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }
    Image compressedImage = model.histogram();

    int[][][] expectedChannels = compressedImage.getChannels();


    int[][][] actual = new int[][][]{
            {{192, 192, 192, 192}, {192, 255, 255, 255}, {192, 255, 255, 255},
                    {192, 255, 255, 255}},
            {{192, 192, 192, 192}, {192, 255, 255, 255}, {192, 255, 255, 255},
                    {192, 255, 255, 255}},
            {{192, 192, 192, 192}, {192, 255, 255, 255}, {192, 255, 255, 255},
                    {192, 255, 255, 255}},
    };


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(actual[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }

  }

  @Test
  public void testMultiple() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }
    ExtendedRGB adjustedImage = (ExtendedRGB) model.levelsAdjust(10, 20, 120);

    ExtendedRGB adjustedCompressedImage = (ExtendedRGB) adjustedImage.compress(20);

    int[][][] array = adjustedCompressedImage.getChannels();

    int[][][] actual = new int[][][]{
            {{0, 255, 255, 255}, {255, 255, 255, 255}, {255, 255, 255, 0}, {255, 255, 0, 255}},
            {{255, 255, 255, 255}, {255, 255, 255, 0}, {255, 255, 0, 255}, {255, 0, 255, 255}},
            {{255, 255, 255, 255}, {255, 255, 255, 0}, {255, 255, 0, 255}, {255, 0, 255, 255}}
    };


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(actual[k][i][j], array[k][i][j]);
        }
      }
    }

  }

  @Test
  public void testRedComponent() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image redComponent = model.channelComponent(null, 0);


    int[][][] expectedChannels = redComponent.getChannels();

    int[][][] expected = new int[][][]{
            {{0, 64, 128, 192}, {64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}},
            {{0, 64, 128, 192}, {64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}},
            {{0, 64, 128, 192}, {64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}}};


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expected[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }

  }


  @Test
  public void testGreenComponent() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image greenComponent = model.channelComponent(null, 1);


    int[][][] expectedChannels = greenComponent.getChannels();

    int[][][] expected = new int[][][]{
            {{255, 192, 128, 64}, {192, 128, 64, 0}, {128, 64, 0, 255}, {64, 0, 255, 192}},
            {{255, 192, 128, 64}, {192, 128, 64, 0}, {128, 64, 0, 255}, {64, 0, 255, 192}},
            {{255, 192, 128, 64}, {192, 128, 64, 0}, {128, 64, 0, 255}, {64, 0, 255, 192}}
    };


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expected[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }

  }


  @Test
  public void testBlueComponent() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image blueComponent = model.channelComponent(null, 2);

    int[][][] expectedChannels = blueComponent.getChannels();

    int[][][] expected = new int[][][]{
            {{64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}, {255, 0, 64, 128}},
            {{64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}, {255, 0, 64, 128}},
            {{64, 128, 192, 255}, {128, 192, 255, 0}, {192, 255, 0, 64}, {255, 0, 64, 128}}
    };


    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expected[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }
  }

  @Test
  public void downScale() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image downscaled = model.downScaling(2, 2);

    int[][][] expectedChannels = downscaled.getChannels();

    int[][][] expected = new int[][][]{
            {{0, 128}, {128, 255}},
            {{255, 128}, {128, 0}},
            {{64, 192}, {192, 0}}
    };


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expected[k][i][j], expectedChannels[k][i][j]);
        }
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void downScaleInvalidGreater() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image downscaled = model.downScaling(6, 6);

  }


  @Test(expected = IllegalArgumentException.class)
  public void downScaleInvalidZero() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image downscaled = model.downScaling(0, 0);

  }


  @Test(expected = IllegalArgumentException.class)
  public void downScaleInvalidNegative() {

    ExtendedRGB model = new ExtendedRGB(4, 4);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int c = 0; c < 3; c++) {
          model.setPixelValue(c, i, j, image[c][i][j]);
        }
      }
    }

    Image downscaled = model.downScaling(-3, -9);

  }


}



