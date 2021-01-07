package core.io.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * FileStreamDemo shows how to read and write files in text or binary mode.
 */
public class FileStreamDemo {

    public static void main(String[] args) {
        final String binPath = System.getProperty("java.io.tmpdir") + "test.bin";
        System.out.println("Using binary file: " + binPath);
        final String txtPath = System.getProperty("java.io.tmpdir") + "test.txt";
        System.out.println("Using text file: " + txtPath);

        writeBinary(binPath);
        System.out.println();
        readBinary(binPath);
        System.out.println();

        writeTxt(txtPath);
        System.out.println();
        readTxt(txtPath);
        System.out.println();
    }

    private static void readTxt(String pathtxt) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(pathtxt))) {
            String bool = reader.readLine();
            String f = reader.readLine();
            String str = reader.readLine();

            System.out.println(bool);
            System.out.println(f);
            System.out.println(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeTxt(String pathtxt) {
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(pathtxt))) {
            writer.println("true");
            writer.println(2000.3);
            writer.println("Schwyz!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readBinary(String path) {
        try (DataInputStream reader =
                     new DataInputStream(
                             new FileInputStream(path))) {
            boolean bool = reader.readBoolean();
            double f = reader.readDouble();
            String str = reader.readUTF();

            System.out.println(bool);
            System.out.println(f);
            System.out.println(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeBinary(String path) {
        try (DataOutputStream writer =
                     new DataOutputStream(
                             new FileOutputStream(path))) {
            writer.writeBoolean(true);
            writer.writeDouble(2000.3);
            writer.writeUTF("Schwyz!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
