package ${package};

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * A unit test sample.
 *
 * Unit tests are a great tool for:
 * - Testing exhaustively a function by changing all the parameters to verify that is fully respects its specification
 * - Testing a function that does not have a lot of dependencies
 *
 * To test something that has interactions with the database, or not only one function but a chain of services,
 * integration tests are preferred. See {@link SampleIntegrationTest} for an example.
 *
 * Once there are other unit tests in the project, this sample should be deleted.
 */
public class SampleTest {
    @Test
    public void methodToTest__test_scenario_description() {
        Assertions.assertThat(1).isEqualTo(1);
    }
}
