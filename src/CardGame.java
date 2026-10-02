public class CardGame
{

    public final int PLAYER_COUNT;

    public  CardGame(int players)
    {
        PLAYER_COUNT = players;
        deal();

        // contains a struct of n players (list) as n is unkwown

        // get our decks in a row
        // pass decks in correct order

    }

    public void start()
    {
        //while nobody has won
            //do a round

    }

    private void doRound()
    {
        // do all the moves concurrently
        // players.doTurn()
    }



    //is called from main, the only class called from main
    // round robin card drawing and dumping

    //instanciates players
    // needs to evaluate winner by asking player.haswon()

    public void deal()
    {
        //deals hands and decks
    }
}
