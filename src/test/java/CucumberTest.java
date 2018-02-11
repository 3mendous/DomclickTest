import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import ru.sbtqa.tag.cucumber.TagCucumber;

@RunWith(TagCucumber.class)
@CucumberOptions(plugin = {"ru.sbtqa.tag.allure.TagAllureReporter"},
        glue = {"ru.sbtqa.tag.pagefactory"},
        features = {"src/test/resources/features/"},
        tags = {"@test"})

public class CucumberTest {}