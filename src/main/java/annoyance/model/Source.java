package annoyance.model;

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

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + this.repository + ':' + this.template + ']';
    }
}