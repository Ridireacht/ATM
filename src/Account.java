import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Account {

  public static int balance;
  private static Client currentClient = new Client();


  private static boolean isCorrectPIN() {
    int localPIN;

    for (int i = 1; i < 4; i++) {
      localPIN = ConsoleIO.inputNumber(1000, 9999, "ПИН-код должен состоять из 4 цифр!");

      if (localPIN == currentClient.PIN) {
        return true;
      }

      else if (i != 3) {
        System.out.println("\nНеправильный ПИН-код! Осталось попыток: " + (3 - i));
      }
    }

    return false;
  }


  private static void verifyCardNumber() {
    System.out.println("Введите номер своей карты в формате XXXX-XXXX-XXXX-XXXX: ");

    while (true) {
      currentClient.cardNumber = ConsoleIO.inputCardNumber();

      if (ClientDatabase.getClientByCard(currentClient.cardNumber) != null) {
        break;
      }

      else {
        System.out.println("\nНомер карты не найден! Попробуйте снова.");
      }
    }
  }


  private static void addMoney() {
    System.out.println("\nВведите, сколько вы хотите добавить:");

    int sum = ConsoleIO.inputNumber(1, 1000000,
        "Сумма пополнения должна быть в пределах 1-1000000!");

    balance += sum;
    currentClient.accountBalance += sum;

    System.out.println("\nСчёт успешно пополнен! Для возврата к аккаунту нажмите Enter...");

    ConsoleIO.pressEnterToExit();
  }


  private static void withdrawMoney() {
    if (currentClient.accountBalance == 0) {
      System.out.println("\nВаш счёт пуст! Попробуйте другую опцию.");
      return;
    }


    System.out.println("\nВведите, сколько вы хотите cнять:");

    int sum;

    while (true) {
      sum = ConsoleIO.inputNumber(1, currentClient.accountBalance,
          "Сумма снятия должна быть в пределах 1-" + currentClient.accountBalance + "!");

      if (sum > balance) {
        System.out.println("Снятие невозможно: в банкомате есть только " + balance + "! Выберите другую сумму.");
      }

      else {
        break;
      }
    }

    balance -= sum;
    currentClient.accountBalance -= sum;

    System.out.println("\nДеньги успешно выведены! Для возврата к аккаунту нажмите Enter...");

    ConsoleIO.pressEnterToExit();
  }


  public static void createClientAccount() {
    ConsoleIO.showHeader(balance);

    System.out.println("         CОЗДАНИЕ АККАУНТА\n");

    System.out.println("\nВведите своё ФИО (латиницей):");
    currentClient.FIO = ConsoleIO.inputFIO();

    System.out.println("\nВведите ПИН-код (должен состоять из 4 цифр): ");
    currentClient.PIN = ConsoleIO.inputNumber(1000, 9999, "ПИН-код должен состоять из 4 цифр!");

    currentClient.cardNumber = Client.generateCardNumber();
    currentClient.accountBalance = 0;

    ClientDatabase.clients.add(currentClient);
    System.out.println("\nАккаунт создан! Номер вашей карты: " + currentClient.cardNumber);

    System.out.println("\nНажмите Enter, чтобы выйти на главный экран...");

    ConsoleIO.pressEnterToExit();
  }


  public static void enterClientAccount() {
    ConsoleIO.showHeader(balance);

    System.out.println("           ВХОД В АККАУНТ\n");

    verifyCardNumber();
    currentClient = ClientDatabase.getClientByCard(currentClient.cardNumber);

    if (currentClient.availableSince.after(new Date())) {
      long diffInMillies = Math.abs(currentClient.availableSince.getTime() - new Date().getTime());
      long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

      System.out.println("\nДанный аккаунт всё ещё заблокирован!");
      System.out.println("Время до снятия блокировки: " + diff + " минут.");
      System.out.println("\nНажмите Enter, чтобы выйти в главное меню...");

      ConsoleIO.pressEnterToExit();

      return;
    }

    System.out.println("\nВведите свой ПИН-код (длина - 4 символа): ");

    if (isCorrectPIN()) {
      boolean toContinue = true;

      do {
        ConsoleIO.showAccount(balance, currentClient);

        switch (ConsoleIO.inputNumber(1, 3, "Выбор должен быть от 1 до 3! Попробуйте снова.")) {
          case (1) -> {
            Account.addMoney();
            ClientDatabase.updateClient(currentClient);
          }

          case (2) -> {
            Account.withdrawMoney();
            ClientDatabase.updateClient(currentClient);
          }

          case (3) -> toContinue = false;
        }

      } while (toContinue);
    }

    else {
      currentClient.availableSince = new Date();

      Calendar cal = Calendar.getInstance();
      cal.setTime(currentClient.availableSince);
      cal.add(Calendar.DATE, 1);
      currentClient.availableSince = cal.getTime();

      System.out.println("\nПИН-код был неверно введён 3 раза, аккаунт заблокирован на день.");
      System.out.println("\nНажмите Enter, чтобы выйти в главное меню...");

      ConsoleIO.pressEnterToExit();
    }
  }
}
