import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of players: ");
        var numPlayers = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter file Path: ");
        var filename = scanner.nextLine();

        Path filePath = Path.of(filename);

        var game = new CardGame(numPlayers, filePath);
        game.start();
    }
}