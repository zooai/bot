---
summary: "CLI reference for `zoo-bot config` (get/set/unset/file/validate)"
read_when:
  - You want to read or edit config non-interactively
title: "config"
---

# `zoo-bot config`

Config helpers: get/set/unset/validate values by path and print the active
config file. Run without a subcommand to open
the configure wizard (same as `zoo-bot configure`).

## Examples

```bash
zoo-bot config file
zoo-bot config get browser.executablePath
zoo-bot config set browser.executablePath "/usr/bin/google-chrome"
zoo-bot config set agents.defaults.heartbeat.every "2h"
zoo-bot config set agents.list[0].tools.exec.node "node-id-or-name"
zoo-bot config unset tools.web.search.apiKey
zoo-bot config validate
zoo-bot config validate --json
```

## Paths

Paths use dot or bracket notation:

```bash
zoo-bot config get agents.defaults.workspace
zoo-bot config get agents.list[0].id
```

Use the agent list index to target a specific agent:

```bash
zoo-bot config get agents.list
zoo-bot config set agents.list[1].tools.exec.node "node-id-or-name"
```

## Values

Values are parsed as JSON5 when possible; otherwise they are treated as strings.
Use `--strict-json` to require JSON5 parsing. `--json` remains supported as a legacy alias.

```bash
zoo-bot config set agents.defaults.heartbeat.every "0m"
zoo-bot config set gateway.port 19001 --strict-json
zoo-bot config set channels.whatsapp.groups '["*"]' --strict-json
```

## Subcommands

- `config file`: Print the active config file path (resolved from `BOT_CONFIG_PATH` or default location).

Restart the gateway after edits.

## Validate

Validate the current config against the active schema without starting the
gateway.

```bash
zoo-bot config validate
zoo-bot config validate --json
```
