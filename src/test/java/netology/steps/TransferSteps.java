package netology.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import netology.data.DataHelper;
import netology.page.DashboardPage;
import netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {

    DashboardPage dashboardPage;

    @Дано("пользователь залогинен с именем {string} и паролем {string}")
    public void UserLogin(String username, String password) {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(username, password);
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode.getCode());
    }


    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void UserTransferBetweenCards(Integer amount, String cardNumber, Integer cardIndex) {
        var transferPage = dashboardPage.selectCardToTransfer(cardIndex - 1);
        transferPage.makeValidTransfer(amount.toString(), cardNumber);
    }


    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void UpdatedBalance(Integer cardIndex, Integer expectedBalance) {
        var actualBalance = dashboardPage.getCardBalance(cardIndex - 1);
        assertEquals(expectedBalance, actualBalance, "Баланс карты не соответствует ожидаемому");
    }
}
