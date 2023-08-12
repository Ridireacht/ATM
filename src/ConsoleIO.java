import java.util.Scanner;


public class ConsoleIO
{
    private static int balance;


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


    public static void SetBalance(int sum)
    {
        balance = sum;
    }


    public static int GetOption(int lower_boundary, int upper_boundary, String errorMessage)
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
                System.out.println("\nНекорректное значение! Попробуйте снова.");;
        }


        sc.close();
        return input;
    }


    public static boolean ValidatePIN(int realPIN)
    {
        int localPIN;
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите ПИН-код: ");


        // На угадывание PIN даётся 3 попытки
        for (int i = 1; i < 4; i++)
        {
            // Перебираем входной поток, пока не найдём там int
            while(!sc.hasNextInt())
            {
                System.out.println("\nНекорректное значение! Попробуйте снова.");
                sc.next();
            }


            localPIN = sc.nextInt();

            if (localPIN == realPIN)
            {
                sc.close();
                return true;
            }

            else if (i != 3)
                System.out.println("\nНеправильный ПИН-код! Осталось попыток: " + (3 - i));
        }


        System.out.println("\nПИН-код был неверно введён 3 раза, банкомат заблокирован.");
        sc.close();
        return false;
    }


    public static ClientAccount CreateClientAccount()
    {
        ClientAccount currentClient = new ClientAccount();
        Scanner sc = new Scanner(System.in);


        ConsoleIO.Clear();
        ConsoleIO.ShowMenu("");


        System.out.println("Введите своё ФИО: ");
        currentClient.FIO = ConsoleIO.GetOption("Некорректное значение! Попробуйте снова.");

        System.out.println("Введите ПИН-код (должен состоять из 4 цифр): ");
        currentClient.PIN = ConsoleIO.GetOption(999, 10000, "ПИН-код должен состоять из 4 цифр!");

        currentClient.accountBalance = 0;

        return currentClient;
    }


    public static void EnterClientAccount()
    {
        ConsoleIO.Clear();
        ConsoleIO.ShowMenu("");

        System.out.println("Введите своё ФИО: ");

        System.out.println("Введите свой ПИН-код (длина - 4 символа): ");
    }
}
