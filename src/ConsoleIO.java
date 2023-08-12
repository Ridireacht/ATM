import java.util.Scanner;


public class ConsoleIO
{
    public static int GetOption()
    {
        int res = 0;
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
}
