package GUI;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Bank.BankAccount;
import Bank.Transaction;
import Bank.TransactionType;
import Data.FileIO;
import Exceptions.AccNotFound;

public class TransactionHistory extends JFrame {

        private static final long serialVersionUID = 1L;
        private JPanel contentPane;
        private JTextField accountField;
        private JTable table;
        private DefaultTableModel tableModel;
        private JLabel balanceLabel;
        private JComboBox<String> typeFilter;

        public TransactionHistory() {
                setTitle("Transaction History");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 720, 520);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel title = new JLabel("Transaction History");
                title.setHorizontalAlignment(SwingConstants.CENTER);
                title.setFont(new Font("Tahoma", Font.BOLD, 20));
                title.setBounds(10, 11, 684, 31);
                contentPane.add(title);

                JLabel accountLabel = new JLabel("Account Number:");
                accountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                accountLabel.setBounds(20, 60, 120, 25);
                contentPane.add(accountLabel);

                accountField = new JTextField();
                accountField.setBounds(150, 60, 200, 25);
                contentPane.add(accountField);

                typeFilter = new JComboBox<>(new String[] { "All", "Deposit", "Withdrawal", "Transfer In",
                                "Transfer Out", "Interest", "Fee" });
                typeFilter.setBounds(360, 60, 150, 25);
                contentPane.add(typeFilter);

                JButton btnLoad = new JButton("Load History");
                btnLoad.setBounds(520, 60, 140, 25);
                btnLoad.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                loadHistory();
                        }
                });
                contentPane.add(btnLoad);

                tableModel = new DefaultTableModel(new Object[] { "Date", "Type", "Amount", "Balance", "Note" }, 0) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                                return false;
                        }
                };
                table = new JTable(tableModel);
                table.setFont(new Font("Tahoma", Font.PLAIN, 12));
                table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBounds(20, 110, 664, 330);
                contentPane.add(scrollPane);

                balanceLabel = new JLabel("Balance: ");
                balanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                balanceLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
                balanceLabel.setBounds(20, 452, 664, 25);
                contentPane.add(balanceLabel);
        }

        private void loadHistory() {
                try {
                        String accountNumber = accountField.getText().trim();
                        if (accountNumber.isEmpty()) {
                                JOptionPane.showMessageDialog(getComponent(0),
                                                "Please enter an account number to load history");
                                return;
                        }

                        BankAccount account = FileIO.bank.findAccount(accountNumber);
                        if (account == null) {
                                throw new AccNotFound("Account not found");
                        }

                        List<Transaction> transactions = account.getTransactions();
                        String filter = (String) typeFilter.getSelectedItem();
                        if (!"All".equals(filter)) {
                                TransactionType type = mapFilterToType(filter);
                                transactions = transactions.stream().filter(t -> t.getType() == type)
                                                .collect(Collectors.toList());
                        }

                        tableModel.setRowCount(0);
                        for (Transaction transaction : transactions) {
                                tableModel.addRow(new Object[] { transaction.getFormattedTimestamp(),
                                                formatType(transaction.getType()),
                                                String.format("%.2f", transaction.getAmount()),
                                                String.format("%.2f", transaction.getBalanceAfter()),
                                                transaction.getDescription() });
                        }

                        balanceLabel.setText(String.format("Balance: %.2f", account.getbalance()));
                } catch (AccNotFound ex) {
                        JOptionPane.showMessageDialog(getComponent(0), ex.getMessage());
                }
        }

        private TransactionType mapFilterToType(String filter) {
                        switch (filter) {
                        case "Deposit":
                                return TransactionType.DEPOSIT;
                        case "Withdrawal":
                                return TransactionType.WITHDRAWAL;
                        case "Transfer In":
                                return TransactionType.TRANSFER_IN;
                        case "Transfer Out":
                                return TransactionType.TRANSFER_OUT;
                        case "Interest":
                                return TransactionType.INTEREST;
                        case "Fee":
                                return TransactionType.FEE;
                        default:
                                return TransactionType.DEPOSIT;
                        }
        }

        private String formatType(TransactionType type) {
                String raw = type.name().toLowerCase().replace('_', ' ');
                return Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
        }
}

