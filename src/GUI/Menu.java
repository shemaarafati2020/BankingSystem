package GUI;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Data.FileIO;

public class Menu extends JFrame {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private JPanel contentPane;

        public Menu() {
                setTitle("Banking System");
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                setBounds(100, 100, 720, 520);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setForeground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblBankingSystem = new JLabel("Digital Banking Command Center");
                lblBankingSystem.setHorizontalAlignment(SwingConstants.CENTER);
                lblBankingSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
                lblBankingSystem.setBounds(20, 30, 664, 46);
                contentPane.add(lblBankingSystem);

                JLabel lblSubtitle = new JLabel("Manage accounts, cash flow and insights from a single place");
                lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
                lblSubtitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblSubtitle.setBounds(20, 75, 664, 25);
                contentPane.add(lblSubtitle);

                JButton btnAddAccount = new JButton("Add Account");
                btnAddAccount.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if (!GUIForm.addaccount.isVisible()) {
                                        GUIForm.addaccount.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }

                        }
                });
                btnAddAccount.setBounds(120, 140, 200, 40);
                contentPane.add(btnAddAccount);

                JButton btnDepositToAccount = new JButton("Deposit");
                btnDepositToAccount.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if (!GUIForm.depositacc.isVisible()) {
                                        GUIForm.depositacc.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }

                        }
                });
                btnDepositToAccount.setBounds(120, 200, 200, 40);
                contentPane.add(btnDepositToAccount);

                JButton btnWithdrawFromAccount = new JButton("Withdraw");
                btnWithdrawFromAccount.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if (!GUIForm.withdraw.isVisible()) {
                                        GUIForm.withdraw.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }

                        }

                });
                btnWithdrawFromAccount.setBounds(120, 260, 200, 40);
                contentPane.add(btnWithdrawFromAccount);

                JButton btnDisplayAccountList = new JButton("Browse Accounts");
                btnDisplayAccountList.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                                if (!GUIForm.displaylist.isVisible()) {
                                        GUIForm.displaylist.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }

                        }
                });
                btnDisplayAccountList.setBounds(120, 320, 200, 40);
                contentPane.add(btnDisplayAccountList);

                JButton btnTransfer = new JButton("Transfer Funds");
                btnTransfer.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if (!GUIForm.transferFunds.isVisible()) {
                                        GUIForm.transferFunds.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }
                        }
                });
                btnTransfer.setBounds(400, 140, 200, 40);
                contentPane.add(btnTransfer);

                JButton btnTransactions = new JButton("Transaction History");
                btnTransactions.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if (!GUIForm.transactionHistory.isVisible()) {
                                        GUIForm.transactionHistory.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }
                        }
                });
                btnTransactions.setBounds(400, 200, 200, 40);
                contentPane.add(btnTransactions);

                JButton btnInsights = new JButton("Insights Dashboard");
                btnInsights.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                if (!GUIForm.insightsDashboard.isVisible()) {
                                        GUIForm.insightsDashboard.setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                                }
                        }
                });
                btnInsights.setBounds(400, 260, 200, 40);
                contentPane.add(btnInsights);

                JButton btnExit = new JButton("Exit & Save");
                btnExit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                                JOptionPane.showMessageDialog(getComponent(0), "Thanks For Using");
                                FileIO.Write();
                                System.exit(0);
                        }
                });
                btnExit.setBounds(400, 320, 200, 40);
                contentPane.add(btnExit);
        }
}

