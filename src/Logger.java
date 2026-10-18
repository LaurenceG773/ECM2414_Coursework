import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Logger
{
    private static boolean isEnabled = true;

    public static void enabled(boolean in)
    {
        isEnabled = in;
    }

    public static void FileCreator(Loggable item)
    {
        if (!isEnabled){return;}
        try {
            //creates a directory for the output files to be put in
            Files.createDirectories(Path.of("Output_Files"));

            //Creates a txt file with the name in this.fileName
            FileWriter txtWriter = new FileWriter("Output_Files/" + item.getFilename());
            //On creation, writes the starting hand to the file
            txtWriter.close();
        } catch (IOException e) {
            System.out.println("An error with creating the file " + item.getFilename());
            e.printStackTrace();
        }
    }

    public static void FileAppender(String text, Loggable item)
    {
        if(!isEnabled){return;}
        try {
            //Opens the txt file in append mode, hence the true
            FileWriter txtAppender = new FileWriter("Output_Files/" + item.getFilename(), true);


            txtAppender.write(text + "\n");
            txtAppender.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

