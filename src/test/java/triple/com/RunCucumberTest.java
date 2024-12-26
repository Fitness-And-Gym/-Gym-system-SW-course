package triple.com;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
  features = "src/test/resources/features",  // Path to your .feature files
  plugin = { "summary", "html:target/cucumber/wikipedia.html"},
	monochrome=true,
	snippets = SnippetType.CAMELCASE,
  glue = "triple.com"  // Package containing your step definitions
)
public class RunCucumberTest {
    // This class will be empty, it's just used to run the tests
}
