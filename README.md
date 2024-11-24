# Image Processing Application

This assignment involves the development of an image processing system that supports various image
manipulations, filtering, and pixel-level operations. The system includes functions like flipping,
brightening, and applying filters such as blur and sharpen to images represented as grids of pixels.
The design is modular and open for extensions.

## MVC Design Pattern

This application follows the MVC design pattern.

Model: Represents data and its operations, handling image manipulation and enhancement.
Controller: Interacts with users, coordinating between the model and the view.
View: The output by which the user interacts with.


### Design techniques used in this application


1. Factory method for loading and saving images

To support various image formats we have created an ImageUtil class which creates ImagIO or PPM 
images based on the file extension. This can be extended to support new image formats. 

2. Adapter design adapter for MVVM design 
The adapter design is used to convert the images stored in the model into buffered images using the
view model class. Any futures transformations between the model and view can be handled by this 
class.

## How to run the application

- This application can be run using GUI (OR) inline commands (OR) a text file containing commands in .txt format
- By running the jar file (or) ApplicationRunner main function you can access the GUI.
- For running the application in inline
```
cd src
java application.ApplicationRunner -text
```
- For running the application in text file
```
cd src
java application.ApplicationRunner -file <path of script.txt>
```

- See USEME.md for how to run specific commands in the application. 

### Folder Structure

- src: the folder which contains all the code
    - application (package)
        - model (package)
        - controller (package)
            - commands(package)
        - view (package)
- test : the folder which contains all the test
- res: the folder which contains the result of IME operations
- UML.png: The class diagram of the application
- REDAME.md: Contains details about the application
- USEME.md: Contains how to use this application

## Changes 
The following have been changed from the previous version
1. View package was created which has graphical view class for Java swing layout.
3. To follow the MVVM design pattern we designed a view model as an adapter.
3. New command were added in commands package to support downscaling.
4. Methods were changed to support partial image manipulation operations.



## Image Citation

- We own the images used in this assignment