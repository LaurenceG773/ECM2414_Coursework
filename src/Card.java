public class Card
{

    private int number;
    public Card(int cardNum)
    {
        number = cardNum;
    }

    //synchronized makes it thread safe use librallry
    public synchronized void doSomething(){}

    public int getValue(){
        return number;
    }

    @Override
    public String toString(){
        return String.valueOf(number);
    }
}
