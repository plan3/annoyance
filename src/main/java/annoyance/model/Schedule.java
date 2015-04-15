package annoyance.model;

import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;

public enum Schedule {
    daily, weekly;

    public Map<String, String> find(final Map<String, String> env) {
        final String prefix = name().concat(":");
        return env.entrySet().stream()
                .filter((job) -> job.getValue().startsWith(prefix))
                .map((job) -> new SimpleEntry<>(job.getKey(), job.getValue().replaceFirst(prefix, "")))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
}
