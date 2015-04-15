package annoyance;

import static java.lang.System.getenv;
import static java.util.Arrays.asList;

import annoyance.model.Destination;
import annoyance.model.PullRequest;
import annoyance.model.Repository;
import annoyance.model.Schedule;
import annoyance.model.Source;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        return this.schedule.find(this.env).stream()
                .map((task) -> task.split(":"))
                .map(Arrays::asList)
                .map(this::toPullRequest);
    }

    public PullRequest toPullRequest(final List<String> job) {
        // We need mutable lists
        final List<String> src = new ArrayList<>(asList(job.get(0).split("/")));
        final List<String> dst = new ArrayList<>(asList(job.get(1).split("/")));
        final Source source = new Source(new Repository(src), src.iterator().next());
        final Destination destination = new Destination(new Repository(dst), String.join("/", dst));
        return new PullRequest(source, destination);
    }

    public static void main(final String[] args) throws IOException {
        final Map<String, String> env = getenv();
        final GitHub github = GitHub.connect("x-oauth-basic", env.get("GITHUB_TOKEN"));
        run(github, LocalDate.now(Clock.systemUTC()), env);
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