package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MenuPage {
    private final SelenideElement allItems = $("[data-test='inventory-sidebar-link']");
    private final  SelenideElement about = $("[data-test='about-sidebar-link']");
    private final  SelenideElement logOut = $("[data-test='logout-sidebar-link']");
    private final  SelenideElement resetAppState = $("[data-test='reset-sidebar-link']");






    public void shouldLogout() {
        logOut.click();
    }












    }