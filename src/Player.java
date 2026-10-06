import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player
{
    private ArrayList<Card> hand;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;

    public Player(Card[] dealtCards, CardDeck drawDeck, CardDeck discardDeck)
    {
        this.hand = new ArrayList<>(Arrays.asList(dealtCards));
        //references to the decks they are drawing and discarding to
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
    }

    public synchronized void drawCard(){}

    public synchronized void discardCard(){
        //implement choice behaviour
    }

    public synchronized void doTurn(){
        //atomised draw and discard
    }

    public synchronized boolean hasWon() throws ExecutionControl.NotImplementedException
    {
        //check winning conditions and return true or false
        throw  new ExecutionControl.NotImplementedException("not done");
    }

    public synchronized void saveState()
    {
        //write state to text file
    }

    @Override
    public String toString() {
        return hand.toString();
    }


}
