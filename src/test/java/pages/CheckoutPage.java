package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutPage {

    private final SelenideElement title = $("[data-test='title']");
    private final  SelenideElement firstName = $("[data-test='firstName']");
    private final SelenideElement lastName = $("[data-test='lastName']");
    private final SelenideElement postalCode = $("[data-test='postalCode']");
    private final  SelenideElement checkCheckOutOverviewTitle = $("[data-test='title']");
    private final  SelenideElement cantinueButton = $("[data-test='continue']");
    private final  SelenideElement errorMessage = $("[data-test='error']");

    @Step("Проверка сообщения об ошибке: {message}")
    public void ErrorMassage(String message) {
        errorMessage.shouldBe(visible).shouldHave(text(message));
    }

    @Step("Проверка открытия страницы ввода данных: 'Checkout: Your Information'")
    public void checkCheckOutPage() {
        title.shouldBe(visible).shouldBe(text("Checkout: Your Information"));
    }

    @Step("Проверка перехода на страницу подтверждения: 'Checkout: Overview'")
    public void checkCheckOutOverviewTitle() {
        checkCheckOutOverviewTitle.shouldBe(visible).shouldBe(text("Checkout: Overview"));
    }

    @Step("Заполнение формы оформления заказа: имя={name}, фамилия={surname}, почтовый индекс={code}")
    public void completionForm(String name, String surname, String code) {
        firstName.setValue(name);
        lastName.setValue(surname);
        postalCode.setValue(code);
        cantinueButton.click();
    }
}