import java.util.ArrayList;


public class ClientDatabase
{
    private static ArrayList<Client> clients = new ArrayList<Client>();


    public static boolean CardNumberExists(long number)
    {
        return clients.stream().anyMatch(o -> o.cardNumber == number);
    }
}
