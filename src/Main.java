public class Main
{
    public static void main(String[] args)
    {
        boolean toContinue = true;

        ConsoleIO.SetBalance(1337);


        do
        {
            ConsoleIO.ShowMenu("Возможные опции: создать счёт (1), войти в счёт (2), выйти (3).");

            switch(ConsoleIO.GetOption())
            {
                case(1):
                {
                    ConsoleIO.CreateClient();
                    break;
                }

                case(2):
                {
                    break;
                }

                case(3):
                {
                    toContinue = false;
                    break;
                }
            }

        } while(toContinue)
    }
}