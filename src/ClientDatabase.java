import java.util.ArrayList;


public class ClientDatabase
{
    public static ArrayList<Client> clients = new ArrayList<>();

    
    public static Client getClientByCard(long number)
    {
        return clients.stream().filter(o -> o.cardNumber == number).findFirst().orElse(null);
    }


    public static void updateClient(Client client)
    {
        clients.removeIf(o -> o.cardNumber == client.cardNumber);
        clients.add(client);
    }
}
