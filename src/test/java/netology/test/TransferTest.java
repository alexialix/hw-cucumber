package netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import netology.page.DashboardPage;
import netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        dashboardPage = loginPage.validLogin("vasya", "qwerty123")
                .validVerify("12345");
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var firstCardNumber = "5559 0000 0000 0001";
        var secondCardNumber = "5559 0000 0000 0002";

        var firstCardBalance = dashboardPage.getCardBalance(0); // Индекс карты
        var secondCardBalance = dashboardPage.getCardBalance(1);

        var amount = generateValidAmount(firstCardBalance);

        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;

        var transferPage = dashboardPage.selectCardToTransfer(1);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardNumber);

        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);

        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfAmountMoreThanAvailable() {
        var firstCardNumber = "5559 0000 0000 0001";
        var secondCardNumber = "5559 0000 0000 0002";

        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);

        var amount = generateInvalidAmount(firstCardBalance);

        var transferPage = dashboardPage.selectCardToTransfer(0);

        transferPage.makeTransfer(String.valueOf(amount), secondCardNumber);
        transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");

        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }

    private int generateValidAmount(int balance) {
        return balance > 1000 ? 1000 : balance / 2;
    }

    private int generateInvalidAmount(int balance) {
        return balance + 1000;
    }
}
