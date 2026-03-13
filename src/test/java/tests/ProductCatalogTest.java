package tests;

import com.codeborne.selenide.Configuration;
import data.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;


public class ProductCatalogTest {

    LoginPage loginPage;
    ProductCatalogPage homePage;


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


        loginPage.happyAuthorization(Row.ValidLogin , Row.ValidPassword);

    }
    @AfterEach
    void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }
    //Проверка отображения списка товаров №1
    @Test
    void shouldVisibleListOfProducts(){
        homePage.checkListProduct();
    }

    //Проверка названия товара №2
    @Test
    void shouldHaveCorrectProductNames() {
        homePage.shouldHaveProductNames();
    }

    //Проверка цены товара №3
    @Test
    void shouldHaveCorrectProductPrice() {
        homePage.shouldHaveProductPrices();
    }
    //Проверка изображения товара
    @Test
    void shouldHaveProductImages() {
        homePage.shouldHaveProductImages();
    }
    //Проверка кнопки Add to cart
    @Test
    void shouldHaveProductAddToCart() {
            homePage.checkAddToCardButton();
        }

    //Добавление одного товара в корзину
    @Test
    void shouldAddToCartItem() {
        homePage.addItemToShoppingBag(1);
        homePage.shouldHaveInCartItem("Sauce Labs Bike Light");
    }

    //Добавление нескольких товаров
    @Test
    void shouldAddToCartItems() {
        homePage.addItemsToShoppingBag(1,2);
        homePage.shouldHaveInCartItems("Sauce Labs Bike Light" , "Sauce Labs Fleece Jacket");
    }

    //Проверка счетчика корзины


    @Test
    void shouldShoppingCartBadge() {
        homePage.addItemsToShoppingBag(1,2);
        homePage.shouldHaveCartBadge(2);

    }

    //Проверка перехода на страницу товара

    @Test
    void shouldOpenDetailPageProduct() {
        homePage.openDetailPageProductByIndex(0);
        homePage.checkOpenDetailPage("Sauce Labs Backpack");
    }

    //Проверка сортировки товаров
    @Test
    void sortByNameAZ () {
        homePage.shouldSortByNameAZAndZA("Name (A to Z)");
        // Ожидаем что будет в таком порядке
        homePage.shouldHaveProductNamesInOrder(
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Onesie",
                "Test.allTheThings() T-Shirt (Red)"
        );
    }
    @Test
    void sortByNameZA() {
        homePage.shouldSortByNameAZAndZA("Name (Z to A)");
        // Ожидаем что будет в таком порядке
        homePage.shouldHaveProductNamesInOrder("Test.allTheThings() T-Shirt (Red)",
                "Sauce Labs Onesie",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Bike Light",
                "Sauce Labs Backpack");
    }

    @Test
    void sortByPriceLowToHigh() {
        homePage.shouldSortProductsByPrice("Price (low to high)");
        homePage.shouldHaveProductPricesInOrder(
                "$7.99", "$9.99", "$15.99",
                "$15.99", "$29.99", "$49.99");

    }
    @Test
    void sortByPriceHighToLow() {
        homePage.shouldSortProductsByPrice("Price (high to low)");
        homePage.shouldHaveProductPricesInOrder(
                "$49.99","$29.99","$15.99"
                ,"$15.99","$9.99","$7.99");

    }
    }

