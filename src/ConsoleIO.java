import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class ConsoleIO
{
    public static int balance;
    private static Client currentClient = new Client();
    private static final Scanner sc = new Scanner(System.in);



    private static void Clear()
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


    private static void ShowHeader()
    {
        ConsoleIO.Clear();

        System.out.println("////////////////////////////////////");
        System.out.println("/             БАНКОМАТ             /");
        System.out.println("////////////////////////////////////");

        System.out.println("\n Баланс банкомата: " + balance);
        System.out.println("____________________________________\n\n\n");
    }


    public static void ShowMainMenu()
    {
        ShowHeader();

        System.out.println("           ГЛАВНОЕ МЕНЮ\n");
        System.out.println("Возможные опции:\n1. Создать счёт\n2. Войти в счёт\n3. Завершить работу\n");
        System.out.println("Введите номер интересующей опции:");
    }


    private static void ShowAccount()
    {
        ShowHeader();

        System.out.println("           АККАУНТ\n");
        System.out.println("Здравствуйте, " + currentClient.FIO + "!");
        System.out.println("\nБаланс на вашем счету: " + currentClient.accountBalance);
        System.out.println("\nВыберите дальнейшее действие: внести средства (1), снять средства (2), выйти из аккаунта (3).");
    }


    // Для ввода номера карты
    public static long GetOption(long lower_boundary, long upper_boundary)
    {
        long res;


        while(true)
        {
            while(!sc.hasNextLong())
            {
                System.out.println("\nНекорректное значение! Попробуйте снова.");
                sc.next();
            }


            res = sc.nextLong();

            if (res >= lower_boundary && res <= upper_boundary)
                break;

            else
                System.out.println("\nНомер карты должен состоять из 16 цифр! Попробуйте снова.");
        }


        return res;
    }


    // Для ввода обычных чисел (опций, сумм, ПИН-кода)
    public static int GetOption(int lower_boundary, int upper_boundary, String errorMessage)
    {
        int res;


        while(true)
        {
            while(!sc.hasNextInt())
            {
                System.out.println("\nНекорректное значение! Попробуйте снова.");
                sc.next();
            }


            res = sc.nextInt();

            if (res >= lower_boundary && res <= upper_boundary)
                break;

            else
                System.out.println("\n" + errorMessage);
        }


        return res;
    }


    // Для ввода ФИО
    public static String GetOption()
    {
        String input;


        while(true)
        {
            input = sc.nextLine();

            if (input.matches("[а-яА-Я ]+"))
                break;

            else
                System.out.println("\nНекорректное значение! Попробуйте снова.");
        }


        return input;
    }


    private static boolean isValidatedPIN()
    {
        int localPIN;


        for (int i = 1; i < 4; i++)
        {
            localPIN = ConsoleIO.GetOption(1000, 9999, "ПИН-код должен состоять из 4 цифр!");

            if (localPIN == currentClient.PIN)
                return true;

            else if (i != 3)
                System.out.println("\nНеправильный ПИН-код! Осталось попыток: " + (3 - i));
        }


        return false;
    }


    private static void AddMoney()
    {
        int sum = ConsoleIO.GetOption(1, 999999, "Сумма пополнения должна быть в пределах 1-999999!");

        balance += sum;
        currentClient.accountBalance += sum;

        System.out.println("Счёт успешно пополнен! Для возврата к аккаунту нажмите любую кнопку...");
        sc.next();
    }


    private static void WithdrawMoney()
    {
        int sum;

        while (true)
        {
            sum = ConsoleIO.GetOption(1, currentClient.accountBalance,  "Сумма снятия должна быть в пределах 1-" + currentClient.accountBalance + "!");

            if (sum > balance)
                System.out.println("Снятие невозможно: в банкомате есть всего лишь " + balance + "! Выберите другую сумму.");

            else
                break;
        }

        balance -= sum;
        currentClient.accountBalance -= sum;

        System.out.println("Деньги успешно выведены! Для возврата к аккаунту нажмите любую кнопку...");
        sc.next();
    }


    public static void CreateClientAccount()
    {
        ShowHeader();


        System.out.println("Введите своё ФИО (кириллицей):");
        currentClient.FIO = ConsoleIO.GetOption();

        System.out.println("Введите ПИН-код (должен состоять из 4 цифр): ");
        currentClient.PIN = ConsoleIO.GetOption(999, 10000, "ПИН-код должен состоять из 4 цифр!");

        currentClient.cardNumber = Client.GenerateCardNumber();
        currentClient.accountBalance = 0;


        ClientDatabase.clients.add(currentClient);
        System.out.println("\nАккаунт создан! Номер вашей карты: " + currentClient.cardNumber + "\nНажмите любую кнопку, чтобы выйти на главный экран...");

        sc.next();
    }


    public static void EnterClientAccount()
    {
        ShowHeader();


        while (true)
        {
            System.out.println("Введите номер своей карты в формате XXXXXXXXXXXXXXXX: ");
            currentClient.cardNumber = ConsoleIO.GetOption(1000000000000000L, 9999999999999999L);

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

                switch (ConsoleIO.GetOption(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова."))
                {
                    case (1) -> ConsoleIO.AddMoney();
                    case (2) -> ConsoleIO.WithdrawMoney();
                    case (3) -> toContinue = false;
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

            System.out.println("\nПИН-код был неверно введён 3 раза, аккаунт заблокирован на день.");
            System.out.println("Нажмите любую кнопку, чтобы выйти в главное меню...");
            sc.next();
        }
    }


    public static void CloseScanner()
    {
        sc.close();
    }
}
