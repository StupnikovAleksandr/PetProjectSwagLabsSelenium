package tests;

import data.Row;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class LoginTest {

    LoginPage loginPage;
    ProductCatalogPage homePage;
    MenuPage menuPage;
    @BeforeEach
    void setup() {
        // Создаём карту настроек (prefs)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.address_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);

        // Создаём ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-infobars",
                "--disable-save-password-bubble",
                "--no-first-run",
                "--disable-web-security",
                "--disable-features=PasswordBounceDetection",
                "--start-maximized"
        );

        // Устанавливаем preferences через experimental options
        options.setExperimentalOption("prefs", prefs);

        // Применяем к Selenide
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.timeout = 15000;
        Configuration.pageLoadTimeout = 30000;
        open("https://www.saucedemo.com/");

        loginPage = new LoginPage();
        homePage = new ProductCatalogPage();
        menuPage=new MenuPage();


    }

    //Успешная авторизация №1
    @Test
    void happyAuthorizationTest(){
        loginPage.happyAuthorization(Row.ValidLogin , Row.ValidPassword);
        homePage.checkHomepage();
    }

    //Логин с неправильным паролем №2
    @Test
    void unhappyAutorizationWithWrongPass() {
        loginPage.unhappyAuthorization(Row.ValidLogin , Row.InValidPassword);
        loginPage.errorMessageIsVisible();
    }


    //Логин с несуществующим пользователем №3
    @Test
    void unhappyAutorizationWithWrongLog() {
        loginPage.unhappyAuthorization(Row.InValidLogin , Row.ValidPassword);
        loginPage.errorMessageIsVisible();
    }

    //Логин с пустым username №4
    @Test
    void unhappyAutorizationEmptyLog() {
        loginPage.unhappyAuthorization(Row.EmptyLogin , Row.ValidPassword);
        loginPage.emptyLoginErrorMessageIsVisible();
    }

    //Логин с пустым password №5
    @Test
    void unhappyAutorizationEpmtyPassword() {
        loginPage.unhappyAuthorization(Row.ValidLogin , Row.EmptyPassword);
        loginPage.emptyPasswordErrorMessageIsVisible();
    }


    //Логин с пустыми полями №6
    @Test
    void unhappyAutorizationEpmtyPasswordAdnLogin() {
        loginPage.unhappyAuthorization(Row.EmptyLogin , Row.EmptyPassword);
        loginPage.emptyLoginErrorMessageIsVisible();
    }

    //7 тескст покрыл


    //Проверка что поле password скрывает символы №8
    @Test
    void passwordFieldShouldHideCharacters() {

        loginPage.shouldHavePasswordType();
    }

    //Проверка logout из системы №10
    @Test
    void logOutTest(){
        loginPage.happyAuthorization(Row.ValidLogin , Row.ValidPassword);
        homePage.openMenuPage();
        menuPage.shouldLogout();
        loginPage.checkLoginPage();

    }


}