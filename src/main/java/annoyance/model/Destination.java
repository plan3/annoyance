package annoyance.model;

import java.io.IOException;
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
}