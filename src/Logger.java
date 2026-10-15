import java.io.FileWriter;
import java.io.IOException;

public class Logger
{
    public static void FileCreator(Player player) {
        try {
            //Creates a txt file with the name in this.fileName
            FileWriter txtWriter = new FileWriter(filenameGenerator(player));

            //On creation, writes the starting hand to the file
            txtWriter.write("Player " + player.getId() + " starting deck is: " + player.getHand() +"\n");
            txtWriter.close();
        } catch (IOException e) {
            System.out.println("An error with creating the file for player " + player.getId() + " occurred.");
            e.printStackTrace();
        }
    }

    public static void FileAppender(String text,Player player)
    {
        try {
            //Opens the txt file in append mode, hence the true
            FileWriter txtAppender = new FileWriter(filenameGenerator(player), true);


            txtAppender.write(text + "\n");
            txtAppender.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String filenameGenerator(Player player)
    {
        return "player" + player.getId() + "_output.txt";
    }

}
