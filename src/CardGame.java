import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class CardGame
{

    public final int PLAYER_COUNT;
    private Player[] players;
    private CardDeck[] decks;
    public volatile boolean gameOver = false;
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
        if (!gameOver){
            gameOver = true;
            System.out.println("Player " + playerId + " has won.");
        }
    }

    public boolean isGameOver()
    {
        return gameOver;
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



    }





    //is called from main, the only class called from main
    // round robin card drawing and dumping

    //instanciates players
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
            outPlayers[i] = new Player(dealtCards, drawDeckPointer, dumpDeckPointer);
        }


        return new Result(outCardDecks,outPlayers);


    }
}
