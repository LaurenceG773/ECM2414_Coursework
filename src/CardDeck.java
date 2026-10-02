import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Array;

public class CardDeck
{

    public CardDeck()
    {
        //import the cards, use a importCards()
    }

    private Card[] importCards(String path) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("input reading not done yet");
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






}


