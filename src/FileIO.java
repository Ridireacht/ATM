import java.nio.file.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class FileIO
{
    private static int balance;
    private static ArrayList<Client> clientList = new ArrayList<Client>();


    public static void WriteToFile(int balance, ArrayList<Client> clientList) throws IOException
    {
        FileWriter fw = new FileWriter("database.txt");


        fw.write(balance + "\n\n");

        for(var client : clientList)
        {
            fw.write(client.accountBalance + "\n");
            fw.write(client.FIO + "\n");
            fw.write(client.PIN + "\n");
            fw.write(client.cardNumber + "\n");
            fw.write(client.availableSince + "\n");

            fw.write("\n");
        }


        fw.close();
    }


    public static void ReadFromFile() throws IOException
    {
        Path path = Paths.get("database.txt");
        if (Files.notExists(path))
            return;


        FileReader fr = new FileReader("database.txt");
        Scanner sc = new Scanner(fr);


        if (sc.hasNextLine())
        {
            balance = Integer.parseInt(sc.nextLine());
            sc.next();
        }

        else
            return;


        while(sc.hasNextLine())
        {
            clientList.add(new Client());

            clientList.get(clientList.size() - 1).accountBalance = Integer.parseInt(sc.nextLine());
            clientList.get(clientList.size() - 1).FIO = sc.nextLine();
            clientList.get(clientList.size() - 1).PIN = Integer.parseInt(sc.nextLine());
            clientList.get(clientList.size() - 1).cardNumber = Long.parseLong(sc.nextLine());
            clientList.get(clientList.size() - 1).availableSince = new Date(Long.parseLong(sc.nextLine()));

            sc.next();
        }


        fr.close();
    }


    public static int GetBalance()
    {
        return balance;
    }


    public static ArrayList<Client> GetClientList()
    {
        return clientList;
    }
}
