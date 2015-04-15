package annoyance;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.fest.assertions.api.Assertions.assertThat;

import annoyance.model.Schedule;

import java.util.Map;

import org.junit.Test;

public class ScheduleTest {

    public void empty() {
        for(final Schedule schedule : Schedule.values()) {
            assertThat(schedule.find(emptyMap())).isEmpty();
        }
    }

    @Test
    public void daily() {
        final Map<String, String> expected = singletonMap("BLAH", "bleh:blah");
        assertThat(Schedule.daily.find(singletonMap("BLAH", "daily:bleh:blah"))).isEqualTo(expected);
        assertThat(Schedule.daily.find(singletonMap("BLAH", "weekly:bleh:blah"))).isEmpty();
    }

    @Test
    public void weekly() {
        final Map<String, String> expected = singletonMap("BLOH", "bleh:blah");
        assertThat(Schedule.weekly.find(singletonMap("BLOH", "weekly:bleh:blah"))).isEqualTo(expected);
        assertThat(Schedule.weekly.find(singletonMap("BLOH", "daily:bleh:blah"))).isEmpty();
    }
}
