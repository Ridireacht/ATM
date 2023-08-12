import java.util.Scanner;


public class ConsoleIO
{
    public static int GetOption()
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите ваш выбор: ");

        while(!sc.hasNextInt())
            System.out.println("Некорректный выбор! Попробуйте снова\n");

        sc.close();
        return sc.nextInt();
    }
}
