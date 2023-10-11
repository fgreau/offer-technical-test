package com.fgreau.offertechtest.integrationTests;

import com.fgreau.offertechtest.OfferTechnicalTestApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Cucumber tests configuration.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = OfferTechnicalTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class CucumberSpringConfiguration {
}
