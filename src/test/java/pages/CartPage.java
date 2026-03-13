package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
public class CartPage {
    private final SelenideElement title = $("[data-test='title']");
    private final SelenideElement continueShoppingButton = $("[data-test='continue-shopping']");
    private final SelenideElement checkoutButton = $("[data-test='checkout']");

    private final  ElementsCollection removeButton = $$(".btn.btn_secondary.btn_small.cart_button");

    @Step("Переход к странице оформления заказа")
    public CheckoutPage goToCheckoutPage() {
        checkoutButton.click();
        return new CheckoutPage();
    }

    @Step("Проверка отображения заголовка страницы корзины")
    public void checkTitleCartPage() {
        title.shouldBe(visible);
    }

    @Step("Удаление товара по индексу {index}")
    public void deleteItemBtIndex(int index) {
        if (index == 0) {
        } else {
            removeButton.get(index).click();
        }
    }

    @Step("Удаление нескольких товаров по индексам")
    public void deleteItemsBtIndex(int... indices) {
        if (indices.length == 0) {
            removeButton.shouldBe(size(0));
        } else {
            for (int index : indices) {
                removeButton.get(index).click();
            }
        }
    }

    @Step("Проверка отсутствия товаров в корзине")
    public void checkDoNotItems() {
        removeButton.shouldBe(size(0));
    }

    @Step("Проверка видимости и активности кнопки 'Continue Shopping'")
    public void checkContinueShoppingButton() {
        continueShoppingButton.shouldBe(visible).shouldBe(enabled);
    }

    @Step("Возврат на страницу каталога товаров")
    public void backProductCatalogPage() {
        continueShoppingButton.click();
    }
}