import java.util.Scanner;


public class ConsoleIO
{
    public static int GetOption()
    {
        int res = 0;
        boolean isSuitable = false;
        Scanner sc = new Scanner(System.in);


        System.out.print("Введите ваш выбор: ");

        do
        {
            while(!sc.hasNextInt())
            {
                System.out.println("\nНекорректное значение! Попробуйте снова.");
                sc.next();
            }


            res = sc.nextInt();

            if (res > 0 && res < 5)
                isSuitable = true;

            if (!isSuitable)
                System.out.println("\nЗначение должно быть в пределах от 1 до 4! Попробуйте снова.");

        } while(!isSuitable);


        sc.close();
        return res;
    }
}
