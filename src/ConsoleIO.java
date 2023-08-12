import java.util.Scanner;


public class ConsoleIO
{
    public static int GetOption()
    {
        int res;
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите ваш выбор: ");

        do
        {
            while(!sc.hasNextInt())
            {
                System.out.println("\nНеверный выбор! Попробуйте снова.");
                sc.next();
            }

            res = sc.nextInt();

        } while(res > 4 || res < 1);


        sc.close();
        return res;
    }
}
