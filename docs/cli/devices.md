---
summary: "CLI reference for `zoo-bot devices` (device pairing + token rotation/revocation)"
read_when:
  - You are approving device pairing requests
  - You need to rotate or revoke device tokens
title: "devices"
---

# `zoo-bot devices`

Manage device pairing requests and device-scoped tokens.

## Commands

### `zoo-bot devices list`

List pending pairing requests and paired devices.

```
zoo-bot devices list
zoo-bot devices list --json
```

### `zoo-bot devices remove <deviceId>`

Remove one paired device entry.

```
zoo-bot devices remove <deviceId>
zoo-bot devices remove <deviceId> --json
```

### `zoo-bot devices clear --yes [--pending]`

Clear paired devices in bulk.

```
zoo-bot devices clear --yes
zoo-bot devices clear --yes --pending
zoo-bot devices clear --yes --pending --json
```

### `zoo-bot devices approve [requestId] [--latest]`

Approve a pending device pairing request. If `requestId` is omitted, Bot
automatically approves the most recent pending request.

```
zoo-bot devices approve
zoo-bot devices approve <requestId>
zoo-bot devices approve --latest
```

### `zoo-bot devices reject <requestId>`

Reject a pending device pairing request.

```
zoo-bot devices reject <requestId>
```

### `zoo-bot devices rotate --device <id> --role <role> [--scope <scope...>]`

Rotate a device token for a specific role (optionally updating scopes).

```
zoo-bot devices rotate --device <deviceId> --role operator --scope operator.read --scope operator.write
```

### `zoo-bot devices revoke --device <id> --role <role>`

Revoke a device token for a specific role.

```
zoo-bot devices revoke --device <deviceId> --role node
```

## Common options

- `--url <url>`: Gateway WebSocket URL (defaults to `gateway.remote.url` when configured).
- `--token <token>`: Gateway token (if required).
- `--password <password>`: Gateway password (password auth).
- `--timeout <ms>`: RPC timeout.
- `--json`: JSON output (recommended for scripting).

Note: when you set `--url`, the CLI does not fall back to config or environment credentials.
Pass `--token` or `--password` explicitly. Missing explicit credentials is an error.

## Notes

- Token rotation returns a new token (sensitive). Treat it like a secret.
- These commands require `operator.pairing` (or `operator.admin`) scope.
- `devices clear` is intentionally gated by `--yes`.
- If pairing scope is unavailable on local loopback (and no explicit `--url` is passed), list/approve can use a local pairing fallback.
