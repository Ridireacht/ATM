import java.util.Date;


public class Client {

  public String FIO;
  public long cardNumber;
  public int PIN;
  public int accountBalance;
  public Date availableSince = new Date();


  public static long generateCardNumber() {
    long number;

    do {
      number = 1000000000000000L + (long) (Math.random() * (9999999999999999L - 1000000000000000L));
    }
    while (ClientDatabase.getClientByCard(number) != null);

    return number;
  }
}
