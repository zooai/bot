---
summary: "Uninstall ZooBot completely (CLI, service, state, workspace)"
read_when:
  - You want to remove ZooBot from a machine
  - The gateway service is still running after uninstall
title: "Uninstall"
---

# Uninstall

Two paths:

- **Easy path** if `zoo-bot` is still installed.
- **Manual service removal** if the CLI is gone but the service is still running.

## Easy path (CLI still installed)

Recommended: use the built-in uninstaller:

```bash
zoo-bot uninstall
```

Non-interactive (automation / npx):

```bash
zoo-bot uninstall --all --yes --non-interactive
npx -y zoo-bot uninstall --all --yes --non-interactive
```

Manual steps (same result):

1. Stop the gateway service:

```bash
zoo-bot gateway stop
```

2. Uninstall the gateway service (launchd/systemd/schtasks):

```bash
zoo-bot gateway uninstall
```

3. Delete state + config:

```bash
rm -rf "${BOT_STATE_DIR:-$HOME/.zoo-bot}"
```

If you set `BOT_CONFIG_PATH` to a custom location outside the state dir, delete that file too.

4. Delete your workspace (optional, removes agent files):

```bash
rm -rf ~/.zoo-bot/workspace
```

5. Remove the CLI install (pick the one you used):

```bash
npm rm -g zoo-bot
pnpm remove -g zoo-bot
bun remove -g zoo-bot
```

6. If you installed the macOS app:

```bash
rm -rf /Applications/ZooBot.app
```

Notes:

- If you used profiles (`--profile` / `BOT_PROFILE`), repeat step 3 for each state dir (defaults are `~/.zoo-bot-<profile>`).
- In remote mode, the state dir lives on the **gateway host**, so run steps 1-4 there too.

## Manual service removal (CLI not installed)

Use this if the gateway service keeps running but `zoo-bot` is missing.

### macOS (launchd)

Default label is `ai.zoo.bot.gateway` (or `ai.zoo.bot.<profile>`; legacy `com.zoo-bot.*` may still exist):

```bash
launchctl bootout gui/$UID/ai.zoo.bot.gateway
rm -f ~/Library/LaunchAgents/ai.zoo.bot.gateway.plist
```

If you used a profile, replace the label and plist name with `ai.zoo.bot.<profile>`. Remove any legacy `com.zoo-bot.*` plists if present.

### Linux (systemd user unit)

Default unit name is `zoo-bot-gateway.service` (or `zoo-bot-gateway-<profile>.service`):

```bash
systemctl --user disable --now zoo-bot-gateway.service
rm -f ~/.config/systemd/user/zoo-bot-gateway.service
systemctl --user daemon-reload
```

### Windows (Scheduled Task)

Default task name is `ZooBot Gateway` (or `ZooBot Gateway (<profile>)`).
The task script lives under your state dir.

```powershell
schtasks /Delete /F /TN "ZooBot Gateway"
Remove-Item -Force "$env:USERPROFILE\.zoo-bot\gateway.cmd"
```

If you used a profile, delete the matching task name and `~\.zoo-bot-<profile>\gateway.cmd`.

## Normal install vs source checkout

### Normal install (install.sh / npm / pnpm / bun)

If you used `https://zoo-bot.ai/install.sh` or `install.ps1`, the CLI was installed with `npm install -g zoo-bot@latest`.
Remove it with `npm rm -g zoo-bot` (or `pnpm remove -g` / `bun remove -g` if you installed that way).

### Source checkout (git clone)

If you run from a repo checkout (`git clone` + `zoo-bot ...` / `bun run zoo-bot ...`):

1. Uninstall the gateway service **before** deleting the repo (use the easy path above or manual service removal).
2. Delete the repo directory.
3. Remove state + workspace as shown above.
