package annoyance;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.api.Assertions.assertThat;

import annoyance.model.Issue;
import annoyance.model.PullRequest;
import annoyance.model.Task;

import org.junit.Test;

public class NagTest {

    private static final String PR = "pr:";
    private static final String ISSUE = "issue:";
    private static final String MENTION = "owner/repo/template.md:owner/repo/stuff/{date}/{week}.md:@chids";
    private static final String NO_MENTION = "owner/repo/template.md:owner/repo/stuff/{date}/{week}.md";

    @Test
    public void prWithMention() {
        assertResult(new Nag(singletonMap("DAILY_TEST", PR.concat(MENTION))), PullRequest.class);
    }

    @Test
    public void prWithoutMention() {
        assertResult(new Nag(singletonMap("WEEKLY_TEST", PR.concat(NO_MENTION))), PullRequest.class);
    }

    @Test
    public void issueWithMention() {
        assertResult(new Nag(singletonMap("DAILY_TEST", ISSUE.concat(MENTION))), Issue.class);
    }

    @Test
    public void issueWithoutMention() {
        assertResult(new Nag(singletonMap("WEEKLY_TEST", ISSUE.concat(NO_MENTION))), Issue.class);
    }

    private void assertResult(final Nag worker, final Class<? extends Task> type) {
        assertThat(worker.tasks().collect(toList())).hasSize(1);
        assertThat(worker.tasks().collect(toList()).get(0)).isExactlyInstanceOf(type);
    }
}
