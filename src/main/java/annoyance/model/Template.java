package annoyance.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Template {
    private static final DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static String DATE = "{date}";
    private static final DateTimeFormatter week = DateTimeFormatter.ofPattern("w");
    private static String WEEK = "{week}";
    private static final DateTimeFormatter dow = DateTimeFormatter.ofPattern("EEEE");
    private static String DOW = "{dow}";
    private final String source;
    private final Clock clock;

    public Template(final String source) {
        this(source, Clock.systemUTC());
    }

    public Template(final String source, final Clock clock) {
        this.source = source;
        this.clock = clock;
    }

    public String render() {
        final LocalDateTime now = LocalDateTime.now(this.clock);
        return this.source.replace(DATE, now.format(date)).replace(WEEK, now.format(week)).replace(DOW, now.format(dow));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.source + ']';
    }
}
