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
            ConsoleIO.ShowMainMenu();


            switch(ConsoleIO.GetOption(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова."))
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

        } while(toContinue);


        ConsoleIO.CloseScanner();
    }
}