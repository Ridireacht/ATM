import java.util.Scanner;


public class ConsoleIO
{
    public static void Clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public static int GetOption()
    {
        int res;
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите ваш выбор: ");


        // Цикл работает до тех пор, пока не будет найден подходящий int
        while(true)
        {
            // Перебираем входной поток, пока не найдём там int
            while(!sc.hasNextInt())
            {
                System.out.println("\nНекорректное значение! Попробуйте снова.");
                sc.next();
            }


            res = sc.nextInt();

            if (res > 0 && res < 5)
                break;

            else
                System.out.println("\nЗначение должно быть в пределах от 1 до 4! Попробуйте снова.");
        }


        sc.close();
        return res;
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
}
