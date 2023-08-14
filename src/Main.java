public class Main
{
    public static void main(String[] args)
    {
        boolean toContinue = true;


        // Подготовка к работе
        FileIO.ReadFromFile();
        ClientDatabase.SetClients(FileIO.GetClientList());
        ConsoleIO.SetBalance(FileIO.GetBalance());


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


        FileIO.WriteToFile(1337, ClientDatabase.GetClients());
    }
}