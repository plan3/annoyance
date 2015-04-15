package annoyance.model;

import static org.fest.assertions.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.Test;

public class TemplateTest {
    @Test
    public void render() {
        assertThat(new Template("foo/bar/baz").render()).isEqualTo("foo/bar/baz");
        final Clock clock = Clock.fixed(Instant.parse("1979-08-07T10:10:10.00Z"), ZoneOffset.UTC);
        assertThat(new Template("foo/{date}/baz", clock).render()).isEqualTo("foo/1979-08-07/baz");
        assertThat(new Template("foo/bar/{week}-baz", clock).render()).isEqualTo("foo/bar/32-baz");
    }
}