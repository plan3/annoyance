package annoyance.model;

import java.io.IOException;
import java.util.UUID;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class PullRequest {
    private final Source source;
    private final Destination destination;
    private final String title;

    public PullRequest(final String title, final Source source, final Destination destination) {
        this.title = title.replace('_', ' ');
        this.source = source;
        this.destination = destination;
    }

    public boolean execute(final GitHub github) {
        try {
            final GHRepository repository = this.destination.from(github);
            final String branch = "annoyance-" + UUID.randomUUID().toString();
            final String master = repository.getRef("heads/master").getObject().getSha();
            repository.createRef("refs/heads/".concat(branch), master);
            repository.createContent(
                    this.source.from(github).render(),
                    this.source.toString(),
                    this.destination.getPath(),
                    branch);
            // Creating the PR occasionally fails with the message...
            // "No commits between master and annoyance-08baaeca-7b27-480c-b838-de7e00f188fb"
            // ...which I assume is an eventual consistency thing, i.e.: the branch commit hasn't been fully propagated
            // and therefore the branch doesn't differ from 'master' and creating the PR fails.
            repository.createPullRequest(this.title, branch, "master", this.destination.getMessage());
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