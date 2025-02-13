package org.example.slicetest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
//@Profile("test")
@ActiveProfiles("test")
public abstract class IntegrationTestSupport {


}
