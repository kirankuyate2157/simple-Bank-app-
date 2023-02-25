import java.util.*;
import java.io.*;

public class Login {
    private static final String FILE_NAME = "UserData.txt";

    private String username; // 0
    private String password = "";// 1
    private String email; // 2
    private String phoneNumber;// 3
    private String birthDate;// 4
    private String openDate;// 5
    private boolean status;// 6
    private String accountNumber;// 7
    private String accountType;// 8
    public boolean loanStatus = false;// 9
    private double LA = 0;// 10
    private double balance;// 11
    private boolean debitCard = true;// 12
    private boolean creditCard = false;// 13
    private boolean internationalTransaction = false;// 14
    private boolean chqueBook = false;// 15

    Login(String username, String password) {
        this.username = username;
        this.password = password;

        boolean user = isUserExist(username, password);
        if (user) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed !");
        }
    }

    Login() {
        System.out.println(" Please Provide credentials !");
    }

    // if present, check credentials yes if not present no
    public boolean isUserExist(String username, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    br.close();
                    username = userData[0];
                    password = userData[1];
                    email = userData[2];
                    phoneNumber = userData[3];
                    birthDate = userData[4];
                    openDate = userData[5];
                    status = Boolean.parseBoolean(userData[6]);
                    accountNumber = userData[7];
                    accountType = userData[8];
                    loanStatus = Boolean.parseBoolean(userData[9]);
                    LA = Double.parseDouble(userData[10]);
                    balance = Double.parseDouble(userData[11]);
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAccount() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLoanActive() {
        return loanStatus;
    }

    public boolean isChqueBook() {
        return chqueBook;
    }

    public boolean isCreditCard() {
        return creditCard;
    }

    public boolean isDebitCard() {
        return debitCard;
    }

    public boolean isInternationalTransaction() {
        return internationalTransaction;
    }

    public void updateLoan(double lA) {
        LA += lA;
        if (lA < 0) {
            balance += lA;
        }
        if (LA == 0)
            loanStatus = false;
    }

    public void createAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        username = sc.nextLine();

        System.out.println("Enter your email address: ");
        email = sc.nextLine();

        System.out.println("Enter your phone number: ");
        phoneNumber = sc.nextLine();

        System.out.println("Enter your birth date (dd/mm/yyyy): ");
        birthDate = sc.nextLine();

        System.out.println("Enter the account type (Checking/Savings): ");
        accountType = sc.nextLine();

        System.out.println("Set 6 digit password: ");
        password = sc.nextLine();

        System.out.println("Enter the opening balance: ");
        balance = sc.nextDouble();

        // Generating a unique account number for the customer
        accountNumber = "KKC" + (int) (Math.random() * 100000);

        // Setting the account status to active
        status = true;

        // Setting the opening date to the current date
        openDate = java.time.LocalDate.now().toString();

        // write account data to file
        try

        {
            FileWriter fileWriter = new FileWriter(FILE_NAME, true); // true parameter to append data to existing file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(username + "," + password + "," + email + "," + phoneNumber + "," + birthDate + ","
                    + openDate + "," + status + ","
                    + accountNumber + "," + accountType + "," + loanStatus + "," + LA + "," + balance + ","
                    + debitCard + "," + creditCard + "," + chqueBook + "," + internationalTransaction
                    + ",");
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        System.out.println("Congratulations, your account has been created successfully!");
        System.out.println("Your account details are: ");
        System.out.println("Account Holder Name: " + username);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Opening Balance: $" + balance);
        System.out.println("Account Open Date: " + openDate);
        System.out.println("Account Status: Active");

        // close resources
        sc.close();
    }

    public static void main(String[] args) {
        Login user = new Login();
        user.createAccount();
    }
}
