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

public class TransferFunds extends JFrame {

        private static final long serialVersionUID = 1L;
        private JPanel contentPane;
        private JTextField fromField;
        private JTextField toField;
        private JTextField amountField;
        private JTextField noteField;

        public TransferFunds() {
                setTitle("Transfer Funds");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 520, 360);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel title = new JLabel("Transfer Funds Between Accounts");
                title.setHorizontalAlignment(SwingConstants.CENTER);
                title.setFont(new Font("Tahoma", Font.BOLD, 18));
                title.setBounds(10, 11, 484, 36);
                contentPane.add(title);

                JLabel fromLabel = new JLabel("From Account:");
                fromLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                fromLabel.setBounds(20, 78, 126, 20);
                contentPane.add(fromLabel);

                fromField = new JTextField();
                fromField.setBounds(156, 78, 268, 20);
                contentPane.add(fromField);

                JLabel toLabel = new JLabel("To Account:");
                toLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                toLabel.setBounds(20, 118, 126, 20);
                contentPane.add(toLabel);

                toField = new JTextField();
                toField.setBounds(156, 118, 268, 20);
                contentPane.add(toField);

                JLabel amountLabel = new JLabel("Amount:");
                amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                amountLabel.setBounds(20, 158, 126, 20);
                contentPane.add(amountLabel);

                amountField = new JTextField();
                amountField.setBounds(156, 158, 268, 20);
                contentPane.add(amountField);

                JLabel noteLabel = new JLabel("Note:");
                noteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                noteLabel.setBounds(20, 198, 126, 20);
                contentPane.add(noteLabel);

                noteField = new JTextField();
                noteField.setBounds(156, 198, 268, 20);
                contentPane.add(noteField);

                JButton btnTransfer = new JButton("Transfer");
                btnTransfer.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        String from = fromField.getText().trim();
                                        String to = toField.getText().trim();
                                        double amount = Double.parseDouble(amountField.getText().trim());
                                        String note = noteField.getText().trim();

                                        if (from.isEmpty() || to.isEmpty()) {
                                                JOptionPane.showMessageDialog(getComponent(0),
                                                                "Please provide both source and destination accounts");
                                                return;
                                        }

                                        int confirm = JOptionPane.showConfirmDialog(getComponent(0),
                                                        String.format("Transfer %.2f from %s to %s?", amount, from, to));
                                        if (confirm != 0) {
                                                return;
                                        }

                                        FileIO.bank.transfer(from, to, amount, note);
                                        FileIO.Write();
                                        GUIForm.UpdateDisplay();

                                        JOptionPane.showMessageDialog(getComponent(0), "Transfer successful");
                                        dispose();
                                } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(getComponent(0),
                                                        "Please enter a valid numeric amount");
                                } catch (InvalidAmount | MaxBalance | MaxWithdraw ex) {
                                        JOptionPane.showMessageDialog(getComponent(0), ex.getMessage());
                                } catch (AccNotFound ex) {
                                        JOptionPane.showMessageDialog(getComponent(0), "One or more accounts not found");
                                } finally {
                                        clearFields();
                                }
                        }
                });
                btnTransfer.setBounds(110, 252, 120, 30);
                contentPane.add(btnTransfer);

                JButton btnReset = new JButton("Reset");
                btnReset.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                clearFields();
                        }
                });
                btnReset.setBounds(280, 252, 120, 30);
                contentPane.add(btnReset);
        }

        private void clearFields() {
                fromField.setText(null);
                toField.setText(null);
                amountField.setText(null);
                noteField.setText(null);
        }
}

