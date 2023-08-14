import java.util.Scanner;


public class ConsoleIO
{
    private static int balance;
    private static Client currentClient;



    public static void Clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public static void ShowMenu(String contentString)
    {
        System.out.println("////////////////////////");
        System.out.println("/       БАНКОМАТ       /");
        System.out.println("////////////////////////");

        System.out.println("\n Баланс банкомата: " + balance);

        System.out.println("\n" + contentString);
    }


    public static void ShowError(String errorString)
    {
        System.out.println("////////////////////////");
        System.out.println("/       БАНКОМАТ       /");
        System.out.println("////////////////////////");

        System.out.println("\n ОШИБКА!");

        System.out.println("\n" + errorString);
    }


    public static void ShowAccount(String errorString)
    {
        System.out.println("////////////////////////");
        System.out.println("/       БАНКОМАТ       /");
        System.out.println("////////////////////////");

        System.out.println("\n Баланс банкомата: " + balance);

        System.out.println("\nЗдравствуйте, " + currentClient.FIO + "!");

        System.out.println("\nБаланс на вашем счету: " + currentClient.accountBalance);

        System.out.println("\nВыберите дальнейшее действие: внести средства (1), снять средства (2), выйти из аккаунта (3).");
    }


    public static void SetBalance(int sum)
    {
        balance = sum;
    }


    public static int GetOption(long lower_boundary, long upper_boundary, String errorMessage)
    {
        int res;
        Scanner sc = new Scanner(System.in);


        while(true)
        {
            while(!sc.hasNextInt())
            {
                System.out.println("\nНекорректное значение! Попробуйте снова.");
                sc.next();
            }


            res = sc.nextInt();

            if (res > lower_boundary && res < upper_boundary)
                break;

            else
                System.out.println("\n" + errorMessage);
        }


        sc.close();
        return res;
    }


    public static String GetOption(String errorMessage)
    {
        String input;
        Scanner sc = new Scanner(System.in);


        while(true)
        {
            input = sc.next();

            if (input.matches("[а-яА-Я]+"))
                break;

            else
                System.out.println("\n" + errorMessage);;
        }


        sc.close();
        return input;
    }


    public static boolean ValidatePIN(int realPIN)
    {
        int localPIN;

        System.out.print("Введите ПИН-код: ");


        for (int i = 1; i < 4; i++)
        {
            localPIN = ConsoleIO.GetOption(999, 10000, "ПИН-код должен состоять из 4 цифр!");

            if (localPIN == realPIN)
                return true;

            else if (i != 3)
                System.out.println("\nНеправильный ПИН-код! Осталось попыток: " + (3 - i));
        }


        System.out.println("\nПИН-код был неверно введён 3 раза, банкомат заблокирован.");
        return false;
    }


    public static Client CreateClientAccount()
    {
        Client localClient = new Client();

        ConsoleIO.Clear();
        ConsoleIO.ShowMenu("");


        System.out.println("Введите своё ФИО (кириллицей): ");
        localClient.FIO = ConsoleIO.GetOption("Некорректное значение! Попробуйте снова.");

        System.out.println("Введите ПИН-код (должен состоять из 4 цифр): ");
        localClient.PIN = ConsoleIO.GetOption(999, 10000, "ПИН-код должен состоять из 4 цифр!");

        localClient.cardNumber = Client.GenerateCardNumber();
        localClient.accountBalance = 0;


        return localClient;
    }


    public static void EnterClientAccount()
    {
        Client localClient = new Client();

        ConsoleIO.Clear();
        ConsoleIO.ShowMenu("");


        while (true)
        {
            System.out.println("Введите номер своей карты в формате XXXXXXXXXXXXXXXX: ");
            localClient.cardNumber = ConsoleIO.GetOption(1000000000000000L, 9999999999999999L, "Номер карты должен состоять из 16 цифр! Попробуйте снова.");

            if (ClientDatabase.CardNumberExists(localClient.cardNumber))
                break;

            else
                System.out.println("Номер карты не найден! Попробуйте снова.");
        }


        System.out.println("Введите свой ПИН-код (длина - 4 символа): ");
    }
}
