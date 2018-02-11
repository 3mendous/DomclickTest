package my.domclick.test.pages.Domclick;

import my.domclick.test.elements.Domclick.ProductDropdown;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.allurehelper.ParamsHelper;
import ru.sbtqa.tag.pagefactory.Page;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.sbtqa.tag.pagefactory.extensions.DriverExtension;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@PageEntry(title = "Ипотека")
public class HypothecPage extends Page {

    private String monthlyPaymentValue;

    @ElementTitle("Ежемесячный платеж")
    @FindBy(id = "monthlyPayment")
    public WebElement monthlyPayment;

    @FindBy(id = "productButton")
    public ProductDropdown product;

    @ElementTitle("Стоимость недвижимости")
    @FindBy(id = "estateCost")
    public TextInput estateCost;

    @ElementTitle("Первоначальный взнос")
    @FindBy(id = "initialFee")
    public TextInput initialFee;

    @ElementTitle("Срок кредита")
    @FindBy(id = "creditTerm")
    public TextInput creditTerm;

    @ElementTitle("Страхование жизни")
    @FindBy(id = "lifeInsurance")
    public CheckBox lifeInsurance;

    @ElementTitle("Молодая семья")
    @FindBy(id = "youngFamilyDiscount")
    public TextInput developerDiscount;

    public HypothecPage() {
        PageFactory.initElements(
                new HtmlElementDecorator(new HtmlElementLocatorFactory(PageFactory.getDriver())), this);
    }

    @ActionTitle("запоминает размер ежемесячного платежа")
    public void saveMonthlyPaymentValue() {
        waitForElement(monthlyPayment);
        monthlyPaymentValue = monthlyPayment.getText();
        ParamsHelper.addParam("'Сохраненное значение ежемесячного платежа - %s'", new String[]{monthlyPaymentValue});

    }

    @ActionTitle("выбирает 'Цель кредита'")
    public void chooseProduct(String productName) throws PageException {
        product.selectProduct(productName);
        ParamsHelper.addParam("'Цель кредита - %s'", new String[]{productName});
    }

    @ActionTitle("заполняет поле")
    public void fillFieldWithScroll(String elementTitle, String text) throws PageException {
        WebElement webElement = getElementByTitle(elementTitle);
        executor("arguments[0].focus();", webElement);
        while (webElement.getAttribute("value").matches("\\d.+"))
            webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(text);
        ParamsHelper.addParam("'Поле %s заполнено значением %s'", new String[]{elementTitle, text});
    }

    @ActionTitle("убеждается, что размер ежемесячного платежа изменился")
    public void compareMonthlyPaymentValues() {
        waitForElement(monthlyPayment);
        String newMonthlyPaymentValue = monthlyPayment.getText();
        Assert.assertFalse("Ежемесячный платеж не изменился", monthlyPaymentValue.equals(newMonthlyPaymentValue));
        ParamsHelper.addParam("'Ежемесячный платеж изменился." +
                "Сохраненное значение - %s, новое значение - %s'", new String[]{monthlyPaymentValue, newMonthlyPaymentValue});
    }

    @ActionTitle("включает переключатель")
    public void switchToggleButtonOn(String toggleButtonName) throws PageException {
        switchToggleButton(toggleButtonName, false);
    }

    @ActionTitle("выключает переключатель")
    public void switchToggleButtonOff(String toggleButtonName) throws PageException {
        switchToggleButton(toggleButtonName, true);
    }

    private void switchToggleButton(String toggleButtonName, Boolean toggleButtonState) throws PageException {
        WebElement webElement = getElementByTitle(toggleButtonName);
        if ((toggleButtonState & webElement.isSelected()) || (!toggleButtonState & !webElement.isSelected()))
            waitForElement(webElement);
            executor("arguments[0].click();", webElement);
    }

    private Object executor(String script, WebElement webElement){
        return ((JavascriptExecutor) PageFactory.getDriver()).executeScript(script, webElement);
    }

    private void waitForElement(WebElement webElement){
        DriverExtension.waitForElementGetEnabled(webElement, 10);
    }
}