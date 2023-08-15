import java.io.IOException;


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

        catch(IOException e1)
        {
            System.out.println("При чтении файла возникла непредвиденная ошибка! Нажмите Enter, чтобы выйти...");

            try { System.in.read(); }
            catch (IOException e2) { System.out.println("\nПри попытке считать нажатие Enter возникла непредвиденная ошибка!"); }

            System.exit(0);
        }



        boolean toContinue = true;

        do
        {
            ConsoleIO.ShowMainMenu();


            switch (ConsoleIO.GetOption(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова."))
            {
                case (1) -> ConsoleIO.CreateClientAccount();
                case (2) -> ConsoleIO.EnterClientAccount();
                case (3) -> toContinue = false;
            }


            try { FileIO.WriteToFile(ConsoleIO.balance, ClientDatabase.clients); }

            catch(IOException e1)
            {
                System.out.println("При записи файла возникла непредвиденная ошибка! Нажмите Enter, чтобы выйти...");

                try { System.in.read(); }
                catch (IOException e2) { System.out.println("\nПри попытке считать нажатие Enter возникла непредвиденная ошибка!"); }

                System.exit(0);
            }

        } while(toContinue);


        ConsoleIO.CloseScanner();
    }
}