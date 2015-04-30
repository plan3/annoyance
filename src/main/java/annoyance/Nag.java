package annoyance;

import static java.lang.System.getenv;

import annoyance.model.Destination;
import annoyance.model.PullRequest;
import annoyance.model.Schedule;
import annoyance.model.Source;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.kohsuke.github.GitHub;

public class Nag {

    private final Map<String, String> env;
    private final Schedule schedule;

    public Nag(final Schedule schedule, final Map<String, String> env) {
        this.schedule = schedule;
        this.env = env;
    }

    public Stream<PullRequest> tasks() {
        return this.schedule.find(this.env).entrySet().stream()
                .map((task) -> new SimpleEntry<>(task.getKey(), task.getValue().split(":")))
                .map(Nag::toPullRequest);
    }

    static PullRequest toPullRequest(final Entry<String, String[]> task) {
        final String[] job = task.getValue();
        final Optional<String> message = Optional.ofNullable((job.length < 3) || job[2].isEmpty() ? null : job[2]);
        return new PullRequest(task.getKey(), Source.parse(job[0]), Destination.parse(job[1], message));
    }

    public static void main(final String[] args) throws IOException {
        final Map<String, String> env = getenv();
        final GitHub github = github(env);
        run(github, LocalDate.now(Clock.systemUTC()), env);
    }

    private static GitHub github(final Map<String, String> env) throws IOException {
        final String ghe = getenv("GITHUB_API_URL");
        final String token = env.get("GITHUB_TOKEN");
        if((ghe == null) || ghe.trim().isEmpty()) {
            return GitHub.connect("x-oauth-basic", token);
        }
        return GitHub.connectToEnterprise(ghe, "x-oauth-basic", token);
    }

    public static void run(final GitHub github, final LocalDate now, final Map<String, String> env) {
        switch(now.getDayOfWeek()) {
            case FRIDAY:
                run(Schedule.weekly, github, env);
            default:
                run(Schedule.daily, github, env);

        }
    }

    public static void run(final Schedule schedule, final GitHub github, final Map<String, String> env) {
        new Nag(schedule, env).tasks()
                .map((task) -> Boolean.toString(task.execute(github)) + ':' + task.toString())
                .forEach(System.err::println);
    }
}