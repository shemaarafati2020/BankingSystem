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

public class AddStudentAccount extends JFrame {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private JPanel contentPane;
        private JTextField nameField;
        private JTextField institutionField;
        private JTextField balanceField;

        public AddStudentAccount() {
                setTitle("Add Student Account");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 450, 300);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblAddCurrentAccount = new JLabel("Add Student Account");
                lblAddCurrentAccount.setFont(new Font("Tahoma", Font.BOLD, 16));
                lblAddCurrentAccount.setHorizontalAlignment(SwingConstants.CENTER);
                lblAddCurrentAccount.setBounds(10, 11, 414, 34);
                contentPane.add(lblAddCurrentAccount);

                JLabel lblName = new JLabel("Name:");
                lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblName.setBounds(10, 72, 124, 14);
                contentPane.add(lblName);

                nameField = new JTextField();
                nameField.setBounds(144, 69, 254, 20);
                contentPane.add(nameField);
                nameField.setColumns(10);

                JLabel lblInstitution = new JLabel("Institution:");
                lblInstitution.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblInstitution.setBounds(10, 118, 124, 14);
                contentPane.add(lblInstitution);

                institutionField = new JTextField();
                institutionField.setColumns(10);
                institutionField.setBounds(144, 115, 254, 20);
                contentPane.add(institutionField);

                JLabel lblBalance = new JLabel("Balance:");
                lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 11));
                lblBalance.setBounds(10, 163, 135, 14);
                contentPane.add(lblBalance);

                balanceField = new JTextField();
                balanceField.setColumns(10);
                balanceField.setBounds(144, 160, 254, 20);
                contentPane.add(balanceField);

                JButton btnAdd = new JButton("Add");
                btnAdd.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                try {
                                        String name = nameField.getText().trim();
                                        String institution = institutionField.getText().trim();
                                        double bal = Double.parseDouble(balanceField.getText().trim());

                                        if (name.isEmpty() || institution.isEmpty() || bal <= 0) {
                                                JOptionPane.showMessageDialog(getComponent(0),
                                                                "Please provide valid account details", "Warning", 0);
                                                return;
                                        }

                                        if (bal < 100) {
                                                JOptionPane.showMessageDialog(getComponent(0),
                                                                "Student accounts require at least 100 to open", "Warning",
                                                                0);
                                                return;
                                        }

                                        int ch = JOptionPane.showConfirmDialog(getComponent(0),
                                                        "Create student account for " + name + "?");
                                        if (ch == 0) {
                                                int index = FileIO.bank.addAccount(name, institution, bal, 100);
                                                JOptionPane.showMessageDialog(getComponent(0),
                                                                "Student account created successfully\nAccount: "
                                                                                + FileIO.bank.getAccounts()[index]
                                                                                                .getAccountNumber());
                                                FileIO.Write();
                                                GUIForm.UpdateDisplay();
                                                dispose();
                                        }
                                } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(getComponent(0),
                                                        "Please provide a numeric opening balance", "Warning", 0);
                                } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(getComponent(0),
                                                        "Unable to create account: " + ex.getMessage(), "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                } finally {
                                        clearFields();
                                }
                        }
                });
                btnAdd.setBounds(86, 209, 89, 23);
                contentPane.add(btnAdd);

                JButton btnReset = new JButton("Reset");
                btnReset.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                clearFields();
                        }
                });
                btnReset.setBounds(309, 209, 89, 23);
                contentPane.add(btnReset);
        }

        private void clearFields() {
                nameField.setText(null);
                institutionField.setText(null);
                balanceField.setText(null);
        }
}

