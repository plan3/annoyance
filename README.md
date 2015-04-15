# Annoyance

## What

Regularly reminding groups of people about the chores they wouldn't otherwise do by opening pull requests and mentioning the ones deemed responsible.

## Why

Because teams of people have collective chores that they need to perform.

## How

### Basic configuration

* `GITHUB_TOKEN`: A personal access token with proper permissions

### Configuring annoyances

* General
   * `<schedule>` is one of `daily` or `weekly` where...
      * `daily` is executed at noon
      * `weekly` is executed on Fridays at noon
   * The destination supports the following variable expansions
      * `{DATE}` expands to the current date in `YYYY-MM-DD` format (i.e. `2015-03-14`)
      * `{WEEK}` expands to the current week number (i.e. `08`)
   * The environment variable name for a task will be used as the title of the pull request
      * Underscore `_` will be replaced by space ` `
* Examples
   * Open pull request
      * `<qualifier>=<schedule>:<owner/org>/<repo>:<owner/org>/<repo>/<where-to-put-file>`
      * e.g. `EXAMPLE=daily:pr:chids/annoyance/template.md:chids/annoyance/stuff/{date}/{week}.md`

### Heroku app configuration

1. Add one worker addon
2. Configure the worker to run daily at noon
3. The command to run `` 