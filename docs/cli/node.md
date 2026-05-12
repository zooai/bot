---
summary: "CLI reference for `zoo-bot node` (headless node host)"
read_when:
  - Running the headless node host
  - Pairing a non-macOS node for system.run
title: "node"
---

# `zoo-bot node`

Run a **headless node host** that connects to the Gateway WebSocket and exposes
`system.run` / `system.which` on this machine.

## Why use a node host?

Use a node host when you want agents to **run commands on other machines** in your
network without installing a full macOS companion app there.

Common use cases:

- Run commands on remote Linux/Windows boxes (build servers, lab machines, NAS).
- Keep exec **sandboxed** on the gateway, but delegate approved runs to other hosts.
- Provide a lightweight, headless execution target for automation or CI nodes.

Execution is still guarded by **exec approvals** and per‑agent allowlists on the
node host, so you can keep command access scoped and explicit.

## Browser proxy (zero-config)

Node hosts automatically advertise a browser proxy if `browser.enabled` is not
disabled on the node. This lets the agent use browser automation on that node
without extra configuration.

Disable it on the node if needed:

```json5
{
  nodeHost: {
    browserProxy: {
      enabled: false,
    },
  },
}
```

## Run (foreground)

```bash
zoo-bot node run --host <gateway-host> --port 18789
```

Options:

- `--host <host>`: Gateway WebSocket host (default: `127.0.0.1`)
- `--port <port>`: Gateway WebSocket port (default: `18789`)
- `--tls`: Use TLS for the gateway connection
- `--tls-fingerprint <sha256>`: Expected TLS certificate fingerprint (sha256)
- `--node-id <id>`: Override node id (clears pairing token)
- `--display-name <name>`: Override the node display name

## Service (background)

Install a headless node host as a user service.

```bash
zoo-bot node install --host <gateway-host> --port 18789
```

Options:

- `--host <host>`: Gateway WebSocket host (default: `127.0.0.1`)
- `--port <port>`: Gateway WebSocket port (default: `18789`)
- `--tls`: Use TLS for the gateway connection
- `--tls-fingerprint <sha256>`: Expected TLS certificate fingerprint (sha256)
- `--node-id <id>`: Override node id (clears pairing token)
- `--display-name <name>`: Override the node display name
- `--runtime <runtime>`: Service runtime (`node` or `bun`)
- `--force`: Reinstall/overwrite if already installed

Manage the service:

```bash
zoo-bot node status
zoo-bot node stop
zoo-bot node restart
zoo-bot node uninstall
```

Use `zoo-bot node run` for a foreground node host (no service).

Service commands accept `--json` for machine-readable output.

## Pairing

The first connection creates a pending device pairing request (`role: node`) on the Gateway.
Approve it via:

```bash
zoo-bot devices list
zoo-bot devices approve <requestId>
```

The node host stores its node id, token, display name, and gateway connection info in
`~/.zoo-bot/node.json`.

## Exec approvals

`system.run` is gated by local exec approvals:

- `~/.zoo-bot/exec-approvals.json`
- [Exec approvals](/tools/exec-approvals)
- `zoo-bot approvals --node <id|name|ip>` (edit from the Gateway)
