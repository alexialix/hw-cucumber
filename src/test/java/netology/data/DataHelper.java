package netology.data;

public class DataHelper {

    public static class AuthInfo {
        private String login;
        private String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class VerificationCode {
        private String code;

        public VerificationCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static class CardInfo {
        private String cardNumber;

        public CardInfo(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardNumber() {
            return cardNumber;
        }
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static CardInfo getCardInfo(int index) {
        if (index == 0) {
            return new CardInfo("5559 0000 0000 0001");
        } else if (index == 1) {
            return new CardInfo("5559 0000 0000 0002");
        } else {
            throw new IllegalArgumentException("Некорректный индекс карты");
        }
    }
}
