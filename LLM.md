# Zoo Bot — Architecture & Context

Zoo Bot is the Zoo Industries fork of `@hanzo/bot`, a multi-channel AI
messaging gateway (TypeScript ESM). It routes messages between 50+ messaging
platforms and AI models/agents through a composable plugin architecture with
a WebSocket + HTTP server core.

- npm: `@zooai/bot`
- bin: `zoo-bot`
- repo: github.com/zooai/bot
- container: ghcr.io/zooai/bot:latest
- upstream: github.com/hanzoai/bot — we track this branch and rebrand the surface

## Surface Rebrand

This is a thin rebrand fork. Internal modules, plugin SDK exports, and
extensions still use the `@hanzo/*` namespace where they share code with
upstream — the goal is to keep the diff vs. upstream small. Only the
user-facing surface is rebranded:

- Package name: `@zooai/bot`
- CLI binary: `zoo-bot`
- Default API base URL: `https://api.zoo.network`
- Container image: `ghcr.io/zooai/bot`
- Config path: `~/.zoo/bot/node.json` (falls back to `~/.hanzo/bot/` for
  upstream compat if the zoo path does not exist)

## Key Architecture Layers

1. **CLI** (`src/cli/`) — Command registry, arg parsing, `bot gateway run`,
   `bot agent`, `bot channels`
2. **Gateway** (`src/gateway/`) — WebSocket + HTTP server, auth, billing,
   marketplace, channels
3. **Channels** (`src/channels/`, `src/discord/`, `src/slack/`,
   `src/telegram/`, etc.) — Platform adapters
4. **Agents** (`src/agents/`) — ACP-based agent spawning, model selection,
   auth profiles
5. **Extensions** (`extensions/`) — 50+ channel/feature plugins as workspace
   packages

## Gateway Server (`src/gateway/`)

- `server.impl.ts` — Main initialization and lifecycle
- `server-http.ts` — HTTP handler chain
- `server-ws.ts` — WebSocket connection management
- `billing/billing-gate.ts` — Pre-request billing check (fail-closed in prod)
- `billing/usage-reporter.ts` — Async usage reporting
- `marketplace-http.ts` — P2P inference marketplace

## Model Defaults

- `DEFAULT_PROVIDER = "zoo"` (was "hanzo" upstream)
- `DEFAULT_MODEL = "claude-sonnet-4-6"`
- Tier-aware routing: free → claude-sonnet-4-6, paid → zen4-pro
- Auth profiles: multi-key round-robin with cooldown recovery

## Build

- pnpm + tsdown (bundle) + tsc (types) + oxfmt (format) + oxlint (lint)
- Node 22+, Bun supported for scripts/dev/tests
- Output: `dist/index.js`
- Image: `ghcr.io/zooai/bot:latest`

## Git Remotes

- `origin` = `ssh://github.com/zooai/bot` (zoo fork — PRs here)
- `upstream` = `https://github.com/hanzoai/bot` (track for syncs)

## Rules for AI Assistants

1. Keep diff vs. upstream small — only change user-facing surface
2. Internal `@hanzo/*` imports are OK (they're upstream packages)
3. Update LLM.md with significant discoveries
4. Never commit symlinked files (CLAUDE.md, AGENTS.md, etc.)
