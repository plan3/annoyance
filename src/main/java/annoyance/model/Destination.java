package annoyance.model;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Destination {
    private final Template path;
    private final Repository repository;
    private final Optional<String> message;

    public Destination(final Repository repository, final String path, final Optional<String> message) {
        this(repository, new Template(path), message);
    }

    public Destination(final Repository repository, final Template template, final Optional<String> message) {
        this.repository = repository;
        this.message = message;
        this.path = template;
    }

    public String getPath() {
        return this.path.render();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.repository + ':' + this.path + ']';
    }

    public GHRepository from(final GitHub github) throws IOException {
        return this.repository.from(github);
    }

    public String getMessage() {
        return this.message.orElse("");
    }

    public static Destination parse(final String job, final Optional<String> message) {
        final List<String> dst = new ArrayList<>(asList(job.split("/")));
        return new Destination(new Repository(dst), String.join("/", dst), message);
    }
}