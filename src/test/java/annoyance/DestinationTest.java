package annoyance;

import static org.fest.assertions.api.Assertions.assertThat;

import annoyance.model.Destination;
import annoyance.model.Repository;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.Test;

public class DestinationTest {
    @Test
    public void destination() {
        final Repository repo = new Repository("owner", "name");
        assertThat(new Destination(repo, "foo/bar/baz", "blah").getPath()).isEqualTo("foo/bar/baz");
        final Clock clock = Clock.fixed(Instant.parse("1979-08-07T10:10:10.00Z"), ZoneOffset.UTC);
        assertThat(new Destination(clock, repo, "foo/{date}/baz", "bleh").getPath()).isEqualTo("foo/1979-08-07/baz");
        assertThat(new Destination(clock, repo, "foo/bar/{week}-baz", "bloh").getPath()).isEqualTo("foo/bar/32-baz");
        assertThat(new Destination(clock, repo, "foo/bar/{week}-baz", "meh").getMessage()).isEqualTo("meh");
    }
}