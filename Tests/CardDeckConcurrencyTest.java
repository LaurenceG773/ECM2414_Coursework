import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckConcurrencyTest
{



    @Test
    public void testCardDeckConcurrency() throws InterruptedException
    {
        Card[] cards = new Card[100];
        for (int i = 0; i < cards.length; i++)
        {
            cards[i] = new Card(i);
        }
        CardDeck deck = new CardDeck(cards, 0);
        AtomicInteger draws = new AtomicInteger();
        HashSet<Card> drawnCards = new HashSet<Card>();

        Runnable drawTask = () ->
        {
            while (true)
            {
                try {assertTrue(drawnCards.add(deck.drawCard()));draws.incrementAndGet();}catch (Exception e){break;}
            }
        };

        Thread t1 = new Thread(drawTask);
        Thread t2 = new Thread(drawTask);
        t1.start();
        t2.start();


        t1.join(2000);
        t2.join(2000);

        assertFalse(t1.isAlive());
        assertFalse(t2.isAlive());
        assertEquals(100, draws.get());
        assertTrue(deck.isEmpty());
    }
}
