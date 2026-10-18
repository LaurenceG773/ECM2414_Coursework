import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckTest
{
    @Test
    void drawRemovesTopCard(){
        Card a = new Card(1);
        Card b = new Card(2);

        CardDeck deck = new CardDeck(new Card[]{a,b},0);
        assertSame(a,deck.drawCard());
        assertSame(b,deck.drawCard());
        assertTrue(deck.isEmpty());
    }

    @Test
    void discardCardToDeck(){
        Card a = new Card(1);
        Card b = new Card(2);
        CardDeck deck = new CardDeck(new Card[]{a},0);

        deck.discardCardOnto(b);
        assertFalse(deck.isEmpty());
        assertSame(a,deck.drawCard());
        assertSame(b,deck.drawCard());
        assertTrue(deck.isEmpty());
    }

    @Test
    void drawFromEmptyDeck()
    {
        CardDeck deck = new CardDeck(new Card[]{},0);
        assertThrows(RuntimeException.class, ()->deck.drawCard());
    }

    @Test
    void deckIdConsistent(){
        CardDeck deck = new CardDeck(new Card[]{},1);
        assertSame(1,deck.getDeckId());
    }
}
