import java.io.IOException;


public class Main
{
    public static void main(String[] args)
    {
        try
        {
            FileIO.readFromFile();
            ClientDatabase.clients = FileIO.getClientList();
            Account.balance = FileIO.getBalance();
        }

        catch(IOException e)
        {
            System.out.println("При чтении файла возникла непредвиденная ошибка! Нажмите Enter, чтобы выйти...");

            ConsoleIO.pressEnterToExit();

            System.exit(0);
        }



        boolean toContinue = true;

        do
        {
            ConsoleIO.showMainMenu(Account.balance);


            switch (ConsoleIO.getNumber(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова."))
            {
                case (1) -> Account.createClientAccount();
                case (2) -> Account.enterClientAccount();
                case (3) -> toContinue = false;
            }


            try { FileIO.writeToFile(Account.balance, ClientDatabase.clients); }

            catch(IOException e)
            {
                System.out.println("При записи файла возникла непредвиденная ошибка! Нажмите Enter, чтобы выйти...");

                ConsoleIO.pressEnterToExit();

                System.exit(0);
            }

        } while(toContinue);


        ConsoleIO.closeScanner();
    }
}