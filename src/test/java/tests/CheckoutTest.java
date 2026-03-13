package tests;

import com.codeborne.selenide.Configuration;
import data.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductCatalogPage;
import pages.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class CheckoutTest {
    LoginPage loginPage;
    ProductCatalogPage homePage;
    CartPage cartPage;
    CheckoutPage checkOutpage;
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
        cartPage = new CartPage();
        checkOutpage = new CheckoutPage();

        loginPage.happyAuthorization(Row.ValidLogin, Row.ValidPassword);
    }

    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    //    Переход к checkout
    @Test
    void goToCheckoutPage() {
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        cartPage.goToCheckoutPage();
        checkOutpage.checkCheckOutPage();
    }


    //    Checkout с валидными данными
    @Test
    void happyPath(){
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        cartPage.goToCheckoutPage();
        checkOutpage.completionForm(Row.LastName,Row.FirsttName , Row.Code);
        checkOutpage.checkCheckOutOverviewTitle();
    }
//
//    Checkout без имени

    @Test
    void inHappeYestName(){
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        cartPage.goToCheckoutPage();
        checkOutpage.completionForm("",Row.LastName , Row.Code);
        checkOutpage.ErrorMassage("Error: First Name is required");
    }
//    Checkout без фамилии

    @Test
    void inHappeYestLastName(){
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        cartPage.goToCheckoutPage();
        checkOutpage.completionForm(Row.FirsttName,"" , Row.Code);
        checkOutpage.ErrorMassage("Error: Last Name is required");
    }
//    Checkout без ZIP

    @Test
    void inHappeYestLastZIP(){
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        cartPage.goToCheckoutPage();
        checkOutpage.completionForm(Row.FirsttName,Row.LastName ,"");
        checkOutpage.ErrorMassage("Error: Postal Code is required");
    }
//
//    Проверка итоговой суммы заказа
//
//    Завершение заказа
//
//    Проверка страницы Order confirmation

}
