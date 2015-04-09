package annoyance.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;

public enum Schedule {
    daily, weekly;

    public List<String> find(final Map<String, String> env) {
        final String prefix = name().concat(":");
        return env.values().stream()
                .filter((job) -> job.startsWith(prefix))
                .map((job) -> job.replaceFirst(prefix, ""))
                .collect(toList());
    }
}
