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
    public void parseString() {
        Destination.parse("chids/annoyance-data/stuff/{date}/{week}.md");
    }
    @Test
    public void destination() {
        final Repository repository = new Repository("owner", "name");
        assertThat(new Destination(repository, "foo/bar/baz").getPath()).isEqualTo("foo/bar/baz");
        final Clock clock = Clock.fixed(Instant.parse("1979-08-07T10:10:10.00Z"), ZoneOffset.UTC);
        assertThat(new Destination(clock, repository, "foo/{date}/baz").getPath()).isEqualTo("foo/1979-08-07/baz");
        assertThat(new Destination(clock, repository, "foo/bar/{week}-baz").getPath()).isEqualTo("foo/bar/32-baz");
    }
}