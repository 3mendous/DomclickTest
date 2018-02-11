package my.domclick.test.elements.Domclick;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import java.util.List;

public class ProductDropdown extends TypifiedElement {

    public ProductDropdown(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public void selectProduct(String productName) {
        this.click();
        List<WebElement> products = findElements(By.xpath("//div[contains(@class, 'item___AMYs item___ZmH5')]"));
        for (WebElement product : products) {
            if (product.getText().equals(productName)) {
                product.click();
                return;
            }
        }
    }
}
