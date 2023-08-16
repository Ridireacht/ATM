import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class FileIO {

  private static long balance = 100000;
  private static ArrayList<Client> clientList = new ArrayList<>();


  public static void writeToFile(long balance, ArrayList<Client> clientList) throws IOException {
    FileWriter fw = new FileWriter("database.txt");

    fw.write(Long.toString(balance));

    for (var client : clientList) {
      fw.write("\n\n" + client.accountBalance + "\n");
      fw.write(client.FIO + "\n");
      fw.write(client.PIN + "\n");
      fw.write(client.cardNumber + "\n");
      fw.write(Long.toString(client.availableSince.getTime()));
    }

    fw.close();
  }


  public static void readFromFile() throws IOException {
    Path path = Paths.get("database.txt");
    if (Files.notExists(path)) {
      return;
    }

    FileReader fr = new FileReader("database.txt");
    Scanner sc = new Scanner(fr);

    if (sc.hasNextLine()) {
      balance = Integer.parseInt(sc.nextLine());
    } else {
      return;
    }

    while (sc.hasNextLine()) {
      sc.nextLine();
      clientList.add(new Client());

      clientList.get(clientList.size() - 1).accountBalance = Long.parseLong(sc.nextLine());
      clientList.get(clientList.size() - 1).FIO = sc.nextLine();
      clientList.get(clientList.size() - 1).PIN = Integer.parseInt(sc.nextLine());
      clientList.get(clientList.size() - 1).cardNumber = sc.nextLine();
      clientList.get(clientList.size() - 1).availableSince = new Date(
          Long.parseLong(sc.nextLine()));
    }

    fr.close();
  }


  public static long getBalance() {
    return balance;
  }


  public static ArrayList<Client> getClientList() {
    return clientList;
  }
}
