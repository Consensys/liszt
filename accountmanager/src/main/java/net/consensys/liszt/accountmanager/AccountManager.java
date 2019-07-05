package net.consensys.liszt.accountmanager;

import net.consensys.liszt.transactionmanager.TransactionManager;


public class AccountManager {

    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();

        String message = transactionManager.getMessage();
        System.out.println("Message: " + message);
    }
}
