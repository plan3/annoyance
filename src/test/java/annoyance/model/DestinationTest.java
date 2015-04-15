package annoyance.model;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Test;

public class DestinationTest {
    @Test
    public void destination() {
        final Optional<String> message = Optional.of("meh");
        final Repository repo = new Repository("owner", "name");
        assertThat(new Destination(repo, "foo/bar/baz", message).getPath()).isEqualTo("foo/bar/baz");
        assertThat(new Destination(repo, "foo/bar/{week}-baz", message).getMessage()).isEqualTo("meh");
    }

    @Test
    public void rendersTemplate() {
        final Template template = mock(Template.class);
        final Destination dst = new Destination(new Repository("owner", "name"), template, Optional.empty());
        dst.getPath();
        verify(template).render();
    }

    @Test
    public void parse() {
        final String job = "owner/repo/stuff/{date}/{week}.md";
        final Destination destination = Destination.parse(job, Optional.empty());
        assertThat(destination.getPath())
                .doesNotContain("{date}")
                .doesNotContain("{week}")
                .startsWith("stuff/")
                .endsWith(".md");
    }

}