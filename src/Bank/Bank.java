package Bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;

import Exceptions.AccNotFound;
import Exceptions.InvalidAmount;
import Exceptions.MaxBalance;
import Exceptions.MaxWithdraw;

public class Bank implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final List<BankAccount> accounts = new ArrayList<>();

        public synchronized int addAccount(BankAccount acc) {
                accounts.add(acc);
                return accounts.size() - 1;
        }

        public synchronized int addAccount(String name, double balance, double maxWithLimit) {
                SavingsAccount acc = new SavingsAccount(name, balance, maxWithLimit);
                return this.addAccount(acc);
        }

        public synchronized int addAccount(String name, double balance, String tradeLicense) {
                CurrentAccount acc = new CurrentAccount(name, balance, tradeLicense);
                return this.addAccount(acc);
        }

        public synchronized int addAccount(String name, String institutionName, double balance, double min_balance) {
                StudentAccount acc = new StudentAccount(name, balance, institutionName);
                return this.addAccount(acc);
        }

        public synchronized BankAccount findAccount(String accountNum) {
                if (accountNum == null) {
                        return null;
                }
                for (BankAccount account : accounts) {
                        if (account.getAccountNumber().equalsIgnoreCase(accountNum)) {
                                return account;
                        }
                }
                return null;
        }

        private BankAccount requireAccount(String accountNumber) throws AccNotFound {
                BankAccount account = findAccount(accountNumber);
                if (account == null) {
                        throw new AccNotFound("Account Not Found");
                }
                return account;
        }

        public synchronized void deposit(String accountNum, double amount) throws InvalidAmount, AccNotFound {
                deposit(accountNum, amount, "Deposit");
        }

        public synchronized void deposit(String accountNum, double amount, String description)
                        throws InvalidAmount, AccNotFound {
                if (amount <= 0) {
                        throw new InvalidAmount("Invalid Deposit amount");
                }
                BankAccount account = requireAccount(accountNum);
                account.deposit(amount, description, TransactionType.DEPOSIT);
        }

        public synchronized void withdraw(String accountNum, double amount)
                        throws MaxBalance, AccNotFound, MaxWithdraw, InvalidAmount {
                withdraw(accountNum, amount, "Withdrawal");
        }

        public synchronized void withdraw(String accountNum, double amount, String description)
                        throws MaxBalance, AccNotFound, MaxWithdraw, InvalidAmount {
                BankAccount account = requireAccount(accountNum);

                if (amount <= 0) {
                        throw new InvalidAmount("Invalid Amount");
                }

                if (amount > account.getbalance()) {
                        throw new MaxBalance("Insufficient Balance");
                }
                account.withdraw(amount, description, TransactionType.WITHDRAWAL);
        }

        public synchronized void transfer(String fromAccount, String toAccount, double amount, String note)
                        throws MaxBalance, AccNotFound, MaxWithdraw, InvalidAmount {
                if (fromAccount.equalsIgnoreCase(toAccount)) {
                        throw new InvalidAmount("Cannot transfer to the same account");
                }
                if (amount <= 0) {
                        throw new InvalidAmount("Transfer amount must be greater than zero");
                }
                BankAccount source = requireAccount(fromAccount);
                BankAccount target = requireAccount(toAccount);

                String outgoingNote = String.format("Transfer to %s (%s)%s", target.getOwnerName(),
                                target.getAccountNumber(),
                                note == null || note.isEmpty() ? "" : " - " + note);
                String incomingNote = String.format("Transfer from %s (%s)%s", source.getOwnerName(),
                                source.getAccountNumber(),
                                note == null || note.isEmpty() ? "" : " - " + note);

                source.withdraw(amount, outgoingNote, TransactionType.TRANSFER_OUT);
                target.deposit(amount, incomingNote, TransactionType.TRANSFER_IN);
        }

        public synchronized DefaultListModel<String> display() {
                DefaultListModel<String> list = new DefaultListModel<String>();
                for (BankAccount account : accounts) {
                        list.addElement(String.format(Locale.US, "%s | %s | %s | Balance: %.2f", account.getOwnerName(),
                                        account.getAccountNumber(), account.getAccountType(), account.getbalance()));
                }
                return list;
        }

        public synchronized List<BankAccount> getAccountsSnapshot() {
                return Collections.unmodifiableList(new ArrayList<>(accounts));
        }

        public synchronized List<Transaction> getTransactions(String accountNumber) throws AccNotFound {
                return requireAccount(accountNumber).getTransactions();
        }

        public synchronized List<Transaction> getRecentTransactions(int limit) {
                return accounts.stream()
                                .flatMap(account -> account.getTransactions().stream())
                                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                                .limit(limit)
                                .collect(Collectors.toList());
        }

        public synchronized List<BankAccount> searchAccounts(String query) {
                if (query == null || query.isEmpty()) {
                        return getAccountsSnapshot();
                }
                final String normalized = query.toLowerCase(Locale.US);
                return accounts.stream()
                                .filter(account -> account.getOwnerName().toLowerCase(Locale.US).contains(normalized)
                                                || account.getAccountNumber().toLowerCase(Locale.US)
                                                                .contains(normalized))
                                .collect(Collectors.toList());
        }

        public synchronized double getTotalBalance() {
                return accounts.stream().mapToDouble(BankAccount::getbalance).sum();
        }

        public synchronized int getTotalCustomers() {
                return accounts.size();
        }

        public synchronized double getAverageBalance() {
                if (accounts.isEmpty()) {
                        return 0;
                }
                return getTotalBalance() / accounts.size();
        }

        public synchronized List<BankAccount> getTopAccountsByBalance(int limit) {
                return accounts.stream().sorted(Comparator.comparingDouble(BankAccount::getbalance).reversed())
                                .limit(limit).collect(Collectors.toList());
        }

        public synchronized long countAccountsByType(Class<? extends BankAccount> type) {
                return accounts.stream().filter(type::isInstance).count();
        }

        public synchronized BankAccount[] getAccounts() {
                return accounts.toArray(new BankAccount[0]);
        }

        public synchronized void setAccounts(List<BankAccount> accounts) {
                this.accounts.clear();
                this.accounts.addAll(accounts);
        }
}

