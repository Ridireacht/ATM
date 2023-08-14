import java.io.IOException;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        try
        {
            FileIO.ReadFromFile();
            ClientDatabase.clients = FileIO.GetClientList();
            ConsoleIO.balance = FileIO.GetBalance();
        }

        catch(IOException e)
        {
            Scanner sc = new Scanner(System.in);

            System.out.println("При чтении файла возникла непредвиденная ошибка! Нажмите любую кнопку, чтобы выйти...");
            sc.next();
            System.exit(0);
        }



        boolean toContinue = true;

        do
        {
            ConsoleIO.Clear();
            ConsoleIO.ShowMenu("Возможные опции: создать счёт (1), войти в счёт (2), выйти (3).");


            switch(ConsoleIO.GetOption(0, 4, "Выбор должен быть от 1 до 3! Попробуйте снова."))
            {
                case(1):
                {
                    ConsoleIO.CreateClientAccount();
                    break;
                }

                case(2):
                {
                    ConsoleIO.EnterClientAccount();
                    break;
                }

                case(3):
                {
                    toContinue = false;
                    break;
                }
            }

        } while(toContinue);



        try
        {
            FileIO.WriteToFile(ConsoleIO.balance, ClientDatabase.clients);
        }

        catch(IOException e)
        {
            Scanner sc = new Scanner(System.in);

            System.out.println("При записи файла возникла непредвиденная ошибка! Нажмите любую кнопку, чтобы выйти...");
            sc.next();
            System.exit(0);
        }
    }
}