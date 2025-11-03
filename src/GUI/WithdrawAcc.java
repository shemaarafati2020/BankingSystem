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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Data.FileIO;
import Exceptions.AccNotFound;
import Exceptions.InvalidAmount;
import Exceptions.MaxBalance;
import Exceptions.MaxWithdraw;

public class WithdrawAcc extends JFrame {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private JPanel contentPane;
        private JTextField accountField;
        private JTextField amountField;
        private JTextField noteField;

        public WithdrawAcc() {
                setTitle("Withdraw From Account");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 480, 320);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblWithdraw = new JLabel("Withdraw From Account");
                lblWithdraw.setFont(new Font("Tahoma", Font.BOLD, 16));
                lblWithdraw.setHorizontalAlignment(SwingConstants.CENTER);
                lblWithdraw.setBounds(10, 11, 444, 36);
                contentPane.add(lblWithdraw);

                JLabel lblName = new JLabel("Account Number:");
                lblName.setHorizontalAlignment(SwingConstants.RIGHT);
                lblName.setBounds(10, 86, 126, 14);
                contentPane.add(lblName);

                accountField = new JTextField();
                accountField.setBounds(146, 83, 258, 20);
                contentPane.add(accountField);
                accountField.setColumns(10);

                JLabel lblAmount = new JLabel("Amount:");
                lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
                lblAmount.setBounds(10, 130, 126, 14);
                contentPane.add(lblAmount);

                amountField = new JTextField();
                amountField.setColumns(10);
                amountField.setBounds(146, 127, 258, 20);
                contentPane.add(amountField);

                JLabel lblNote = new JLabel("Description:");
                lblNote.setHorizontalAlignment(SwingConstants.RIGHT);
                lblNote.setBounds(10, 174, 126, 14);
                contentPane.add(lblNote);

                noteField = new JTextField();
                noteField.setColumns(10);
                noteField.setBounds(146, 171, 258, 20);
                contentPane.add(noteField);

                JButton btnWithdraw = new JButton("Withdraw");
                btnWithdraw.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        String accountNumber = accountField.getText().trim();
                                        double amount = Double.parseDouble(amountField.getText().trim());
                                        String note = noteField.getText().trim();

                                        if (accountNumber.isEmpty()) {
                                                JOptionPane.showMessageDialog(getComponent(0),
                                                                "Please enter an account number");
                                                return;
                                        }

                                        int confirm = JOptionPane.showConfirmDialog(getComponent(0),
                                                        "Withdraw " + amount + " from account " + accountNumber + "?");
                                        if (confirm != 0) {
                                                return;
                                        }

                                        FileIO.bank.withdraw(accountNumber, amount,
                                                        note.isEmpty() ? "Withdrawal" : note);
                                        FileIO.Write();
                                        GUIForm.UpdateDisplay();

                                        JOptionPane.showMessageDialog(getComponent(0), "Withdrawal Successful\nNew Balance: "
                                                        + String.format("%.2f",
                                                                        FileIO.bank.findAccount(accountNumber)
                                                                                        .getbalance()));
                                        dispose();
                                } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(getComponent(0),
                                                        "Please enter a valid numeric amount");
                                } catch (InvalidAmount ex) {
                                        JOptionPane.showMessageDialog(getComponent(0), ex.getMessage());
                                } catch (AccNotFound ex) {
                                        JOptionPane.showMessageDialog(getComponent(0), "Account not found");
                                } catch (MaxBalance ex) {
                                        JOptionPane.showMessageDialog(getComponent(0), ex.getMessage());
                                } catch (MaxWithdraw ex) {
                                        JOptionPane.showMessageDialog(getComponent(0), ex.getMessage());
                                } finally {
                                        clearFields();
                                }
                        }
                });
                btnWithdraw.setBounds(86, 227, 110, 25);
                contentPane.add(btnWithdraw);

                JButton btnReset = new JButton("Reset");
                btnReset.setBounds(266, 227, 110, 25);
                btnReset.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                clearFields();
                        }
                });
                contentPane.add(btnReset);
        }

        private void clearFields() {
                accountField.setText(null);
                amountField.setText(null);
                noteField.setText(null);
        }
}

