import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.FileWriter;
import java.util.Random;

public class Player implements Runnable
{
    private ArrayList<Card> hand;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private final CardGame game;
    private final String fileName;
    private final int Id;

    public Player(Card[] dealtCards, CardDeck drawDeck, CardDeck discardDeck, CardGame game, int Id)
    {
        this.game = game;
        this.hand = new ArrayList<>(Arrays.asList(dealtCards));

        //references to the decks they are drawing and discarding to
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
        this.Id = Id;
        //creates the txt file path for creating the file and later access to update it
        this.fileName = "player" + this.Id + "_output.txt";
        FileCreator();
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
        List<Card> canDiscard = new ArrayList<>();
        for (Card card:hand)
        {
            if(card.getValue() != Id){canDiscard.add(card);}
        }

        var r = new Random().nextInt(canDiscard.size());
        Card discardedCard = canDiscard.get(r);
        hand.remove(discardedCard);



        //When discard card is finished, un-comment the next line
        FileAppender("discard", discardedCard.getValue(), "to", discardDeck.getDeckId());
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
                if (game.isGameOver()){return;} //stops if the condion has changed while wating for the lock
                Card drawnCard = drawDeck.drawCard();
                hand.add(drawnCard);

                FileAppender("draw", drawnCard.getValue(), "from", drawDeck.getDeckId());


                System.out.println(discardCard());
                discardDeck.discardCardOnto(discardCard());
                hasWon();
                //TODO saveState();


            }
        }

    }

    public synchronized boolean hasWon()
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



    public void FileCreator() {
        try {
            //Creates a txt file with the name in this.fileName
            FileWriter txtWriter = new FileWriter(fileName);

            //On creation, writes the starting hand to the file
            txtWriter.write("Player " + Id + " starting deck is: " + hand);
            txtWriter.close();
        } catch (IOException e) {
            System.out.println("An error with creating the file for player " + Id + " occurred.");
            e.printStackTrace();
        }
    }

    public void FileAppender(String action, int card, String toOrFrom, int deckNum) {
        try {
            //Opens the txt file in append mode, hence the true
            FileWriter txtAppender = new FileWriter(fileName, true);

            //Could possibly format in anoyher way, but this follows how the specifications show their example
            txtAppender.write("\nPlayer " + Id + " " + action +"s a " + card + toOrFrom + " deck " + deckNum);
            txtAppender.close();
        } catch (IOException e) {
            System.out.println("An error with appending a " + action + " to the file for player " + Id + " occurred.");
            e.printStackTrace();
        }
    }



}
