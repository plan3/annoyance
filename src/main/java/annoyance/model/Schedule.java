package annoyance.model;

import static java.util.stream.Collectors.toMap;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;

public enum Schedule {
    daily, weekly;

    public Map<String, String> find(final Map<String, String> env) {
        return env.entrySet().stream()
                .filter(job -> job.getKey().toLowerCase().startsWith(name().toLowerCase()))
                .map(job -> new SimpleEntry<>(job.getKey(), job.getValue()))
                .collect(toMap(Entry::getKey, Entry::getValue));
    }
}
