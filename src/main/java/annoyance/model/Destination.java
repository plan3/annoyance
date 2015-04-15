package annoyance.model;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Destination {
    private static final DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static String DATE = "{date}";
    private static final DateTimeFormatter week = DateTimeFormatter.ofPattern("w");
    private static String WEEK = "{week}";
    private final String path;
    private final Repository repository;
    private final String message;

    public Destination(final Repository repository, final String path, final String message) {
        this(Clock.systemUTC(), repository, path, message);
    }

    public Destination(final Clock clock, final Repository repository, final String path, final String message) {
        this.repository = repository;
        this.message = message;
        final LocalDateTime now = LocalDateTime.now(clock);
        this.path = path.replace(DATE, now.format(date)).replace(WEEK, now.format(week));;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.repository + ':' + this.path + ']';
    }

    public GHRepository from(final GitHub github) throws IOException {
        return this.repository.from(github);
    }

    public String getMessage() {
        return this.message;
    }
}