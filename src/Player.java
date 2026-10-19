import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.io.FileWriter;

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
        // A weight tracking list to hold the weight of each card in the hand
        ArrayList<Integer> handWeightings = new ArrayList<Integer>();
        int matches;
        int weight;
        // A for loop to compare ervery card in the hand to eachother
        for (int n = 0; n < hand.size(); n++) {
            matches = 0;
            for (int x = 0; x < hand.size(); x ++) {
                if (n != x) {
                    // Increments matches by 1 when heres  a match
                    if (hand.get(n) == hand.get(x)) {
                        ++matches;

                    }
                }
            }
            // The way weighting will work is copies will have a base value (multiples of 2)
            // then if the number is the desired number (matches player number), the value will be + 1
            weight = 0;

            if (matches == 1) {
                // 2 of the same number
                weight = 2;
            } else if (matches == 2) {
                // 3 of the same number
                weight = 4;
            } else if (matches == 3) {
                // 4 of the same number
                weight = 6;
            }

            if (n == Id) {
                ++weight;
            }
            // This adds the weight to the weight tracking list
            handWeightings.add(weight);
        }

        // now all the weightings are in an arraylist, we need to get the index of the lowest weight, and then use
        // that index to return the card to discard

        // starts by declaring the lowest weight to be the first
        int lowest = 0;
        // skips index 0 as thats already assumed the lowest
        for (int y = 1; y < handWeightings.size(); y++) {
            // goes through the handWeightings and if the current one is lower, then lowest gets updated to that index
            if (handWeightings.get(lowest) > handWeightings.get(y)) {
                lowest = y;
            }
        }
        Card discardedCard = hand.get(lowest);

        //updates the txt file with the action
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

                hasWon();

                discardDeck.discardCardOnto(discardCard());

                //TODO saveState();


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



    public void FileCreator() {
        try {
            //Creates a txt file with the name in this.fileName
            FileWriter txtWriter = new FileWriter(this.fileName);

            //On creation, writes the starting hand to the file
            txtWriter.write("Player " + this.Id + " starting deck is: " + this.hand);
            txtWriter.close();
        } catch (IOException e) {
            System.out.println("An error with creating the file for player " + this.Id + " occurred.");
            e.printStackTrace();
        }
    }

    public void FileAppender(String action, int card, String toOrFrom, int deckNum) {
        try {
            //Opens the txt file in append mode, hence the true
            FileWriter txtAppender = new FileWriter(this.fileName, true);

            //Could possibly format in anoyher way, but this follows how the specifications show their example
            txtAppender.write("\nPlayer " + this.Id + " " + action +"s a " + card + toOrFrom + " deck " + deckNum);
            txtAppender.close();
        } catch (IOException e) {
            System.out.println("An error with appending a " + action + " to the file for player " + this.Id + " occurred.");
            e.printStackTrace();
        }
    }



}
