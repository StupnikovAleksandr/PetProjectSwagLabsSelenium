package tests;

import com.codeborne.selenide.Configuration;
import data.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.ProductCatalogPage;
import pages.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class CartTests {
    LoginPage loginPage;
    ProductCatalogPage homePage;
    CartPage cartPage;

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
            cartPage =new CartPage();

            loginPage.happyAuthorization(Row.ValidLogin , Row.ValidPassword);


        }
    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    //Проверка отображения корзины
        @Test
        void checkTitleCart() {
            homePage.goToCartPage();
            cartPage.checkTitleCartPage();
        }


     //Проверка товара в корзине
     @Test
     void checkItemToCartByTitle() {
         homePage.addItemToShoppingBag(0);
         homePage.shouldHaveInCartItem("Sauce Labs Backpack");
     }


    //Удаление товара из корзины

    @Test
    void deleteItemToCart() {
        homePage.addItemToShoppingBag(0);
        homePage.goToCartPage();
        cartPage.deleteItemBtIndex(0);

    }

    //Удаление нескольких товаров
    @Test
    void shouldRemoveFirstAndSecondItems(){
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        //нужно удалять по убыванию, чтобы индкесы не менялись
        cartPage.deleteItemsBtIndex(1,0);
    }

    //Проверка что корзина пустая после удаления
    @Test
    void shouldEmptyCardForDelete(){
        homePage.addItemsToShoppingBag(0,1);
        homePage.goToCartPage();
        cartPage.deleteItemsBtIndex(1,0);
        cartPage.checkDoNotItems();
        }


//        Проверка кнопки Continue shopping
        @Test
        void checkContinueShoppingButton() {
            homePage.goToCartPage();
            cartPage.checkContinueShoppingButton();
        }
//        Проверка перехода обратно в каталог
        @Test
        void backHomePage() {
            homePage.goToCartPage();
            cartPage.backProductCatalogPage();
            homePage.checkHomepage();
        }
//        Проверка количества товаров в корзине

//    @Test
//    void shouldRemoveFirstAndSecondItems(){
//        homePage.addItemsToShoppingBag(0,1);
//        homePage.goToCartPage();
//        //нужно удалять по убыванию, чтобы индкесы не менялись
//        cartPage.deleteItemsBtIndex(1,0);
//    }
    }

