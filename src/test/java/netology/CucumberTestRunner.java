package netology;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "ru.netology.steps",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {"pretty", "html:build/reports/cucumber-html-report"},
        monochrome = true
)
public class CucumberTestRunner {
}
