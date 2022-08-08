package acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features"},
        glue = {"acceptance.steps"},
        monochrome = true,
        dryRun = false,
        plugin = {
                "pretty",
                "json:build/cucumber-reports/cucumber.json",
                "rerun:build/cucumber-reports/rerun.txt"
        })
public class CucumberRunner {
}
