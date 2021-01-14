package lang;                                   // Package-Name, the "subdirectory" where the Java-file is stored

import java.util.Scanner;

public class Demo00FirstJavaFile                // Class-Name, must be equal to the Filename of the .java-file (without extension)
{
    public static void main(String[] args) {    // The main-program
        // the contained code here will be executed when the Program is started

        // prints a text (on the console)
        System.out.println("This is my first java program");

        // reads text (from the console), waits until the user presses the ENTER-key.
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
    }
}
