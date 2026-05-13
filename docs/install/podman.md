---
summary: "Run Bot in a rootless Podman container"
read_when:
  - You want a containerized gateway with Podman instead of Docker
title: "Podman"
---

# Podman

Run the Bot gateway in a **rootless** Podman container. Uses the same image as Docker (build from the repo [Dockerfile](https://github.com/zoo-bot/zoo-bot/blob/main/Dockerfile)).

## Requirements

- Podman (rootless)
- Sudo for one-time setup (create user, build image)

## Quick start

**1. One-time setup** (from repo root; creates user, builds image, installs launch script):

```bash
./setup-podman.sh
```

This also creates a minimal `~zoo-bot/.zoo-bot/zoo-bot.json` (sets `gateway.mode="local"`) so the gateway can start without running the wizard.

By default the container is **not** installed as a systemd service, you start it manually (see below). For a production-style setup with auto-start and restarts, install it as a systemd Quadlet user service instead:

```bash
./setup-podman.sh --quadlet
```

(Or set `BOT_PODMAN_QUADLET=1`; use `--container` to install only the container and launch script.)

**2. Start gateway** (manual, for quick smoke testing):

```bash
./scripts/run-zoo-bot-podman.sh launch
```

**3. Onboarding wizard** (e.g. to add channels or providers):

```bash
./scripts/run-zoo-bot-podman.sh launch setup
```

Then open `http://127.0.0.1:18789/` and use the token from `~zoo-bot/.zoo-bot/.env` (or the value printed by setup).

## Systemd (Quadlet, optional)

If you ran `./setup-podman.sh --quadlet` (or `BOT_PODMAN_QUADLET=1`), a [Podman Quadlet](https://docs.podman.io/en/latest/markdown/podman-systemd.unit.5.html) unit is installed so the gateway runs as a systemd user service for the zoo-bot user. The service is enabled and started at the end of setup.

- **Start:** `sudo systemctl --machine zoo-bot@ --user start zoo-bot.service`
- **Stop:** `sudo systemctl --machine zoo-bot@ --user stop zoo-bot.service`
- **Status:** `sudo systemctl --machine zoo-bot@ --user status zoo-bot.service`
- **Logs:** `sudo journalctl --machine zoo-bot@ --user -u zoo-bot.service -f`

The quadlet file lives at `~zoo-bot/.config/containers/systemd/zoo-bot.container`. To change ports or env, edit that file (or the `.env` it sources), then `sudo systemctl --machine zoo-bot@ --user daemon-reload` and restart the service. On boot, the service starts automatically if lingering is enabled for zoo-bot (setup does this when loginctl is available).

To add quadlet **after** an initial setup that did not use it, re-run: `./setup-podman.sh --quadlet`.

## The zoo-bot user (non-login)

`setup-podman.sh` creates a dedicated system user `zoo-bot`:

- **Shell:** `nologin` — no interactive login; reduces attack surface.
- **Home:** e.g. `/home/zoo-bot` — holds `~/.zoo-bot` (config, workspace) and the launch script `run-zoo-bot-podman.sh`.
- **Rootless Podman:** The user must have a **subuid** and **subgid** range. Many distros assign these automatically when the user is created. If setup prints a warning, add lines to `/etc/subuid` and `/etc/subgid`:

  ```text
  zoo-bot:100000:65536
  ```

  Then start the gateway as that user (e.g. from cron or systemd):

  ```bash
  sudo -u zoo-bot /home/zoo-bot/run-zoo-bot-podman.sh
  sudo -u zoo-bot /home/zoo-bot/run-zoo-bot-podman.sh setup
  ```

- **Config:** Only `zoo-bot` and root can access `/home/zoo-bot/.zoo-bot`. To edit config: use the Control UI once the gateway is running, or `sudo -u zoo-bot $EDITOR /home/zoo-bot/.zoo-bot/zoo-bot.json`.

## Environment and config

- **Token:** Stored in `~zoo-bot/.zoo-bot/.env` as `BOT_GATEWAY_TOKEN`. `setup-podman.sh` and `run-zoo-bot-podman.sh` generate it if missing (uses `openssl`, `python3`, or `od`).
- **Optional:** In that `.env` you can set provider keys (e.g. `GROQ_API_KEY`, `OLLAMA_API_KEY`) and other Bot env vars.
- **Host ports:** By default the script maps `18789` (gateway) and `18790` (bridge). Override the **host** port mapping with `BOT_PODMAN_GATEWAY_HOST_PORT` and `BOT_PODMAN_BRIDGE_HOST_PORT` when launching.
- **Gateway bind:** By default, `run-zoo-bot-podman.sh` starts the gateway with `--bind loopback` for safe local access. To expose on LAN, set `BOT_GATEWAY_BIND=lan` and configure `gateway.controlUi.allowedOrigins` (or explicitly enable host-header fallback) in `zoo-bot.json`.
- **Paths:** Host config and workspace default to `~zoo-bot/.zoo-bot` and `~zoo-bot/.zoo-bot/workspace`. Override the host paths used by the launch script with `BOT_CONFIG_DIR` and `BOT_WORKSPACE_DIR`.

## Useful commands

- **Logs:** With quadlet: `sudo journalctl --machine zoo-bot@ --user -u zoo-bot.service -f`. With script: `sudo -u zoo-bot podman logs -f zoo-bot`
- **Stop:** With quadlet: `sudo systemctl --machine zoo-bot@ --user stop zoo-bot.service`. With script: `sudo -u zoo-bot podman stop zoo-bot`
- **Start again:** With quadlet: `sudo systemctl --machine zoo-bot@ --user start zoo-bot.service`. With script: re-run the launch script or `podman start zoo-bot`
- **Remove container:** `sudo -u zoo-bot podman rm -f zoo-bot` — config and workspace on the host are kept

## Troubleshooting

- **Permission denied (EACCES) on config or auth-profiles:** The container defaults to `--userns=keep-id` and runs as the same uid/gid as the host user running the script. Ensure your host `BOT_CONFIG_DIR` and `BOT_WORKSPACE_DIR` are owned by that user.
- **Gateway start blocked (missing `gateway.mode=local`):** Ensure `~zoo-bot/.zoo-bot/zoo-bot.json` exists and sets `gateway.mode="local"`. `setup-podman.sh` creates this file if missing.
- **Rootless Podman fails for user zoo-bot:** Check `/etc/subuid` and `/etc/subgid` contain a line for `zoo-bot` (e.g. `zoo-bot:100000:65536`). Add it if missing and restart.
- **Container name in use:** The launch script uses `podman run --replace`, so the existing container is replaced when you start again. To clean up manually: `podman rm -f zoo-bot`.
- **Script not found when running as zoo-bot:** Ensure `setup-podman.sh` was run so that `run-zoo-bot-podman.sh` is copied to zoo-bot’s home (e.g. `/home/zoo-bot/run-zoo-bot-podman.sh`).
- **Quadlet service not found or fails to start:** Run `sudo systemctl --machine zoo-bot@ --user daemon-reload` after editing the `.container` file. Quadlet requires cgroups v2: `podman info --format '{{.Host.CgroupsVersion}}'` should show `2`.

## Optional: run as your own user

To run the gateway as your normal user (no dedicated zoo-bot user): build the image, create `~/.zoo-bot/.env` with `BOT_GATEWAY_TOKEN`, and run the container with `--userns=keep-id` and mounts to your `~/.zoo-bot`. The launch script is designed for the zoo-bot-user flow; for a single-user setup you can instead run the `podman run` command from the script manually, pointing config and workspace to your home. Recommended for most users: use `setup-podman.sh` and run as the zoo-bot user so config and process are isolated.
