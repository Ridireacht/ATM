import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class ConsoleIO
{
    public static int balance;
    private static Client currentClient = new Client();



    public static void Clear()
    {
        try
        {
            String os = System.getProperty("os.name");

            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            else
                Runtime.getRuntime().exec("clear");
        }

        catch (Exception e)
        {
            System.out.println("При попытке очистить экран возникла непредвиденная ошибка!\n");
        }
    }


    public static void ShowMenu(String additionalContent)
    {
        ConsoleIO.Clear();

        System.out.println("////////////////////////");
        System.out.println("/       БАНКОМАТ       /");
        System.out.println("////////////////////////");

        System.out.println("\n Баланс банкомата: " + balance);

        System.out.println("\n" + additionalContent);
    }


    public static void ShowError(String errorString)
    {
        ConsoleIO.Clear();

        System.out.println("////////////////////////");
        System.out.println("/       БАНКОМАТ       /");
        System.out.println("////////////////////////");

        System.out.println("\n ОШИБКА!");

        System.out.println("\n" + errorString);
    }


    public static void ShowAccount()
    {
        ConsoleIO.Clear();

        System.out.println("////////////////////////");
        System.out.println("/       БАНКОМАТ       /");
        System.out.println("////////////////////////");

        System.out.println("\n Баланс банкомата: " + balance);

        System.out.println("\nЗдравствуйте, " + currentClient.FIO + "!");

        System.out.println("\nБаланс на вашем счету: " + currentClient.accountBalance);

        System.out.println("\nВыберите дальнейшее действие: внести средства (1), снять средства (2), выйти из аккаунта (3).");
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


    public static boolean isValidatedPIN()
    {
        int localPIN;

        System.out.print("Введите ПИН-код: ");


        for (int i = 1; i < 4; i++)
        {
            localPIN = ConsoleIO.GetOption(999, 10000, "ПИН-код должен состоять из 4 цифр!");

            if (localPIN == currentClient.PIN)
                return true;

            else if (i != 3)
                System.out.println("\nНеправильный ПИН-код! Осталось попыток: " + (3 - i));
        }


        System.out.println("\nПИН-код был неверно введён 3 раза, аккаунт заблокирован на день.");
        return false;
    }


    public static void AddMoney()
    {
        int sum = ConsoleIO.GetOption(0, 1000000, "Сумма пополнения должна быть больше 0 и меньше 1000000!");

        balance += sum;
        currentClient.accountBalance += sum;

        System.out.println("Счёт успешно пополнен! Для возврата к аккаунту нажмите любую кнопку...");
        Scanner sc = new Scanner(System.in);
        sc.next();
    }


    public static void WithdrawMoney()
    {
        int sum;

        while (true)
        {
            sum = ConsoleIO.GetOption(0, currentClient.accountBalance + 1,  "Сумма снятия должна быть больше 0 и меньше" + currentClient.accountBalance + "!");

            if (sum > balance)
                System.out.println("Снятие невозможно: в банкомате есть всего лишь " + balance + "! Выберите другую сумму.");

            else
                break;
        }

        balance -= sum;
        currentClient.accountBalance -= sum;

        System.out.println("Деньги успешно выведены! Для возврата к аккаунту нажмите любую кнопку...");
        Scanner sc = new Scanner(System.in);
        sc.next();
    }


    public static void CreateClientAccount()
    {
        ConsoleIO.Clear();
        ConsoleIO.ShowMenu("");


        System.out.println("Введите своё ФИО (кириллицей): ");
        currentClient.FIO = ConsoleIO.GetOption("Некорректное значение! Попробуйте снова.");

        System.out.println("Введите ПИН-код (должен состоять из 4 цифр): ");
        currentClient.PIN = ConsoleIO.GetOption(999, 10000, "ПИН-код должен состоять из 4 цифр!");

        currentClient.cardNumber = Client.GenerateCardNumber();
        currentClient.accountBalance = 0;


        ClientDatabase.clients.add(currentClient);
        System.out.println("\nАккаунт создан! Нажмите любую кнопку, чтобы выйти на главный экран...");

        Scanner sc = new Scanner(System.in);
        sc.next();
    }


    public static void EnterClientAccount()
    {
        ConsoleIO.Clear();
        ConsoleIO.ShowMenu("");


        while (true)
        {
            System.out.println("Введите номер своей карты в формате XXXXXXXXXXXXXXXX: ");
            currentClient.cardNumber = ConsoleIO.GetOption(1000000000000000L, 9999999999999999L, "Номер карты должен состоять из 16 цифр! Попробуйте снова.");

            if (ClientDatabase.GetClientByCard(currentClient.cardNumber) != null)
                break;

            else
                System.out.println("Номер карты не найден! Попробуйте снова.");
        }



        currentClient = ClientDatabase.GetClientByCard(currentClient.cardNumber);

        if (currentClient.availableSince.after(new Date()))
        {
            long diffInMillies = Math.abs(currentClient.availableSince.getTime() - new Date().getTime());
            long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

            System.out.println("Данный аккаунт всё ещё заблокирован!");
            System.out.println("Время до снятия блокировки: " + diff + " минут.");
            System.out.println("Нажмите любую кнопку, чтобы выйти в главное меню...");
            Scanner sc = new Scanner(System.in);
            sc.next();

            return;
        }



        System.out.println("Введите свой ПИН-код (длина - 4 символа): ");

        if (isValidatedPIN())
        {
            boolean toContinue = true;

            do
            {
                ConsoleIO.ShowAccount();

                switch(ConsoleIO.GetOption(0, 4, "Выбор должен быть от 1 до 3! Попробуйте снова."))
                {
                    case(1):
                    {
                        ConsoleIO.AddMoney();
                        break;
                    }

                    case(2):
                    {
                        ConsoleIO.WithdrawMoney();
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

        else
        {
            currentClient.availableSince = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(currentClient.availableSince);
            cal.add(Calendar.DATE, 1);
            currentClient.availableSince = cal.getTime();

            System.out.println("Нажмите любую кнопку, чтобы выйти в главное меню...");
            Scanner sc = new Scanner(System.in);
            sc.next();
        }
    }
}
