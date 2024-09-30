import java.util.Scanner;

class BankAccount {
    private String accountHolderName;
    private String accountNumber;
    private double balance;

    public BankAccount(String accountHolderName, String accountNumber) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited " + amount + ". Current balance: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew " + amount + ". Current balance: " + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            recipient.balance += amount;
            System.out.println("Successfully transferred " + amount + " to " + recipient.accountHolderName);
        } else {
            System.out.println("Transfer failed. Insufficient balance or invalid amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: " + balance);
    }
}

public class banky {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter account holder's name for Account 1:");
        String name1 = scanner.nextLine();
        System.out.println("Enter account number for Account 1:");
        String accNumber1 = scanner.nextLine();
        BankAccount account1 = new BankAccount(name1, accNumber1);

        System.out.println("Enter account holder's name for Account 2:");
        String name2 = scanner.nextLine();
        System.out.println("Enter account number for Account 2:");
        String accNumber2 = scanner.nextLine();
        BankAccount account2 = new BankAccount(name2, accNumber2);

        BankAccount activeAccount = null;

        System.out.print("Enter your account number: ");
        String enteredAccount = scanner.nextLine();

        if (enteredAccount.equals(accNumber1)) {
            activeAccount = account1;
        } else if (enteredAccount.equals(accNumber2)) {
            activeAccount = account2;
        } else {
            System.out.println("Account not found.");
            return;
        }

        while (true) {
            System.out.println("\nBank Menu:");
            System.out.println("1. Deposit Funds");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Transfer Funds");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    activeAccount.deposit(depositAmount);
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    activeAccount.withdraw(withdrawAmount);
                    break;

                case 3:
                    System.out.print("Enter recipient's account number: ");
                    scanner.nextLine();
                    String recipientAcc = scanner.nextLine();
                    BankAccount recipient = null;

                    if (recipientAcc.equals(accNumber1)) {
                        recipient = account1;
                    } else if (recipientAcc.equals(accNumber2)) {
                        recipient = account2;
                    } else {
                        System.out.println("Recipient account not found.");
                        break;
                    }

                    if (recipient != activeAccount) {
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        activeAccount.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Cannot transfer to the same account.");
                    }
                    break;

                case 4:
                    activeAccount.checkBalance();
                    break;

                case 5:
                    System.out.println("Thank you for using the bank system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
