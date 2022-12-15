package fr.crossessentials.crossessentials.economy;

import java.math.BigDecimal;

/**
 * economy account of a player
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class EconomyAccount {
    BigDecimal balance = new BigDecimal(0);
    int activeTransaction = -1;

    public synchronized void setActiveTransaction(int transactionId) {
        if(transactionId == -1)throw new RuntimeException("-1 is not allowed in setActiveTransaction");
        this.activeTransaction = transactionId;
    }

    public synchronized void removeActiveTransaction(){
        this.activeTransaction = -1;
    }

    public synchronized int getActiveTransaction() {
        return activeTransaction;
    }

    public BigDecimal getBalance() { return balance; }

    public synchronized void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public synchronized void addBalance(double amount){
        this.balance.add(BigDecimal.valueOf(amount));
    }
}
