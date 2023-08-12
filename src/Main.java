public class Main
{
    public static void main(String[] args)
    {
        boolean toContinue = true;
        ConsoleIO.SetBalance(1337);


        do
        {
            ConsoleIO.Clear();
            ConsoleIO.ShowMenu("Возможные опции: создать счёт (1), войти в счёт (2), выйти (3).");


            switch(ConsoleIO.GetOption())
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
    }
}