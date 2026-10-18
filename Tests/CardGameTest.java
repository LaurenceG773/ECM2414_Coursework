import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CardGameTest
{
    @TempDir
    Path dir;

    @BeforeAll
    public static void start()
    {
        Logger.enabled(false);
    }
    private Path writeFile(String content) throws IOException
    {
        Path p = dir.resolve("temp.txt");
        Files.writeString(p, content);
        return p;
    }

    @Test
    public void cardsSizeTest() throws IOException, IllegalArgumentException
    {
        assertThrows(IllegalArgumentException.class, () -> {
            new CardGame(3, Path.of("Test_Input_Files/TwoPlayer.txt"));
        });

    }

    @Test
    public void fileDoesNotExistThrows() throws IOException
    {
        assertThrows(IOException.class, () -> {
            new CardGame(3, Path.of("ThisFileDoesNotExist.txt"));
        });
    }

    @Test
    void emptyFileThrows() throws IOException
    {
        Path p = writeFile("");
        assertThrows(IllegalArgumentException.class, () -> new CardGame(4, p));
    }

    @Test
    void declareWinIsUnique() throws IOException
    {
        CardGame game = new CardGame(2, Path.of("Test_Input_Files/TwoPlayer.txt"));
        Player p1 = new Player(new Card[] {},null,null,game,1);
        Player p2 = new Player(new Card[] {},null,null,game,2);

        game.declareWin(p1);
        game.declareWin(p2);

        assertSame(game.winner,p1);

    }
}