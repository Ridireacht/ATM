import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileIO
{
    private static int balance;
    private static ArrayList<Client> clientList;


    public static void WriteToFile(int balance, ArrayList<Client> clientList) throws IOException
    {
        FileWriter fw = new FileWriter("database.txt");

        fw.close();
    }


    public static void ReadFromFile() throws IOException
    {
        FileReader fr = new FileReader("database.txt");

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
