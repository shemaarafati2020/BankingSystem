package Bank;

public class CurrentAccount extends BankAccount {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final String tradeLicenseNumber;

        public CurrentAccount(String name, double balance, String tradeLicenseNumber) {
                super(name, balance, 5000);
                this.tradeLicenseNumber = tradeLicenseNumber;
        }

        public String getTradeLicenseNumber() {
                return tradeLicenseNumber;
        }

        @Override
        public String toString() {
                return super.toString() + String.format(" | Trade License: %s", tradeLicenseNumber);
        }
}

