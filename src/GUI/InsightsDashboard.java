package GUI;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Bank.BankAccount;
import Bank.CurrentAccount;
import Bank.SavingsAccount;
import Bank.StudentAccount;
import Bank.Transaction;
import Bank.TransactionType;
import Data.FileIO;

public class InsightsDashboard extends JFrame {

        private static final long serialVersionUID = 1L;
        private JPanel contentPane;
        private JLabel totalBalanceLabel;
        private JLabel totalCustomersLabel;
        private JLabel averageBalanceLabel;
        private JLabel savingsCountLabel;
        private JLabel currentCountLabel;
        private JLabel studentCountLabel;
        private DefaultTableModel topAccountsModel;
        private DefaultTableModel recentTransactionsModel;

        public InsightsDashboard() {
                setTitle("Bank Insights");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 820, 600);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel title = new JLabel("Insights & Analytics");
                title.setHorizontalAlignment(SwingConstants.CENTER);
                title.setFont(new Font("Tahoma", Font.BOLD, 22));
                title.setBounds(10, 11, 784, 30);
                contentPane.add(title);

                totalBalanceLabel = new JLabel();
                totalBalanceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
                totalBalanceLabel.setBounds(20, 60, 250, 25);
                contentPane.add(totalBalanceLabel);

                totalCustomersLabel = new JLabel();
                totalCustomersLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
                totalCustomersLabel.setBounds(290, 60, 200, 25);
                contentPane.add(totalCustomersLabel);

                averageBalanceLabel = new JLabel();
                averageBalanceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
                averageBalanceLabel.setBounds(520, 60, 250, 25);
                contentPane.add(averageBalanceLabel);

                savingsCountLabel = new JLabel();
                savingsCountLabel.setBounds(20, 95, 220, 20);
                contentPane.add(savingsCountLabel);

                currentCountLabel = new JLabel();
                currentCountLabel.setBounds(290, 95, 220, 20);
                contentPane.add(currentCountLabel);

                studentCountLabel = new JLabel();
                studentCountLabel.setBounds(520, 95, 220, 20);
                contentPane.add(studentCountLabel);

                JLabel topAccountsLabel = new JLabel("Top Accounts");
                topAccountsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
                topAccountsLabel.setBounds(20, 130, 200, 20);
                contentPane.add(topAccountsLabel);

                topAccountsModel = new DefaultTableModel(new Object[] { "Owner", "Account", "Type", "Balance" }, 0) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };
                JTable topAccountsTable = new JTable(topAccountsModel);
                topAccountsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
                JScrollPane topAccountsScroll = new JScrollPane(topAccountsTable);
                topAccountsScroll.setBounds(20, 160, 360, 220);
                contentPane.add(topAccountsScroll);

                JLabel recentTransactionsLabel = new JLabel("Recent Transactions");
                recentTransactionsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
                recentTransactionsLabel.setBounds(410, 130, 200, 20);
                contentPane.add(recentTransactionsLabel);

                recentTransactionsModel = new DefaultTableModel(
                                new Object[] { "Date", "Account", "Type", "Amount" }, 0) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };
                JTable recentTransactionsTable = new JTable(recentTransactionsModel);
                recentTransactionsTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
                JScrollPane recentTransactionsScroll = new JScrollPane(recentTransactionsTable);
                recentTransactionsScroll.setBounds(410, 160, 384, 220);
                contentPane.add(recentTransactionsScroll);

                JButton refreshButton = new JButton("Refresh Data");
                refreshButton.setBounds(20, 410, 150, 30);
                refreshButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                refreshData();
                        }
                });
                contentPane.add(refreshButton);

                refreshData();
        }

        private void refreshData() {
                totalBalanceLabel.setText(String.format("Total Balance: %.2f", FileIO.bank.getTotalBalance()));
                totalCustomersLabel.setText("Customers: " + FileIO.bank.getTotalCustomers());
                averageBalanceLabel.setText(String.format("Average Balance: %.2f", FileIO.bank.getAverageBalance()));
                savingsCountLabel.setText("Savings Accounts: " + FileIO.bank.countAccountsByType(SavingsAccount.class));
                currentCountLabel.setText("Current Accounts: " + FileIO.bank.countAccountsByType(CurrentAccount.class));
                studentCountLabel.setText("Student Accounts: " + FileIO.bank.countAccountsByType(StudentAccount.class));

                List<BankAccount> topAccounts = FileIO.bank.getTopAccountsByBalance(5);
                topAccountsModel.setRowCount(0);
                for (BankAccount account : topAccounts) {
                        topAccountsModel.addRow(new Object[] { account.getOwnerName(), account.getAccountNumber(),
                                        account.getAccountType(), String.format("%.2f", account.getbalance()) });
                }

                List<Transaction> recentTransactions = FileIO.bank.getRecentTransactions(10);
                recentTransactionsModel.setRowCount(0);
                for (Transaction transaction : recentTransactions) {
                        recentTransactionsModel.addRow(new Object[] { transaction.getFormattedTimestamp(),
                                        findAccountNumber(transaction), formatType(transaction.getType()),
                                        String.format("%.2f", transaction.getAmount()) });
                }
        }

        private String findAccountNumber(Transaction transaction) {
                for (BankAccount account : FileIO.bank.getAccountsSnapshot()) {
                        if (account.getTransactions().contains(transaction)) {
                                return account.getAccountNumber();
                        }
                }
                return "-";
        }

        private String formatType(TransactionType type) {
                String raw = type.name().toLowerCase().replace('_', ' ');
                return Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
        }

        public void refreshDataView() {
                refreshData();
        }
}

