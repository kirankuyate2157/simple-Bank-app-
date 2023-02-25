
import java.util.Scanner;

public class Bank {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();
        Account account = null;
        boolean loggedIn = false;

        while (true) {
            System.out.println("Welcome to the bank!");
            System.out.println("1. Login");
            System.out.println("2. Create new account");
            System.out.println("0. Exit");

            int choice = input.nextInt();

            if (choice == 1) {
                System.out.println("Enter your username:");
                String username = input.next();

                System.out.println("Enter your password:");
                String password = input.next();

                if (login.isValid(username, password)) {
                    account = new Account(username);
                    System.out.println("Login successful!");
                    loggedIn = true;
                } else {
                    System.out.println("Invalid username or password!");
                }
            } else if (choice == 2) {
                System.out.println("Enter your username:");
                String username = input.next();

                System.out.println("Enter your password:");
                String password = input.next();

                if (login.createUser(username, password)) {
                    account = new Account(username);
                    System.out.println("Account created successfully!");
                    loggedIn = true;
                } else {
                    System.out.println("Username already exists!");
                }
            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }

            if (loggedIn) {
                while (true) {
                    System.out.println("What would you like to do?");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdrawal");
                    System.out.println("3. View balance");
                    System.out.println("4. Mini statement");
                    System.out.println("5. Active services");
                    System.out.println("6. Bank details");
                    System.out.println("7. Change password");
                    System.out.println("8. Take or give loan");
                    System.out.println("9. Check loan eligibility");
                    System.out.println("0. Logout");

                    choice = input.nextInt();

                    if (choice == 1) {
                        System.out.println("Enter the amount to deposit:");
                        double amount = input.nextDouble();
                        account.deposit(amount);
                    } else if (choice == 2) {
                        System.out.println("Enter the amount to withdraw:");
                        double amount = input.nextDouble();
                        account.withdraw(amount);
                    } else if (choice == 3) {
                        System.out.println("Your balance is: " + account.getBalance());
                    } else if (choice == 4) {
                        account.printMiniStatement();
                    } else if (choice == 5) {
                        System.out.println("The following services are active: ");
                        account.printActiveServices();
                    } else if (choice == 6) {
                        account.printBankDetails();
                    } else if (choice == 7) {
                        System.out.println("Enter your current password:");
                        String oldPassword = input.next();
                        System.out.println("Enter your new password:");
                        String newPassword = input.next();
                        account.changePassword(oldPassword, newPassword);
                    } else if (choice == 8) {
                        System.out.println("Enter the loan amount:");
                        double loanAmount = input.nextDouble();
                        account.takeOrGiveLoan(loanAmount);
                    } else if (choice == 9) {
                        boolean loanEligibility = account.isLoanable();
                        if (loanEligibility) {
                            System.out.println("Congratulations! You are eligible");
                        } else {
                            System.out.println("Sorry, you are not eligible !");
                        }
                    }
                }
            }
        }
    }
}
