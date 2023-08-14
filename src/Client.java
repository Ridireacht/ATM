import java.util.Date;


public class Client
{
    public String FIO;
    public long cardNumber;
    public int PIN;
    public int accountBalance;
    public Date availableSince = new Date();


    public static long GenerateCardNumber()
    {
        long number;

        do
        {
            number = 1000000000000000L + (long) (Math.random() * (9999999999999999L - 1000000000000000L));
        }
        while(ClientDatabase.GetClientByCard(number) != null);

        return number;
    }
}
