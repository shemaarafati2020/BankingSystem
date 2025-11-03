package GUI;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Bank.BankAccount;
import Data.FileIO;

public class DisplayList extends JFrame {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        static DefaultListModel<String> arr = new DefaultListModel<String>();
        private JPanel contentPane;
        private JTextField searchField;
        private JList<String> list;
        private JLabel totalBalanceLabel;
        private JLabel totalAccountsLabel;

        public DisplayList() {
                setTitle("Account Directory");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 720, 520);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblAccountList = new JLabel("Account Directory");
                lblAccountList.setFont(new Font("Tahoma", Font.BOLD, 18));
                lblAccountList.setHorizontalAlignment(SwingConstants.CENTER);
                lblAccountList.setBounds(10, 11, 684, 31);
                contentPane.add(lblAccountList);

                searchField = new JTextField();
                searchField.setBounds(20, 53, 300, 25);
                contentPane.add(searchField);
                searchField.setColumns(10);

                JButton btnSearch = new JButton("Search");
                btnSearch.setBounds(330, 53, 100, 25);
                btnSearch.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                populateList(FileIO.bank.searchAccounts(searchField.getText()));
                        }
                });
                contentPane.add(btnSearch);

                JButton btnReset = new JButton("Reset");
                btnReset.setBounds(440, 53, 100, 25);
                btnReset.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                searchField.setText(null);
                                populateList(FileIO.bank.getAccountsSnapshot());
                        }
                });
                contentPane.add(btnReset);

                JButton btnRefresh = new JButton("Refresh");
                btnRefresh.setBounds(550, 53, 100, 25);
                btnRefresh.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                populateList(FileIO.bank.getAccountsSnapshot());
                        }
                });
                contentPane.add(btnRefresh);

                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setBounds(20, 115, 674, 320);
                contentPane.add(scrollPane);

                arr = new DefaultListModel<>();
                list = new JList<>(arr);
                list.setFont(new Font("Monospaced", Font.PLAIN, 12));
                scrollPane.setViewportView(list);

                totalAccountsLabel = new JLabel();
                totalAccountsLabel.setBounds(20, 446, 250, 25);
                contentPane.add(totalAccountsLabel);

                totalBalanceLabel = new JLabel();
                totalBalanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                totalBalanceLabel.setBounds(394, 446, 300, 25);
                contentPane.add(totalBalanceLabel);

                populateList(FileIO.bank.getAccountsSnapshot());
        }

        private void populateList(List<BankAccount> accounts) {
                arr.clear();
                for (BankAccount account : accounts) {
                        arr.addElement(String.format("%-25s %-15s %-18s Balance: %10.2f", account.getOwnerName(),
                                        account.getAccountNumber(), account.getAccountType(), account.getbalance()));
                }

                if (list != null) {
                        list.setModel(arr);
                }
                totalAccountsLabel.setText("Accounts: " + FileIO.bank.getTotalCustomers());
                totalBalanceLabel.setText(String.format("Total balance: %.2f", FileIO.bank.getTotalBalance()));
        }
}

