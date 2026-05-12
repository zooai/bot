---
summary: "CLI reference for `zoo-bot daemon` (legacy alias for gateway service management)"
read_when:
  - You still use `zoo-bot daemon ...` in scripts
  - You need service lifecycle commands (install/start/stop/restart/status)
title: "daemon"
---

# `zoo-bot daemon`

Legacy alias for Gateway service management commands.

`zoo-bot daemon ...` maps to the same service control surface as `zoo-bot gateway ...` service commands.

## Usage

```bash
zoo-bot daemon status
zoo-bot daemon install
zoo-bot daemon start
zoo-bot daemon stop
zoo-bot daemon restart
zoo-bot daemon uninstall
```

## Subcommands

- `status`: show service install state and probe Gateway health
- `install`: install service (`launchd`/`systemd`/`schtasks`)
- `uninstall`: remove service
- `start`: start service
- `stop`: stop service
- `restart`: restart service

## Common options

- `status`: `--url`, `--token`, `--password`, `--timeout`, `--no-probe`, `--deep`, `--json`
- `install`: `--port`, `--runtime <node|bun>`, `--token`, `--force`, `--json`
- lifecycle (`uninstall|start|stop|restart`): `--json`

Notes:

- `status` resolves configured auth SecretRefs for probe auth when possible.
- When token auth requires a token and `gateway.auth.token` is SecretRef-managed, `install` validates that the SecretRef is resolvable but does not persist the resolved token into service environment metadata.
- If token auth requires a token and the configured token SecretRef is unresolved, install fails closed.
- If both `gateway.auth.token` and `gateway.auth.password` are configured and `gateway.auth.mode` is unset, install is blocked until mode is set explicitly.

## Prefer

Use [`zoo-bot gateway`](/cli/gateway) for current docs and examples.
