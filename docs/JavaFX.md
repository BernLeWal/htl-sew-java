# Using the JavaFX GUI framework
...is a litle tricky!

## Enable JavaFX support in Maven
To enable JavaFX you first need to add the following Maven dependencies in the pom.xml-file:
```xml
     <dependencies>
         <dependency>
             <groupId>org.openjfx</groupId>
             <artifactId>javafx-graphics</artifactId>
             <version>13</version>
         </dependency>
         <dependency>
             <groupId>org.openjfx</groupId>
             <artifactId>javafx-controls</artifactId>
             <version>13</version>
         </dependency>
     </dependencies>
```
and also add the javafx-plugin there
```xml
             <plugin>
                 <groupId>org.openjfx</groupId>
                 <artifactId>javafx-maven-plugin</artifactId>
                 <version>0.0.4</version>
                 <configuration>
                     <mainClass>ui.fx.HelloWorldFXDemo</mainClass>
                     <options>
                         <option>--enable-preview</option>
                     </options>
                 </configuration>
             </plugin>
```
now you can clean, compile the complete Maven build system.

## Run the application with Maven
Run the application with Maven by executing the "javafx:run" plugin,  
f.e. __"mvn clean javafx:run"__  

REMARK: It just runs the main-class defined in the pom.xml file. You have to change the POM if you want to run another class.

## Run the application directly in IntelliJ
To enable JavaFX in the IntelliJ-IDE directly, you also must add the runtime-components to the Run configuration:
 * Run -> Edit Configurations:
 * Modify Options -> add VM options:
```
     --module-path "C:\Program Files\Java\javafx-sdk-13.0.2\lib" --add-modules javafx.controls,javafx.fxml
```
REMARK: replace "C:\Program Files\Java\javafx-sdk-13.0.2\lib" to the correct path on your system.
