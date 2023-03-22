package com.mibanco.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features={"src//main//resources//features"},
        glue={
                "com.mibanco.steps",
                "com.mibanco.helpers"
        },
        tags = "@AUTO-CP0001",
        monochrome = true,
        publish = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)

@Test
public class RunMiBanco extends AbstractTestNGCucumberTests { }


