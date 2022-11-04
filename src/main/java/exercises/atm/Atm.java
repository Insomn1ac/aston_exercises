package exercises.atm;

import exercises.atm.model.Account;

import java.util.Scanner;

import static exercises.atm.service.AtmService.*;

public class Atm {

    public static void main(String[] args) throws Exception {
        Account account = new Account();
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        option = mainMenu(option, scanner);
        if (option == 1) {
            newAccount(scanner);
        } else {
            signedIn(account, scanner);
        }
    }
}