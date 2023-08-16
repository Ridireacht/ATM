import java.util.Date;


public class Client {

  public String FIO;
  public String cardNumber;
  public int PIN;
  public long accountBalance;
  public Date availableSince = new Date();


  public static String generateCardNumber() {
    int numberPart;
    String cardNumber = "";

    do {
      for (int i = 0; i < 4; i++) {
        numberPart = (int) (1000 + (Math.random() * (9999 - 1000)));
        cardNumber += numberPart;

        if (i != 3)
          cardNumber += "-";
      }
    }
    while (ClientDatabase.getClientByCard(cardNumber) != null);

    return cardNumber;
  }
}
