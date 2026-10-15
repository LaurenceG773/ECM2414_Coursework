import java.io.FileWriter;
import java.io.IOException;

public class Logger
{
    public static void FileCreator(Loggable item) {
        try {
            //Creates a txt file with the name in this.fileName
            FileWriter txtWriter = new FileWriter(item.getFilename());
            //On creation, writes the starting hand to the file
            txtWriter.close();
        } catch (IOException e) {
            System.out.println("An error with creating the file " + item.getFilename());
            e.printStackTrace();
        }
    }

    public static void FileAppender(String text,Loggable item)
    {
        try {
            //Opens the txt file in append mode, hence the true
            FileWriter txtAppender = new FileWriter(item.getFilename(), true);


            txtAppender.write(text + "\n");
            txtAppender.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }



}

