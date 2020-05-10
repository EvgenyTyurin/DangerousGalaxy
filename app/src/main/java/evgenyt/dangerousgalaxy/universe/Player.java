package evgenyt.dangerousgalaxy.universe;

public class Player {
    private int balance = 1000;


    public boolean debBalance(int debit) {
        if (debit > balance)
            return false;
        balance -= debit;
        return true;
    }

    public void credBalance(int sum) {
        balance += sum;
    }

    public int getBalance() {
        return balance;
    }
}
