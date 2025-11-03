package Bank;

import Exceptions.MaxBalance;
import Exceptions.MaxWithdraw;

public class SavingsAccount extends BankAccount {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private double rate = .05f;
        private double maxWithLimit;

        public SavingsAccount(String name, double balance, double maxWithLimit) {
                super(name, balance, 2000);
                this.maxWithLimit = maxWithLimit;
        }

        public double getNetBalance() {
                return getbalance() + (getbalance() * rate);
        }

        public double getInterestRate() {
                return rate;
        }

        public void setInterestRate(double rate) {
                if (rate <= 0) {
                        throw new IllegalArgumentException("Rate must be positive");
                }
                this.rate = rate;
        }

        public double previewMonthlyInterest() {
                return getbalance() * (rate / 12.0);
        }

        public double applyMonthlyInterest() {
                double interest = previewMonthlyInterest();
                balance += interest;
                recordTransaction(TransactionType.INTEREST, interest, "Monthly interest credit");
                return interest;
        }

        public void withdraw(double amount) throws MaxWithdraw, MaxBalance {
                if (amount <= maxWithLimit) {
                        super.withdraw(amount);

                } else {
                        throw new MaxWithdraw("Maximum Withdraw Limit Exceed");
                }

        }

        public double getMaxWithLimit() {
                return maxWithLimit;
        }

        public void setMaxWithLimit(double maxWithLimit) {
                this.maxWithLimit = maxWithLimit;
        }

}

