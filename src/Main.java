import java.nio.file.Path;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        Path folderpath = Path.of("Test_Input_Files");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of players: ");
        var numPlayers = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter just filename: ");
        var filename = scanner.nextLine();

        Path filePath = folderpath.resolve(filename);

        var game = new CardGame(numPlayers, filePath);
        game.start();
    }
}