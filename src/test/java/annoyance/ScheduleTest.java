package annoyance;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.fest.assertions.api.Assertions.assertThat;

import annoyance.model.Schedule;

import org.junit.Test;

public class ScheduleTest {

    public void empty() {
        for(final Schedule schedule : Schedule.values()) {
            assertThat(schedule.find(emptyMap())).isEmpty();
        }
    }

    @Test
    public void daily() {
        assertThat(Schedule.daily.find(singletonMap("BLAH", "daily:bleh:blah"))).isEqualTo(asList("bleh:blah"));
        assertThat(Schedule.daily.find(singletonMap("BLAH", "weekly:bleh:blah"))).isEmpty();
    }

    @Test
    public void weekly() {
        assertThat(Schedule.weekly.find(singletonMap("BLOH", "weekly:bleh:blah"))).isEqualTo(asList("bleh:blah"));
        assertThat(Schedule.weekly.find(singletonMap("BLOH", "daily:bleh:blah"))).isEmpty();
    }
}
