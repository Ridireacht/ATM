import java.util.ArrayList;


public class ClientDatabase
{
    public static ArrayList<Client> clients = new ArrayList<Client>();

    
    public static Client GetClientByCard(long number)
    {
        return clients.stream().filter(o -> o.cardNumber == number).findFirst().orElse(null);
    }


    public static void UpdateClient(Client client)
    {
        clients.removeIf(o -> o.cardNumber == client.cardNumber);
        clients.add(client);
    }
}
