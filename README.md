# Object Edge Detector In Pixel Graphics

This application was created as a semestreal project on Charles University, Faculty of Mathematics and Physics in winter semester 2024/25 for Multimedia Retrieval lecture (NDBI034).

Main goal of the project is to create a picture where edges of objects are detected from given picture using algorithmical approach.

## Project structure

The main directory contains apart of this README these items:

- ```ObjectEdgeDetectorInPixelGraphics``` folder which contains the Java with Maven project itself and is its root directory.
- ```ImagesCreatedInTesting``` folder which contains interesting images (in my opinion) that were created when working on this project (some are not replicable on final version).
- ```DocImages``` folder which holds images used in this documentation.
- ```specifikace_projektu.pdf``` which is article-like pdf file with initial specification of the project (before it was started). This file is only in Czech.
- ```vyhodnoceni_projektu.pdf``` which contains my sumarisation of the final project and evaluates the initial specification. This file is also only in Czech.
- ```.gitignore``` internal git repository file.

## Using the program

### Prerequisities

The program was created using **Java** version 17.0.7. It should run on this version without problems.
Other versions weren't tested but the version certainly have to be changed at least in ```pom.xml```  in ```properties``` section.

The program is intended to be used with **Apache Maven**. 
Version 3.9.9 was used to test compilation process.

To use this program users need to have a distribution of **OpenCV** on their computer.
OpenCV can be downloaded from [official website](https://opencv.org/releases/).
Version 4.9.0 was used during developement and it is specified in ```pom.xml```.
Downloaded files need to be extracted and their location needs to be specified as a parameter in commadn which starts the program itself (see [Running the program](###running-the-program
)).

### Building the program

To run the program first compile the JAR file using command: ```mvn clean package``` in the Java project root directory (/ObjectEdgeDetectorInPixelGraphics).
This command creates ```ObjectEdgeDetectorInPixelGraphics-1.0-SNAPSHOT.jar``` file which will be later used to run the program.

### Running the program

After the JAR file is created the program proper is started in the Java project root directory (/ObjectEdgeDetectorInPixelGraphics) by using command: ```java -Djava.library.path="[1]" -cp "[2];[3]" [4]```.

Command parts description:
1. Instead of the first mark add: ```-Djava.library.path="/path/to/opencv/build/java/x64"```. This adds the library file to the project. ```/path/to/opencv``` should be replaced with the actual path on your computer to folder where OpenCV is. ```/build/java/x64``` part should be path to java_opencv490.dll file inside OpenCV folder. This should **not** be replaced.
2. Similarly instead of the second mark add: ```"/path/to/opencv/build/java/opencv-490.jar;```. Replace ```/path/to/opencv``` with the same path as above. This leads to OpenCV JAR file needed to run the program.
3. The third mark replace with: ```target/ObjectEdgeDetectorInPixelGraphics-1.0-SNAPSHOT.jar"```. This should **not** be changed. If command is used from Java project root directory (/ObjectEdgeDetectorInPixelGraphics) than the path to the JAR file is correct.
4. The fourth mark replace with: ```cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.ObjectEdgeDetectorInPixelGraphics``` which is also correct without a change. It specifies path to main class to the program.

On my computer (Windows) the whole command looks like this: ```java -Djava.library.path="C:/opencv/build/java/x64" -cp "C:/opencv/build/java/opencv-490.jar;target/ObjectEdgeDetectorInPixelGraphics-1.0-SNAPSHOT.jar" cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.ObjectEdgeDetectorInPixelGraphics```.

### Cleaning the project

Generated files can be deled using ```mvn clean``` command in the Java project root directory (/ObjectEdgeDetectorInPixelGraphics).

## Generated documentation

This project contains generated documentation of Java Maven project. 
It isn't included in project itself but can be generated by using command: ```mvn javadoc:javadoc``` in the Java project root directory (/ObjectEdgeDetectorInPixelGraphics).

After the generating is finished the documentation is located in ```/target/site/apidocs``` and is accessed via ```index.html``` for example in any web browser.

The documentation contains brief descriptions of classes, methods and other components.

## User documentation

(If the images aren't visible you can find them in DocImages folder in root directory. They should be visible on GitHub.)

After opening the **application**, the screen like this is presented to the user:

![app overview](/DocImages/basicScreen.png "Whole app window")

First let's describe the **top part** with places for images:

![top panel](/DocImages/topScreenNumbered.png "Top part of screen")

1. A place where the input image will be displayed when one is provided. Drag-and-drop function is enabled on this frame, it is one method to add image for processing.
2. Button for manual adding of the image. This display a simple smaller window where input image can be selected from system files.
3. A place where the output image will be displayed when algithm is runned and returns result.
4. A button which opens simple smaller window where name and location for saving the displayed output image can be selected. 

The **bottom part** serves mainly for choosing parameters for the algorithms:

![bottom panel](/DocImages/bottomScreenNumbered.png "Bottom part of screen")

1. Tabs with individual algorithm. There you can choose which algorithm to run.
2. A panel where parameters of given algorithm are chosen.
3. Button run which starts chosen algorithm with selected parameters and displays result in top-right box.

If **combine tab** is selected more complex panel is displayed. It serves for combining two algorithms:

![combine tab](/DocImages/combineTab.png "Tab for combining algorithms")

1. This is the part where the parameters for the combination itself are entered. For AND and OR options the right two sliders are not present. They only shows up for the WEIGHED option. The chosen option determins what happens to the results of the two algorithms.  
2. First algorithm panel. On top the algorithm is chosen. Lower the parameters are selected as in the stand-alone version of the algorithm.
3. Second algorithm panel. It is same as the first one.

