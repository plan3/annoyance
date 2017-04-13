# Annoyance

## What

Regularly reminding groups of people about the chores they wouldn't otherwise do by opening pull requests and mentioning the ones deemed responsible. Examples of templates and pull requests can be found [here](https://github.com/smp-labs/annoyances).

## Why

Because teams of people have collective chores that they need to perform.

## How

### Basic configuration

* `GITHUB_TOKEN`: A personal access token with proper permissions
* `GITHUB_API_URL`: (optional) Github API URL, only needed for use with Github Enterprise

### Configuring annoyances

* General
   * `<schedule>` is one of `daily` or `weekly` where...
      * `daily` is executed at noon
      * `weekly` is executed on Fridays at noon
   * The destination supports the following variable expansions
      * `{date}` expands to the current date in `YYYY-MM-DD` format (i.e. `2015-03-14`)
      * `{week}` expands to the current week number (i.e. `08`)
   * The environment variable name for a task will be used as the title of the pull request
      * Underscore `_` will be replaced by space ` `
* Examples
   * Open pull request
      * `<schedule>_some-descriptive-name=<owner/org>/<repo>:<owner/org>/<repo>/<where-to-put-file>:<pr-description>`
      * e.g. `DAILY_EXAMPLE=chids/annoyance/template.md:chids/annoyance/stuff/{date}/{week}.md:@chids`

### Heroku app configuration

1. Add the [Scheduler](https://devcenter.heroku.com/articles/scheduler) add-on
2. Configure it to run daily at noon

### Running locally
