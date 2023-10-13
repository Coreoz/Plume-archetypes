package ${package}.integration;

import ${package}.guice.TestModule;
import ${package}.webservices.api.ExampleWs;

import com.carlosbecker.guice.GuiceModules;
import com.carlosbecker.guice.GuiceTestRunner;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

// TODO provide basic documentation
@RunWith(GuiceTestRunner.class)
@GuiceModules(TestModule.class)
public class SampleIntegrationTest {
    @Inject
    ExampleWs exampleWs;
    @Test
    public void integration_test_scenario_description() {
        Assertions
            .assertThat(exampleWs.test("World").getName())
            .isEqualTo("hello World\nA configuration value");
    }
}
