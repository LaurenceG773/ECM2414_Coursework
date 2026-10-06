import jdk.jshell.spi.ExecutionControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class CardDeck
{
    ArrayList<Card> cards;
    public CardDeck(Card[] startingCards)
    {
        this.cards = new ArrayList<>(Arrays.asList(startingCards));
    }



    //contains a struct of 4 cards to begin with,

    public synchronized void saveState()
    {
        //check that state are all good

        //save to a sensible location
    }

    public Card drawCard() throws ExecutionControl.NotImplementedException
    {
        //draw from front of the queue
        // use reomove function throws no such element exception
        throw new ExecutionControl.NotImplementedException("card drawing from deck not implemented");
    }

    public void discardCard(Card card)
    {
        //place at the back of the queue
        // use add
    }

    @Override
    public String toString() {
        return cards.toString();
    }







}


