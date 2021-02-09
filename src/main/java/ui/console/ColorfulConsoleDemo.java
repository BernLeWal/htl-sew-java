package ui.console;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.AnsiConsole;

/**
 * ColorfulConsoleDemo demonstrates how to use the ANSI console commands to make the console more colorful.
 * ATTENTION: You need to run this sample directly in the command-line window, in the IDE's output window the colors won't appear!
 *
 * https://github.com/fusesource/jansi
 */
public class ColorfulConsoleDemo {
    public static void main(String[] args) {
        AnsiConsole.systemInstall();

        System.out.println(bg(Color.CYAN,"Colors")+" are pretty. "+bg(Color.CYAN,"Colors")+" are fun.");
        System.out.println("Tell me, which "+bg(Color.CYAN,"color's")+" your favorite one?");
        System.out.println(fg(Color.BLUE, "Blue")+" is the sky and "+fg(Color.BLUE, "blue")+" is the sea.");
        System.out.println(fg(Color.YELLOW, "Yellow's")+" the stripes on a bumblebee.");
        System.out.println(fg(Color.RED, "Red")+ " is an apple; "+fg(Color.WHITE, "white")+" is a blizzard.");
        System.out.println(fg(Color.GREEN, "Green")+" is the back of a baby lizard");
        System.out.println(fg(Color.BLACK, "Black")+" and brown are the garden dirt");
        System.out.println(fg(Color.MAGENTA, "Purple's")+" the throat of a hummingbird.");
        System.out.println(fg(Color.YELLOW, "Orange")+" are the pumpkins out in the sub.");
        System.out.println("Tell me, which "+bg(Color.CYAN,"color's")+" your favorite one?");

        AnsiConsole.systemUninstall();
    }

    public static String fg(Color color, String text) {
        return Ansi.ansi().fg(color).a(text).reset().toString();
    }

    public static String bg(Color color, String text) {
        return Ansi.ansi().bg(color).a(text).reset().toString();
    }
}
