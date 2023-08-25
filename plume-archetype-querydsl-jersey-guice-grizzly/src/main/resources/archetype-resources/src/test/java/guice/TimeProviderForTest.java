package ${package}.guice;

import com.coreoz.plume.services.time.TimeProvider;

import javax.inject.Singleton;
import java.time.Clock;

@Singleton
public class TimeProviderForTest implements TimeProvider {
	private Clock clock;

	public TimeProviderForTest() {
		this.clock = Clock.systemDefaultZone();
	}

	@Override
	public Clock clock() {
		return clock;
	}

	public void changeClock(Clock clock) {
		this.clock = clock;
	}

    public void withClock(Clock clock, Runnable toExecuteWithClock) {
        Clock oldClock = this.clock;
        changeClock(clock);
        toExecuteWithClock.run();
        changeClock(oldClock);
    }
}
