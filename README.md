# Annoyance

## What

Regularly reminding groups of people about the chores they wouldn't otherwise do by opening pull requests and mentioning the ones deemed responsible. Examples of templates and pull requests can be found [here](https://github.com/smp-labs/annoyances).

## Why

Because teams of people have collective chores that they need to perform.

## How

### Basic configuration

* `GITHUB_TOKEN`
   * A personal access token with `public_repo` if you're using public repositories or `repo` permissions if you need yo use private repositories
* `GITHUB_API_URL`: _(optional)_
   * Only needed for use with Github Enterprise

### Configuring annoyances

* General
   * `<schedule>` is one of `daily` or `weekly` where...
      * `daily` is executed daily
      * `weekly` is executed on Fridays
   * `<task>` is either or `pr` or `issue`
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
2. Configure it to run daily at the time when you want stuff to be created

### Running locally

1. Create a `.env` with the env vars documented above
2. `heroku local worker`