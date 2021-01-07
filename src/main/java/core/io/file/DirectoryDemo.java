package core.io.file;

import java.io.File;

/**
 * DirectoryDemo shows various methods to access directories and directory-contents of the file system.
 */
public class DirectoryDemo {

    public static void main(String[] args) {
        // List the content of the current directory
        File workingDir = new File(".");    // uses the current working directory
        File userHomeDir = new File(System.getProperty("user.dir")); // gets the user-directory from the OS

        String[] content = userHomeDir.list();
        if (content != null)
            for (String entry : content) {
                System.out.println(entry);
            }
    }
}
