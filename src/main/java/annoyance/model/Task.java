package annoyance.model;

import org.kohsuke.github.GitHub;

public interface Task {
    enum Type {
        PR, ISSUE;
    }

    boolean execute(GitHub github);
}
