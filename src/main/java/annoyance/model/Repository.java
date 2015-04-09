package annoyance.model;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Repository {
    private final String owner;
    private final String name;

    public Repository(final List<String> segments) {
        this(segments.remove(0), segments.remove(0));
    }

    public Repository(final String owner, final String name) {
        this.owner = requireNonNull(owner, "Owner");
        this.name = requireNonNull(name, "Name");
    }

    public GHRepository from(final GitHub github) throws IOException {
        return github.getRepository(String.join("/", this.owner, this.name));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.owner + '/' + this.name + ']';
    }

    public static Repository parse(final String string) {
        final String[] segments = string.split("/");
        // At least "owner/repo"
        if(segments.length > 1) {
            return new Repository(segments[0], segments[1]);
        }
        throw new IllegalArgumentException("owner/repo required, was: " + string);
    }
}