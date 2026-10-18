
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class CardGame
{

    public final int PLAYER_COUNT;
    public Player winner;
    private Player[] players;
    private CardDeck[] decks;
    public volatile AtomicBoolean gameOver = new AtomicBoolean(false);
    public record Result (CardDeck[] decks,Player[] players){}

    public  CardGame(int num_players, Path path) throws IOException
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

    public synchronized void declareWin(Player p){
        if (!gameOver.get()){
            gameOver.set(true);
            winner = p;
            System.out.println("Player " + p.getId() + " has won.");
        }
    }

    public boolean isGameOver()
    {
        return gameOver.get();
    }


    private Card[] importCards(Path path) throws IOException
    {

        List<Card> cards = new ArrayList<>();
        //allows for much larger files
        try (BufferedReader r = Files.newBufferedReader(path))
        {

            String line;
            while ((line = r.readLine()) != null)
            {
                cards.add(new Card(Integer.parseInt(line)));
            }
        }
        catch (NoSuchFileException e) {throw  new IOException("File not found " + path,e);}

        catch (IOException e) {throw new  IOException("Could not read from " + path,e);}

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

        for (CardDeck deck : decks){deck.saveState();}

    }





    //is called from main, the only class called from main

    // needs to evaluate winner by asking player.haswon()

    public Result deal(Card[] cards)
    {
        Player[] outPlayers = new Player[PLAYER_COUNT];
        CardDeck[] outCardDecks = new CardDeck[PLAYER_COUNT];

        if (cards.length != PLAYER_COUNT * 8){
            throw new IllegalArgumentException("Expected " + PLAYER_COUNT*8 + " cards but found " +cards.length);
        }

        //allways works out to 4 cards in the deck no matter the player count
        for (int i =0; i < PLAYER_COUNT; i++)
        {
            var startingCards = new Card[]{cards[4*PLAYER_COUNT+i],cards[4*PLAYER_COUNT + 1*PLAYER_COUNT + i],cards[4*PLAYER_COUNT + 2*PLAYER_COUNT + i],cards[4*PLAYER_COUNT + 3*PLAYER_COUNT + i]};
            outCardDecks[i] = new CardDeck(startingCards,i+1);
        }


        for (int i =0; i < PLAYER_COUNT; i++)
        {
            var dealtCards = new Card[]{cards[i],cards[1*PLAYER_COUNT + i],cards[2*PLAYER_COUNT + i],cards[3*PLAYER_COUNT + i]};
            CardDeck drawDeckPointer = outCardDecks[(i - 1 + PLAYER_COUNT) % PLAYER_COUNT];
            CardDeck dumpDeckPointer = outCardDecks[i];
            outPlayers[i] = new Player(dealtCards, drawDeckPointer, dumpDeckPointer,this,i+1);
        }


        return new Result(outCardDecks,outPlayers);


    }
}
