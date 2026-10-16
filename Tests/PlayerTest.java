import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest
{
    private CardDeck drawDeck;
    private CardDeck dumpDeck;

    @BeforeEach
    public void Init()
    {

    }

    @Test
    void hasWonCorrect()
    {
        Card[] cards1 = {new Card(1),new Card(1),new  Card(1),new Card(1)};
        var player = new Player(cards1,null,null,null,1);

        Card[] cards2 = {new Card(2),new Card(1),new  Card(1),new Card(1)};
        var player2 = new Player(cards2,null,null,null,2);

        assertEquals(player.hasWon(),true);
        assertEquals(player2.hasWon(),false);
    }





}
