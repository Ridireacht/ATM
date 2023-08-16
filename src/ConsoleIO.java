import java.io.IOException;
import java.util.Scanner;


public class ConsoleIO {

  private static final Scanner sc = new Scanner(System.in);


  private static void clear() {
    try {
      String os = System.getProperty("os.name");

      if (os.contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      }

      else {
        Runtime.getRuntime().exec("clear");
      }

    } catch (Exception e) {
      System.out.println("При попытке очистить экран возникла непредвиденная ошибка!\n");
    }
  }


  public static void pressEnterToExit() {
    try {
      System.in.read();
    }

    catch (IOException e2) {
      System.out.println("\nПри попытке считать нажатие Enter возникла непредвиденная ошибка!");
    }
  }


  public static void showHeader(long balance) {
    ConsoleIO.clear();

    System.out.println("////////////////////////////////////");
    System.out.println("/             БАНКОМАТ             /");
    System.out.println("////////////////////////////////////");

    System.out.println("\n Баланс банкомата: " + balance);
    System.out.println("____________________________________\n\n\n");
  }


  public static void showMainMenu(long balance) {
    showHeader(balance);

    System.out.println("            ГЛАВНОЕ МЕНЮ\n");
    System.out.println("Возможные опции:\n1. Создать счёт\n2. Войти в счёт\n3. Завершить работу\n");
    System.out.println("Введите номер интересующей опции:");
  }


  public static void showAccount(long balance, Client client) {
    showHeader(balance);

    System.out.println("              АККАУНТ\n");
    System.out.println("Здравствуйте, " + client.FIO + "!");
    System.out.println("\nБаланс на вашем счету: " + client.accountBalance);
    System.out.println("\nВыберите дальнейшее действие: внести средства (1), снять средства (2), выйти из аккаунта (3).");
  }


  public static String inputCardNumber() {
    String input;

    while (true) {

      input = sc.nextLine();

      if (input.matches("[1-9][0-9][0-9][0-9]-[1-9][0-9][0-9][0-9]-[1-9][0-9][0-9][0-9]-[1-9][0-9][0-9][0-9]")) {
        break;
      }

      else {
        System.out.println("\nВведённая строка не соответствует установленному формату! Попробуйте снова.");
      }
    }

    return input;
  }


  public static int inputNumber(int lower_boundary, int upper_boundary, String errorMessage) {
    int input;

    while (true) {

      while (!sc.hasNextInt()) {
        System.out.println("\nНекорректное значение! Попробуйте снова.");
        sc.next();
      }

      input = sc.nextInt();

      if (input >= lower_boundary && input <= upper_boundary) {
        break;
      }

      else {
        System.out.println("\n" + errorMessage);
      }
    }

    sc.nextLine();  // чтобы убрать \n, которое осталось после nextInt()

    return input;
  }


  public static String inputFIO() {
    String input;

    while (true) {
      input = sc.nextLine();

      if (input.matches("[a-zA-Z ]+") && input.trim().length() > 0) {
        break;
      }

      else {
        System.out.println("\nНекорректное значение! Попробуйте снова.");
      }
    }

    return input;
  }


  public static void closeScanner() {
    sc.close();
  }
}
