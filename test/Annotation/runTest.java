package Annotation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "Annotation.Steps",
        features = "classpath:Annotation/Outline.feature"
)
public class runTest{ }