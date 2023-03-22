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
        tags = "@REG",
        monochrome = true,
        publish = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)

@Test
public class Runner extends AbstractTestNGCucumberTests { }


