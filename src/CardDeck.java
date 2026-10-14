import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;

public class CardDeck
{
    ArrayList<Card> cards;
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
        //check that state are all good

        //save to a sensible location
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







}


