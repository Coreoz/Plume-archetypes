package ${package}.guice;

import com.coreoz.plume.services.time.TimeProvider;

import jakarta.inject.Singleton;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Override the default {@link TimeProvider} for testing purposes.
 * This adds the possibility to change how time flows during a test.
 * This works only for code that relies on the {@link TimeProvider}
 */
@Singleton
public class TimeProviderForTest implements TimeProvider {
	private Clock clock;

	public TimeProviderForTest() {
		this.clock = Clock.systemDefaultZone();
	}

    /**
     * Returns the current clock used
     */
	@Override
	public Clock clock() {
		return clock;
	}

    /**
     * Changes the current clock used. This is generally a temporary measure that should be reverted.
     * See {@link #executeWithClock(Clock, Runnable)} for usage
     * @param newClock The new clock to use
     */
	public void changeClock(Clock newClock) {
		this.clock = newClock;
	}

    /**
     * Execute a function with a custom clock. If unsure, use {@link #executeWithInstant(Instant, Runnable)} or {@link #executeWithConstantTime(Runnable)} instead
     * @param newClock The custom clock
     * @param toExecuteWithClock The function to execute
     */
    public void executeWithClock(Clock newClock, Runnable toExecuteWithClock) {
        Clock oldClock = this.clock;
        changeClock(newClock);
        toExecuteWithClock.run();
        changeClock(oldClock);
    }

    /**
     * Execute a function for which for time does not change
     * @param fixedInstantForExecution The instant that will be used to execute the function
     * @param toExecuteWithInstant The function to execute
     */
    public void executeWithInstant(Instant fixedInstantForExecution, Runnable toExecuteWithInstant) {
        executeWithClock(
            Clock.fixed(fixedInstantForExecution, ZoneId.systemDefault()),
            toExecuteWithInstant
        );
    }

    /**
     * Execute a function for which for time does not change
     * @param toExecuteWithConstantTime The function to execute
     */
    public void executeWithConstantTime(Runnable toExecuteWithConstantTime) {
        executeWithInstant(Instant.now(), toExecuteWithConstantTime);
    }
}
