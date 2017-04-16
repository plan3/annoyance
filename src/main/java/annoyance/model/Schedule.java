package annoyance.model;

import static java.util.stream.Collectors.toMap;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Schedule {

    public static Map<String, String> filter(final DayOfWeek today, final Map<String, String> env) {
        final Set<String> valid = new HashSet<>();
        switch(today) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                valid.add("WEEKDAY");
            default:
                valid.add(today.name());
                valid.add("DAILY");
        }
        return env.entrySet().stream()
                .filter(e -> valid.contains(e.getKey().substring(0, e.getKey().indexOf('_'))))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
}
