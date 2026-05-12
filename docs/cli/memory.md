---
summary: "CLI reference for `zoo-bot memory` (status/index/search)"
read_when:
  - You want to index or search semantic memory
  - You’re debugging memory availability or indexing
title: "memory"
---

# `zoo-bot memory`

Manage semantic memory indexing and search.
Provided by the active memory plugin (default: `memory-core`; set `plugins.slots.memory = "none"` to disable).

Related:

- Memory concept: [Memory](/concepts/memory)
- Plugins: [Plugins](/tools/plugin)

## Examples

```bash
zoo-bot memory status
zoo-bot memory status --deep
zoo-bot memory status --deep --index
zoo-bot memory status --deep --index --verbose
zoo-bot memory index
zoo-bot memory index --verbose
zoo-bot memory search "release checklist"
zoo-bot memory search --query "release checklist"
zoo-bot memory status --agent main
zoo-bot memory index --agent main --verbose
```

## Options

Common:

- `--agent <id>`: scope to a single agent (default: all configured agents).
- `--verbose`: emit detailed logs during probes and indexing.

`memory search`:

- Query input: pass either positional `[query]` or `--query <text>`.
- If both are provided, `--query` wins.
- If neither is provided, the command exits with an error.

Notes:

- `memory status --deep` probes vector + embedding availability.
- `memory status --deep --index` runs a reindex if the store is dirty.
- `memory index --verbose` prints per-phase details (provider, model, sources, batch activity).
- `memory status` includes any extra paths configured via `memorySearch.extraPaths`.
- If effectively active memory remote API key fields are configured as SecretRefs, the command resolves those values from the active gateway snapshot. If gateway is unavailable, the command fails fast.
- Gateway version skew note: this command path requires a gateway that supports `secrets.resolve`; older gateways return an unknown-method error.
