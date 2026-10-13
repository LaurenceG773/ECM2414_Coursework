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
    private final int Id;

    public Player(Card[] dealtCards, CardDeck drawDeck, CardDeck discardDeck, CardGame game, int Id)
    {
        this.game = game;
        this.hand = new ArrayList<>(Arrays.asList(dealtCards));
        //references to the decks they are drawing and discarding to
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
        this.Id = Id;
    }

    @Override
    public void run()
    {
        System.out.println("Player " + Id + " has Started");
        while(!game.isGameOver())
        {

            if (hasWon()) {
                game.gameOver.set(true);
            }

            doTurn();
        }

    }

    public  Card discardCard()
    {
        //TODO implement choice behaviour
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

    private synchronized boolean hasWon()
    {
        return hand.stream().allMatch(x -> x.getValue() == hand.getFirst().getValue());
    }

    public synchronized void saveState()
    {
        //TODO  write state to text file
    }

    @Override
    public String toString() {
        return ("Player " + Id + ": " + hand.toString());
    }

    public int getDrawDeckId(){
        return drawDeck.getDeckId();
    }

    public int getDiscardDeckId(){
        return discardDeck.getDeckId();
    }


}
