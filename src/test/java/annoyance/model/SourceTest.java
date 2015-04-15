package annoyance.model;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class SourceTest {

    @Test
    public void parse() {
        final String job = "owner/repo/path/to/template.md";
        final String string = Source.parse(job).toString();
        assertThat(string).contains("owner/repo");
        assertThat(string).contains("path/to/template.md");
    }

}
