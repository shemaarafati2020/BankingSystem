package Bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import Exceptions.InvalidAmount;
import Exceptions.MaxBalance;
import Exceptions.MaxWithdraw;

public class BankAccount implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final String name;
        private final String acc_num;
        protected double balance;
        protected double min_balance;
        private final List<Transaction> transactions = new ArrayList<>();

        public BankAccount(String name, double balance, double min_balance) {
                if (balance < min_balance) {
                        throw new IllegalArgumentException(
                                        "Initial balance cannot be less than the minimum required balance: " + min_balance);
                }
                this.name = name;
                this.balance = balance;
                this.min_balance = min_balance;
                this.acc_num = generateAccountNumber();
                recordTransaction(TransactionType.ACCOUNT_CREATED, balance,
                                "Account created with an initial deposit");
        }

        private String generateAccountNumber() {
                return "AC" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        public void deposit(double amount) throws InvalidAmount {
                deposit(amount, "Deposit");
        }

        public void deposit(double amount, String description) throws InvalidAmount {
                deposit(amount, description, TransactionType.DEPOSIT);
        }

        public void deposit(double amount, String description, TransactionType type) throws InvalidAmount {
                if (amount <= 0) {
                        throw new InvalidAmount("Deposit amount must be greater than zero.");
                }
                balance += amount;
                recordTransaction(type, amount, description);
        }

        public void withdraw(double amount) throws MaxWithdraw, MaxBalance {
                withdraw(amount, "Withdrawal");
        }

        public void withdraw(double amount, String description) throws MaxWithdraw, MaxBalance {
                withdraw(amount, description, TransactionType.WITHDRAWAL);
        }

        public void withdraw(double amount, String description, TransactionType type) throws MaxWithdraw, MaxBalance {
                if ((balance - amount) >= min_balance && amount <= balance) {
                        balance -= amount;
                        recordTransaction(type, amount, description);
                }

                else {
                        throw new MaxBalance("Insufficient Balance");
                }
        }

        protected void recordTransaction(TransactionType type, double amount, String description) {
                transactions.add(new Transaction(type, amount, balance, description));
        }

        public double getbalance() {
                return balance;
        }

        public String getAccountNumber() {
                return acc_num;
        }

        public String getOwnerName() {
                return name;
        }

        public double getMinBalance() {
                return min_balance;
        }

        protected void setMinBalance(double min_balance) {
                this.min_balance = min_balance;
        }

        public List<Transaction> getTransactions() {
                return Collections.unmodifiableList(transactions);
        }

        public String getAccountType() {
                return getClass().getSimpleName().replace("Account", " Account").trim();
        }

        @Override
        public String toString() {
                return String.format("%s (%s) - Balance: %.2f", name, acc_num, balance);
        }
}
