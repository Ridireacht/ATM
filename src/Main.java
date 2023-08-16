import java.io.IOException;


public class Main
{
    public static void main(String[] args)
    {
        FileIO.readFromFile();
        ClientDatabase.clients = FileIO.getClientList();
        Account.balance = FileIO.getBalance();


        boolean toContinue = true;

        do
        {
            ConsoleIO.showMainMenu(Account.balance);


            switch (ConsoleIO.inputNumber(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова."))
            {
                case (1) -> Account.createClientAccount();
                case (2) -> Account.enterClientAccount();
                case (3) -> toContinue = false;
            }


            FileIO.writeToFile(Account.balance, ClientDatabase.clients);

        } while(toContinue);


        ConsoleIO.closeScanner();
    }
}