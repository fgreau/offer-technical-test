package com.fgreau.offertechtest.unitTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Abstract test class to embed all the necessary annotations.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback()
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-unit-test.properties")
public abstract class AbstractTest {

}