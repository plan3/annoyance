package annoyance.model;

import static java.util.Arrays.copyOfRange;

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

    public Destination(final Repository repository, final String path) {
        this(Clock.systemUTC(), repository, path);
    }

    public Destination(final Clock clock, final Repository repository, final String path) {
        this.repository = repository;
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

    public static Destination parse(final String string) {
        final String[] segments = string.split("/");
        // At least owner, repo and a file name
        if(segments.length > 2) {
            final Repository repository = new Repository(segments[0], segments[1]);
            final String template = String.join("/", copyOfRange(segments, 2, segments.length));
            return new Destination(repository, template);
        }
        throw new IllegalArgumentException("owner/repo/path required, was: " + string);
    }
}