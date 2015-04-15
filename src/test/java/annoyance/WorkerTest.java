package annoyance;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.api.Assertions.assertThat;

import annoyance.model.Schedule;

import org.junit.Test;

public class WorkerTest {

    @Test
    public void parseEnv() {
        final String env = "daily:chids/annoyance-data/template.md:chids/annoyance-data/stuff/{date}/{week}.md:@chids";
        final Nag worker = new Nag(Schedule.daily, singletonMap("mock", env));
        assertThat(worker.tasks().collect(toList())).hasSize(1);
    }
}
