import java.io.IOException;


public class Main
{
    public static void main(String[] args)
    {
        try
        {
            FileIO.readFromFile();
            ClientDatabase.clients = FileIO.getClientList();
            ConsoleIO.balance = FileIO.getBalance();
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
            ConsoleIO.showMainMenu();


            switch (ConsoleIO.getOption(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова."))
            {
                case (1) -> ConsoleIO.createClientAccount();
                case (2) -> ConsoleIO.enterClientAccount();
                case (3) -> toContinue = false;
            }


            try { FileIO.writeToFile(ConsoleIO.balance, ClientDatabase.clients); }

            catch(IOException e1)
            {
                System.out.println("При записи файла возникла непредвиденная ошибка! Нажмите Enter, чтобы выйти...");

                try { System.in.read(); }
                catch (IOException e2) { System.out.println("\nПри попытке считать нажатие Enter возникла непредвиденная ошибка!"); }

                System.exit(0);
            }

        } while(toContinue);


        ConsoleIO.closeScanner();
    }
}