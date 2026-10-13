import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class CardGame
{

    public final int PLAYER_COUNT;
    private Player[] players;
    private CardDeck[] decks;
    public volatile AtomicBoolean gameOver = new AtomicBoolean(false);
    public record Result (CardDeck[] decks,Player[] players){}

    public  CardGame(int num_players,String path)
    {
        PLAYER_COUNT = num_players;

        Result r = deal(importCards(path));
        players = r.players;
        decks = r.decks;

        for (int i = 0; i < PLAYER_COUNT; i++)
        {
            System.out.println(players[i].toString());
            System.out.println(decks[i].toString());

        }
        // get our decks in a row
        // pass decks in correct order

    }

    public synchronized void declareWin(int playerId){
        if (!gameOver.get()){
            gameOver.set(true);
            System.out.println("Player " + playerId + " has won.");
        }
    }

    public boolean isGameOver()
    {
        return gameOver.get();
    }


    private Card[] importCards(String path)
    {

        List<Card> cards = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(Path.of(path)))
        {

            String line;
            while ((line = r.readLine()) != null)
            {
                cards.add(new Card(Integer.parseInt(line)));
            }
        }
        catch (IOException e) {throw new RuntimeException(e);}

        return cards.toArray(new Card[cards.size()]);

    }



    public void start()
    {
        List<Thread> playerThreads = new ArrayList<>();
        for (Player p : players)
        {
            Thread t = new Thread(p);
            playerThreads.add(t);
            t.start();
        }

        //TODO save to file at end

    }





    //is called from main, the only class called from main

    // needs to evaluate winner by asking player.haswon()

    public Result deal(Card[] cards)
    {
        Player[] outPlayers = new Player[PLAYER_COUNT];
        CardDeck[] outCardDecks = new CardDeck[PLAYER_COUNT];

        //allways works out to 4 cards in the deck no matter the player count
        for (int i =0; i < PLAYER_COUNT; i++)
        {
            Card[] startingCards = Arrays.copyOfRange(cards, (i * 8) + 4, (i * 8) + 8);
            outCardDecks[i] = new CardDeck(startingCards,i);
        }


        for (int i =0; i < PLAYER_COUNT; i++)
        {
            Card[] dealtCards = Arrays.copyOfRange(cards, i * 8, (i * 8) + 4);
            CardDeck drawDeckPointer = outCardDecks[(i - 1 + PLAYER_COUNT) % PLAYER_COUNT];
            CardDeck dumpDeckPointer = outCardDecks[i];
            outPlayers[i] = new Player(dealtCards, drawDeckPointer, dumpDeckPointer,this,i);
        }


        return new Result(outCardDecks,outPlayers);


    }
}
