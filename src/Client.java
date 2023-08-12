public class Client
{
    public String FIO;
    public long cardNumber;
    public int PIN;
    public int accountBalance;


    public static long GenerateCardNumber()
    {
        long number;

        do
        {
            number = 1000000000000000L + (long) (Math.random() * (9999999999999999L - 1000000000000000L));
        }
        while(ClientDatabase.CardNumberExists(number));

        return number;
    }
}
