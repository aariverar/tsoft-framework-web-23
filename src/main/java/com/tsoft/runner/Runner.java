package com.tsoft.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features={"src//main//resources//features"},
        glue={
                "com.tsoft.steps",
                "com.tsoft.helpers"
        },
        tags = "@CP001-PET",
        monochrome = true,
        publish = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {
                "json:target/cucumber.json",
                "junit:target/cucumber-junit-report.xml"
        }

)
@Test
public class Runner extends AbstractTestNGCucumberTests {

}


