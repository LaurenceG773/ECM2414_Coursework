import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public class Player implements Runnable
{
    private final ArrayList<Card> hand;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private final CardGame game;
    private final int Id;



    enum Action {DRAW,DISCARD}

    public Player(Card[] dealtCards, CardDeck drawDeck, CardDeck discardDeck, CardGame game, int Id)
    {
        this.game = game;
        this.hand = new ArrayList<>(Arrays.asList(dealtCards));

        //references to the decks they are drawing and discarding to
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
        this.Id = Id;
        //creates the txt file path for creating the file and later access to update it

        Logger.FileCreator(this);
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

        //TODO exit save

    }

    public  Card discardCard()
    {
        List<Card> canDiscard = new ArrayList<>();
        for (Card card:hand)
        {
            if(card.getValue() != Id){canDiscard.add(card);}
        }

        var r = new Random().nextInt(canDiscard.size());
        //set discard card to var here did it twice by accident when printing earlier
        Card discardedCard = canDiscard.get(r);
        hand.remove(discardedCard);


        //When discard card is finished, un-comment the next line
        Logger.FileAppender(saveAction(Action.DISCARD, discardedCard,discardDeck),this);
        return discardedCard;
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
                if (drawDeck.isEmpty()){return;}//stops if it cannot pick a card up
                if (game.isGameOver()){return;} //stops if the condition has changed while waiting for the lock
                Card drawnCard = drawDeck.drawCard();
                hand.add(drawnCard);

                Logger.FileAppender(saveAction(Action.DRAW, drawnCard,drawDeck),this);

                Card discardedCard = discardCard();
                discardDeck.discardCardOnto(discardedCard);

                if (hasWon()) {
                    game.gameOver.set(true);
                }

                Logger.FileAppender(saveHand(),this);


            }
        }

    }

    public synchronized boolean hasWon()
    {
        return  hand.stream().allMatch(x -> x.getValue() == hand.getFirst().getValue());
    }

    public synchronized String saveHand()
    {
        return ("Player " + Id +"'s current hand is " + hand.toString());
    }

    public synchronized String saveAction(Action action, Card card, CardDeck deck)
    {
        //Could possibly format in another way, but this follows how the specifications show their example
        int deckNum = deck.getDeckId();
        switch (action) {
            case DISCARD -> {
                return ("Player " + Id + " " + "discard's a " + card + " to deck " + deckNum);
            }
            case DRAW -> {
                return ("Player " + Id + " " + "draw's a " + card + " from deck " + deckNum);
            }
        }

        throw new RuntimeException("An error with appending a " + action + " to the file for player " + Id + " occurred.");

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

    public String getHand()
    {
        return  hand.toString();
    }

    public String getId()
    {
        return String.valueOf(Id);
    }



}
