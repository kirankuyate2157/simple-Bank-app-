
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Account {
    private static final String FILE_NAME = "AccountData.txt";

    private String accountNumber;
    private double balance;
    private Login usr;

    Account(Login usr, String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.usr = usr;
    }

    Account() {
        System.out.println("Please provide account details!");
    }

    Scanner sc = new Scanner(System.in);

    void deposit(double amount) {
        balance += amount;
        // Saving transaction details to file
        saveTransactionDetails("Deposit", amount);
        // updateBalance(accountNumber, balance);
        System.out.println("Deposit of $" + amount + " successful. Current balance is $" + balance);
    }

    void withdraw(double amount) {
        if (balance < amount) {
            System.out.println("Insufficient funds. Please try again.");
            return;
        }
        balance -= amount;
        updateBalance(accountNumber, balance);
        // Saving transaction details to file
        saveTransactionDetails("Withdrawal", amount);
        System.out.println("Withdrawal of $" + amount + " successful. Current balance is $" + balance);
    }

    void miniStatement() {
        // Reading last 5 transaction details from file
        List<String> transactions = readLastNTransactions(5);
        int x = 5;
        if (transactions.size() < 5)
            x = transactions.size();
        System.out.println("Last " + x + " transaction details transactions :");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
        transactions.clear();
    }

    private void saveTransactionDetails(String type, double amount) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            LocalDateTime now = LocalDateTime.now();
            bufferedWriter.write(accountNumber + "," + type + "," + amount + "," + now + ",balance : " + balance);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // number of transactions cout all
    private int numberOfTransactions() {
        List<String> transactions = new ArrayList<>();
        int cnt = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null) {
                String[] transactionData = line.split(",");
                if (transactionData[0].equals(accountNumber)) {
                    cnt++;
                    transactions.add(transactionData[1] + " of $" + transactionData[2] + " on " + transactionData[3]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cnt;
    }

    public boolean isLoanable(Login user) {
        if (numberOfTransactions() > 10 && !user.loanStatus)
            return true;
        return false;
    }

    private List<String> readLastNTransactions(int n) {
        List<String> transactions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null && transactions.size() < n) {
                String[] transactionData = line.split(",");
                if (transactionData[0].equals(accountNumber)) {
                    transactions.add(transactionData[1] + " of $" + transactionData[2] + " on " + transactionData[3]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private void updateBalance(String accountNumber, double newBalance) {
        try {
            File file = new File("userData.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[7].equals(accountNumber)) {
                    userData[11] = Double.toString(newBalance);
                    line = String.join(",", userData);
                }
                sb.append(line).append("\n");
            }
            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
            System.out.println("Balance updated successfully for account number " + accountNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLoanBalance(String accountNumber, double Loan, boolean tag) {
        try {
            File file = new File("userData.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[7].equals(accountNumber) && tag) {
                    double x = Double.parseDouble(userData[10]) + Loan;
                    userData[10] = Double.toString(x);
                    x = Double.parseDouble(userData[11]) + Loan;
                    userData[11] = Double.toString(x);
                    userData[9] = Boolean.toString(true);
                    line = String.join(",", userData);
                }
                if (userData[7].equals(accountNumber) && !tag) {
                    double x = Double.parseDouble(userData[10]) - Loan;
                    userData[10] = Double.toString(x);
                    if (x == 0) {
                        userData[9] = Boolean.toString(false);
                    }
                    line = String.join(",", userData);
                }
                sb.append(line).append("\n");
            }
            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
            System.out.println(" Loan Balance updated successfully for account number " + accountNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void takeLoan(boolean lStatus) {
        if (lStatus) {
            double amt = 20000.0;
            System.out.println("Appoved loan amount is : " + amt);
            System.out.print("if you want to continue : ");
            int choice = 0;
            choice = sc.nextInt();
            if (choice < 1)
                return;
            updateLoanBalance(accountNumber, amt, false);
            saveTransactionDetails("Loan ", amt);
        } else {
            System.out.println("Your not eligible for Loan ");
        }
    }

    void giveLoan(boolean lStatus) {
        if (!lStatus) {
            double amt = 20000.0;
            System.out.println("your amount is : " + amt);
            System.out.print("if you want to continue : ");
            int choice = 0;
            choice = sc.nextInt();
            if (choice < 1)
                return;
            updateLoanBalance(accountNumber, amt, false);
            saveTransactionDetails("Loan ", amt);
        } else {
            System.out.println("Your not eligible for Loan ");
        }
    }

    private boolean checkPassword(String a, int b) {
        boolean flg = false;
        try {
            File file = new File("userData.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[7].equals(accountNumber) && userData[1].equals(a)) {
                    userData[1] = Integer.toString(b);
                    System.out.println(b);
                    flg = true;
                    line = String.join(",", userData);

                }
                sb.append(line).append("\n");
            }
            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
            if (flg)
                return flg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    void changePassword() {
        String a;
        int b;
        System.out.println("Enter old password: ");
        a = sc.nextLine();
        System.out.println("Enter new password: ");
        b = sc.nextInt();
        boolean x = checkPassword(a, b);
        if (x) {
            saveTransactionDetails("Pin changed ", 0);
            System.out.println("password updated successfully");
        } else {
            System.out.println("Wrong password !");
        }
    }

    void bankDetails() {
        System.out.println("Welcome to Komal Secure Bank Pvt. Ltd.");
        System.out.println("We are one of the leading banks in India.");
        System.out.println(
                "Our services include: savings accounts, current accounts, fixed deposits, loans, and credit cards.");
        System.out.println(
                "Our branches are located in various cities across India, including Mumbai, Delhi, Bangalore, Chennai, Kolkata, and Hyderabad.");
        System.out.println("For more information, visit our website at www.komalsecurebank.com.");
    }

    void activeServices() {
        System.out.println("- emails");
        System.out.println("- SMS");
        if (usr.isDebitCard())
            System.out.println("- debit card");
        if (usr.isCreditCard())
            System.out.println("- credit card");
        if (usr.isChqueBook())
            System.out.println("- cheque Book");
        if (usr.isInternationalTransaction())
            System.out.println("Internationals transactions");

    }

    public static void main(String[] args) {
        Login user = new Login("shital", "8855");
        String accountNumber = user.getAccount();
        double amount = user.getBalance();
        System.out.println("Account : " + accountNumber + " Amount : " + amount + "\n");

        Account account = new Account(user, accountNumber, amount);

        // account.changePassword();
        // account.loan(account.isLoanable(user));
        // account.miniStatement();
        Login obj = new Login();
        obj.createAccount();

    }

    /*
     * if user give the credentials then check validations
     * if user is new user then create new account
     * check valid user
     * deposit
     * withdrawal
     * view balance
     * miniStatment
     * active services
     * bankDetails
     * change password
     * take or give loan
     * loanable?
     */

}