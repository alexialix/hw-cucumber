import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {

    DashboardPage dashboardPage;

    @Дано("пользователь залогинен с именем {string} и паролем {string}")
    public void пользовательЗалогиненСИменемИПаролем(String username, String password) {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new AuthInfo(username, password);
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = new VerificationCode("12345"); // Используйте актуальный код
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void пользовательПереводитРублейСКартыСНомеромНаСвоюКартуСГлавнойСтраницы(Integer amount, String cardNumber, Integer cardIndex) {
        var cardInfo = new CardInfo(cardNumber);
        var transferPage = dashboardPage.selectCardToTransfer(cardInfo);
        var targetCard = dashboardPage.getCardInfo(cardIndex - 1); // Индекс карты: 1 -> 0
        transferPage.makeValidTransfer(amount.toString(), cardInfo.getCardNumber());
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void балансЕгоКартыИзСпискаНаГлавнойСтраницеДолженСтатьРублей(Integer cardIndex, Integer expectedBalance) {
        var actualBalance = dashboardPage.getCardBalance(cardIndex - 1); // Индекс карты: 1 -> 0
        assertEquals(expectedBalance, actualBalance, "Баланс карты не соответствует ожидаемому");
    }
}
