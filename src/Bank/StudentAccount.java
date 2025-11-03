package Bank;

public class StudentAccount extends SavingsAccount {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final String institutionName;

        public StudentAccount(String name, double balance, String institutionName) {
                super(name, balance, 20000);
                setMinBalance(100);
                this.institutionName = institutionName;
        }

        public String getInstitutionName() {
                return institutionName;
        }

        @Override
        public String toString() {
                return super.toString() + String.format(" | Institution: %s", institutionName);
        }
}

