package annoyance.model;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;

public class ScheduleTest {

    @Test
    public void empty() {
        for(final Schedule schedule : Schedule.values()) {
            assertThat(schedule.find(emptyMap())).isEmpty();
        }
    }

    @Test
    public void daily() {
        final Map<String, String> expected = singletonMap("DAILY_BLAH", "pr:bleh:blah");
        assertThat(Schedule.daily.find(singletonMap("DAILY_BLAH", "pr:bleh:blah"))).isEqualTo(expected);
        assertThat(Schedule.daily.find(singletonMap("WEEKLY_BLAH", "pr:bleh:blah"))).isEmpty();
    }

    @Test
    public void weekly() {
        final Map<String, String> expected = singletonMap("WEEKLY_BLOH", "pr:bleh:blah");
        assertThat(Schedule.weekly.find(singletonMap("WEEKLY_BLOH", "pr:bleh:blah"))).isEqualTo(expected);
        assertThat(Schedule.weekly.find(singletonMap("DAILY_BLOH", "pr:bleh:blah"))).isEmpty();
    }
}
