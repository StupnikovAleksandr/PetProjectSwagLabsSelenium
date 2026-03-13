package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test='username']");
    private final SelenideElement passwordInput = $("[data-test='password']");
    private final SelenideElement loginButton = $("[data-test='login-button']");
    private final SelenideElement errorMessage = $("[data-test='error']");

    public static final String ErrorInformation = "Epic sadface: Username and password do not match any user in this service";
    public static final String EmptyLoginErrorInformation = "Epic sadface: Username is required";
    public static final String EmptyPasswordErrorInformation = "Epic sadface: Password is required";

    @Step("Проверка видимости полей ввода логина и пароля на странице авторизации")
    public void checkLoginPage() {
        loginInput.shouldBe(visible);
        passwordInput.shouldBe(visible);
    }

    @Step("Успешная авторизация с логином '{login}' и паролем '{password}'")
    public ProductCatalogPage happyAuthorization(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
        return new ProductCatalogPage();
    }

    @Step("Неудачная попытка авторизации с логином '{login}' и паролем '{password}'")
    public void unhappyAuthorization(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
    }

    @Step("Ввод логина '{login}' и пароля '{password}' без нажатия кнопки входа")
    public void enterLoginAdnPassword(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
    }

    @Step("Проверка отображения ошибки: 'Username and password do not match'")
    public void errorMessageIsVisible() {
        errorMessage.shouldBe(visible).shouldHave(text(ErrorInformation));
    }

    @Step("Проверка отображения ошибки: 'Username is required'")
    public void emptyLoginErrorMessageIsVisible() {
        errorMessage.shouldBe(visible).shouldHave(text(EmptyLoginErrorInformation));
    }

    @Step("Проверка отображения ошибки: 'Password is required'")
    public void emptyPasswordErrorMessageIsVisible() {
        errorMessage.shouldBe(visible).shouldHave(text(EmptyPasswordErrorInformation));
    }

    @Step("Проверка, что поле ввода пароля скрыто (тип 'password')")
    public void shouldHavePasswordType() {
        passwordInput.shouldHave(attribute("type", "password"));
    }

}
