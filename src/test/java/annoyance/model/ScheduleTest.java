package annoyance.model;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
public class ScheduleTest {

    public static final Map<String, String> ENV = new HashMap<String, String>() {{
        put("MONDAY_", "");
        put("TUESDAY_", "");
        put("WEDNESDAY_", "");
        put("THURSDAY_", "");
        put("FRIDAY_", "");
        put("SATURDAY_", "");
        put("SUNDAY_", "");
        put("DAILY_", "");
        put("WEEKDAY_", "");
    }};

    @Test
    public void filter() {
        assertThat(Schedule.filter(DayOfWeek.SATURDAY, ENV))
                .hasSize(2)
                .contains(entry("SATURDAY_", ""), entry("DAILY_", ""));
        assertThat(Schedule.filter(DayOfWeek.FRIDAY, ENV))
                .hasSize(3)
                .contains(entry("FRIDAY_", ""), entry("DAILY_", ""), entry("WEEKDAY_", ""));
        assertThat(Schedule.filter(DayOfWeek.MONDAY, ENV))
                .hasSize(3)
                .contains(entry("MONDAY_", ""), entry("DAILY_", ""), entry("WEEKDAY_", ""));
    }
}
