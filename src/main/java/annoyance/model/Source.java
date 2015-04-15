package annoyance.model;

import static java.util.Arrays.copyOfRange;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class Source {
    private final String template;
    private final Repository repository;

    public Source(final Repository repository, final String template) {
        this.repository = repository;
        this.template = template;
    }

    public Template from(final GitHub github) throws IOException {
        final GHRepository repository = this.repository.from(github);
        try(InputStream stream = repository.getFileContent(this.template).read()) {
            return new Template(IOUtils.toString(stream));
        }
    }

    public static Source parse(final String string) {
        final String[] segments = string.split("/");
        // At least owner, repo and a file name
        if(segments.length > 2) {
            final Repository repository = new Repository(segments[0], segments[1]);
            final String template = String.join("/", copyOfRange(segments, 2, segments.length));
            return new Source(repository, template);
        }
        throw new IllegalArgumentException("owner/repo/path required, was: " + string);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.repository + ':' + this.template + ']';
    }
}