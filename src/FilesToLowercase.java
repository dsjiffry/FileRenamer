import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;

public class FilesToLowercase {
    public static void main(String[] args)
    {
        FilesToLowercase filesToLowercase = new FilesToLowercase();
        filesToLowercase.startConsole();

        System.out.print("This program will recursively rename all files in the location: " +System.getProperty("user.dir")+
                            "\nTHIS PROCESS CANNOT BE UNDONE. Type 'yes' to continue: ");
        Scanner sc = new Scanner(System.in);
        if(sc.next().equalsIgnoreCase("yes"))
        {
            try {
                Files.walk(Paths.get(System.getProperty("user.dir")))
                        .forEach(filesToLowercase::forEveryFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }

    private void forEveryFile(Path path)
    {
        if (Files.isDirectory(path)) //Skipping folders
        {
            return;
        }

        String fileName = path.getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf('.'));

        if (!fileName.equals(fileName.toLowerCase()) || fileName.contains(" ") || fileName.contains("-"))    //Check if name has Uppercase Letters, spaces or dashes
        {
            fileName = fileName.substring(0, fileName.lastIndexOf('.')); //Removing extension from filename
            String newname = fileName.toLowerCase().trim();    //Renaming file to have lowercase letters only
            newname = newname.replaceAll(" ","_");  //Replacing spaces with underscore
            newname = newname.replaceAll("-","_");  //Replacing dashes with underscore
            newname = newname.concat(extension);    //adding the extension back
            try {
                Files.move(path, path.resolveSibling(newname), ATOMIC_MOVE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startConsole()
    {
        Console console = System.console();
        if(console == null)
        {
            String filename = FilesToLowercase.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
            try {
                filename = filename.replaceAll("%20"," ");
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar \"" + filename + "\""});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
