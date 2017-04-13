package annoyance.model;

import java.io.IOException;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Issue implements Task {
    private final Source source;
    private final Destination destination;
    private final String title;

    public Issue(final String title, final Source source, final Destination destination) {
        this.title = title.replace('_', ' ');
        this.source = source;
        this.destination = destination;
    }

    @Override
    public boolean execute(final GitHub github) {
        try {
            final GHRepository repository = this.destination.from(github);
            repository.createIssue(this.title).body(this.source.from(github).render()).create();
            return true;
        }
        catch(final IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.source + " => " + this.destination + ']';
    }
}