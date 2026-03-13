package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductCatalogPage {
    private final SelenideElement cartIcon = $("[data-test='shopping-cart-link']");
    private final SelenideElement burgerButton = $("#react-burger-menu-btn");
    private final SelenideElement listOfProducts = $("[data-test='inventory-container']");
    private final SelenideElement badgeNumber = $("[data-test='shopping-cart-badge']");
    private final SelenideElement sortButton = $("[data-test='product-sort-container']");

    // Коллекции наименований продуктов
    private final ElementsCollection productNames = $$("[data-test='inventory-item-name']");
    private final ElementsCollection productPrices = $$("[data-test='inventory-item-price']");
    private final ElementsCollection productImages = $$("[data-test='inventory-item'] img");
    private final ElementsCollection inventoryItemContainer = $$("[data-test='inventory-item-name']");
    private final ElementsCollection addToCartButtons = $$("button.btn.btn_primary.btn_small.btn_inventory");

    @Step("Проверка видимости иконки корзины на главной странице")
    public void checkHomepage() {
        cartIcon.shouldBe(visible);
    }

    @Step("Переход в корзину")
    public CartPage goToCartPage() {
        cartIcon.click();
        return new CartPage();
    }

    @Step("Открытие бокового меню")
    public MenuPage openMenuPage() {
        burgerButton.click();
        return new MenuPage();
    }

    @Step("Проверка отображения списка товаров")
    public void checkListProduct() {
        listOfProducts.shouldBe(visible);
    }

    @Step("Проверка соответствия названий товаров ожидаемым")
    public void shouldHaveProductNames() {
        productNames.shouldHave(texts(
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Onesie",
                "Test.allTheThings() T-Shirt (Red)"
        ));
    }

    @Step("Проверка соответствия цен товаров ожидаемым")
    public void shouldHaveProductPrices() {
        List<String> expectedPrices = List.of("$29.99", "$9.99", "$15.99", "$49.99", "$7.99", "$15.99");
        productPrices.shouldHave(texts(expectedPrices));
    }

    @Step("Проверка наличия и корректности изображений товаров")
    public void shouldHaveProductImages() {
        for (SelenideElement image : productImages) {
            image
                    .shouldBe(visible)
                    .shouldHave(attribute("src"))
                    .shouldHave(attribute("alt"));
        }
    }

    @Step("Проверка кнопок 'Add to cart' на всех товарах")
    public void checkAddToCardButton() {
        addToCartButtons.forEach(button -> button
                .shouldBe(visible)
                .shouldHave(text("Add to cart")));
    }

    @Step("Добавление товара в корзину по индексу {itemNumber}")
    public void addItemToShoppingBag(int itemNumber) {
        addToCartButtons.get(itemNumber).click();
    }

    @Step("Проверка, что товар '{productName}' добавлен в корзину")
    public void shouldHaveInCartItem(String productName) {
        cartIcon.click();
        $(byText(productName))
                .shouldBe(visible);
    }

    @Step("Добавление нескольких товаров в корзину: индексы {firstItemNumber}, {secondItemNumber}")
    public void addItemsToShoppingBag(int firstItemNumber, int secondItemNumber) {
        addToCartButtons.get(firstItemNumber).click();
        addToCartButtons.get(secondItemNumber).click();
    }

    @Step("Проверка, что товары '{firstProductName}' и '{secondProductName}' добавлены в корзину")
    public void shouldHaveInCartItems(String firstProductName, String secondProductName) {
        cartIcon.click();
        $(byText(firstProductName)).shouldBe(visible);
        $(byText(secondProductName)).shouldBe(visible);
    }

    @Step("Проверка счётчика корзины: ожидается {count} товар(ов)")
    public void shouldHaveCartBadge(int count) {
        if (count == 0) {
            badgeNumber.shouldNot(exist);
        } else {
            badgeNumber
                    .shouldBe(visible)
                    .shouldHave(text(String.valueOf(count)));
        }
    }

    @Step("Переход на страницу товара по индексу {index}")
    public void openDetailPageProductByIndex(int index) {
        inventoryItemContainer.get(index).click();
    }

    @Step("Проверка открытия детальной страницы товара с названием '{productTitle}'")
    public void checkOpenDetailPage(String productTitle) {
        $(byText(productTitle)).shouldBe(visible);
    }

    @Step("Сортировка товаров по фильтру: {filter}")
    public void shouldSortByNameAZAndZA(String filter) {
        sortButton.selectOption(filter);
    }

    @Step("Сортировка товаров по цене: {filter}")
    public void shouldSortProductsByPrice(String filter) {
        sortButton.selectOption(filter);
    }

    @Step("Проверка порядка названий товаров")
    public void shouldHaveProductNamesInOrder(String... expectedNames) {
        productNames.shouldHave(texts(expectedNames));
    }

    @Step("Проверка порядка цен товаров")
    public void shouldHaveProductPricesInOrder(String... expectedPrices) {
        productPrices.shouldHave(texts(expectedPrices));
    }
}