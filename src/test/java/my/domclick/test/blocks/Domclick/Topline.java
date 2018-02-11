package my.domclick.test.blocks.Domclick;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.PageFactory;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.exceptions.WaitException;
import ru.sbtqa.tag.pagefactory.extensions.DriverExtension;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

@FindBy(id = "topline__grid-row")
public class Topline extends HtmlElement {

    private static final Logger LOG = LoggerFactory.getLogger(Topline.class);

    @Name("Ипотека")
    @FindBy(id = "Topline_menuItem_Ipoteka")
    public Link hypotecLink;

    @ActionTitle("кликает по разделу")
    public void clickSectionByName(String sectionTitle) {
        WebElement webElement;
        By partialLink = By.partialLinkText(sectionTitle);
        try {
            webElement = findElement(partialLink);
            DriverExtension.waitForElementGetEnabled(webElement, PageFactory.getTimeOut());
        } catch (NoSuchElementException | WaitException e) {
            LOG.warn("Failed to find section by title {}", sectionTitle, e);
            webElement = DriverExtension.waitUntilElementAppearsInDom(partialLink);
        }
        webElement.click();
    }
}
