package annoyance.model;

import annoyance.model.Source;

import org.junit.Test;

public class SourceTest {

    @Test
    public void parseValid() {
        Source.parse("owner/repo/path");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalid() {
        Source.parse("owner/repo");
    }
}