import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player implements Runnable
{
    private ArrayList<Card> hand;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private final CardGame game;

    public Player(Card[] dealtCards, CardDeck drawDeck, CardDeck discardDeck, CardGame game)
    {
        this.game = game;
        this.hand = new ArrayList<>(Arrays.asList(dealtCards));

        //references to the decks they are drawing and discarding to
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
    }

    @Override
    public void run()
    {
        while(!game.isGameOver())

        if (hasWon())
        {
            game.gameOver.set(true);
        }


    }

    public  Card discardCard()
    {
        //implement choice behaviour
        return null;
    }

    //synchronized is done inside the function
    public void doTurn()
    {
        //we cannot lock a turn based on pickup and discard decks, it would cause circular deadlock
        // An id based lock where the lowest is locked first would stop a deadlock

        CardDeck A = (drawDeck.getDeckId() < discardDeck.getDeckId()) ? drawDeck : discardDeck;
        CardDeck B = (drawDeck.getDeckId() < discardDeck.getDeckId()) ? discardDeck : drawDeck;

        //last player has to wait for the lower id (put down deck) before it can lock anything
        synchronized (A)
        {
            synchronized (B)
            {
                Card drawnCard = drawDeck.drawCard();
                hand.add(drawnCard);

                hasWon();

                discardDeck.discardCardOnto(discardCard());



            }
        }

    }

    public synchronized boolean hasWon()
    {
        return hand.stream().allMatch(x -> x.getValue() == hand.getFirst().getValue());
    }

    public synchronized void saveState()
    {
        //write state to text file
    }

    @Override
    public String toString() {
        return hand.toString();
    }

    public int getDrawDeckId(){
        return drawDeck.getDeckId();
    }

    public int getDiscardDeckId(){
        return discardDeck.getDeckId();
    }


}
