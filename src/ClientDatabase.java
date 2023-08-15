import java.util.ArrayList;


public class ClientDatabase {

  public static ArrayList<Client> clients = new ArrayList<>();


  public static Client getClientByCard(String cardNumber) {
    return clients.stream().filter(o -> o.cardNumber == cardNumber).findFirst().orElse(null);
  }


  public static void updateClient(Client client) {
    clients.removeIf(o -> o.cardNumber == client.cardNumber);
    clients.add(client);
  }
}
