package annoyance;

import static org.fest.assertions.api.Assertions.assertThat;

import annoyance.model.Destination;
import annoyance.model.Repository;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.Test;

public class DestinationTest {
    @Test
    public void destination() {
        final Optional<String> message = Optional.of("meh");
        final Repository repo = new Repository("owner", "name");
        assertThat(new Destination(repo, "foo/bar/baz", message).getPath()).isEqualTo("foo/bar/baz");
        final Clock clock = Clock.fixed(Instant.parse("1979-08-07T10:10:10.00Z"), ZoneOffset.UTC);
        assertThat(new Destination(clock, repo, "foo/{date}/baz", message).getPath()).isEqualTo("foo/1979-08-07/baz");
        assertThat(new Destination(clock, repo, "foo/bar/{week}-baz", message).getPath()).isEqualTo("foo/bar/32-baz");
        assertThat(new Destination(clock, repo, "foo/bar/{week}-baz", message).getMessage()).isEqualTo("meh");
    }
}