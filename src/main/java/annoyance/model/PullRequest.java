package annoyance.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class PullRequest {
    private final Source source;
    private final Destination destination;

    public PullRequest(final Source source, final Destination destination) {
        this.source = source;
        this.destination = destination;
    }

    public boolean execute(final GitHub github) {
        try(InputStream content = this.source.from(github)) {
            final GHRepository repository = this.destination.from(github);
            final String branch = "annoyance-" + UUID.randomUUID().toString();
            final String master = repository.getRef("heads/master").getObject().getSha();
            repository.createRef("refs/heads/".concat(branch), master);
            repository.createContent(
                    IOUtils.toByteArray(content),
                    this.source.toString(),
                    this.destination.getPath(),
                    branch);
            repository.createPullRequest("an title", branch, "master", "yalla, @chids");
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