import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;

public class CardDeck implements Loggable
{
    private ArrayList<Card> cards;
    private final int deckId;

    public CardDeck(Card[] startingCards,int deckId)
    {
        this.cards = new ArrayList<>(Arrays.asList(startingCards));
        this.deckId = deckId;
    }

    public int getDeckId()
    {
        return deckId;
    }



    //contains a struct of 4 cards to begin with,

    public synchronized void saveState()
    {
        if (cards.size() != 4){throw new RuntimeException("CardDeck has wrong size");}
        Logger.FileCreator(this);
        Logger.FileAppender(("deck " + deckId + " contents: " + cards.toString()),this);
    }

    public synchronized Card drawCard()
    {
        if (!cards.isEmpty())
        {
            return cards.remove(0);
        }
        throw new RuntimeException("card Deck is empty");

    }

    public synchronized void discardCardOnto(Card card)
    {
        cards.add(card);
    }

    @Override
    public String toString()
    {
        return ("Deck " + getDeckId() + ":  " + cards.toString());
    }


    public boolean isEmpty()
    {
        return cards.isEmpty();
    }

    @Override
    public String getFilename()
    {
        return "deck" + deckId + "_output.txt";
    }
}


