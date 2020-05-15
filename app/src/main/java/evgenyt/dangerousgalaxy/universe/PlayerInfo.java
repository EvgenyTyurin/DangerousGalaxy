package evgenyt.dangerousgalaxy.universe;

import evgenyt.dangerousgalaxy.ui.PrefsWork;

public class PlayerInfo {
    public static final String PREFS_PLAYER_BALANCE = "Player.balance";
    private int balance;

    public PlayerInfo() {
        String balanceStr = PrefsWork.readSlot(PREFS_PLAYER_BALANCE);
        if (balanceStr.equals("")) {
            balance = 1000;
        } else {
            balance = Integer.valueOf(balanceStr);
        }
    }

    public boolean debBalance(int debit) {
        if (debit > balance)
            return false;
        balance -= debit;
        PrefsWork.saveSlot(PREFS_PLAYER_BALANCE, String.valueOf(balance));
        return true;
    }

    public void credBalance(int sum) {
        balance += sum;
        PrefsWork.saveSlot(PREFS_PLAYER_BALANCE, String.valueOf(balance));
    }

    public int getBalance() {
        return balance;
    }
}
