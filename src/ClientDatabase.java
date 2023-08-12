import java.util.ArrayList;


public class ClientDatabase
{
    private static ArrayList<Client> clients = new ArrayList<Client>();
    private static ArrayList<Long> cardNumbers = new ArrayList<Long>();


    public static boolean CardNumberExists(long number)
    {
        return cardNumbers.contains(number) ? true : false;
    }
}
