// Narrow plugin-sdk surface for the bundled diffs plugin.
// Keep this list additive and scoped to symbols used under extensions/diffs.

export type { BotConfig } from "../config/config.js";
export { resolvePreferredZooBotTmpDir } from "../infra/tmp-bot-dir.js";
export type {
  AnyAgentTool,
  BotPluginApi,
  ZooBotPluginConfigSchema,
  PluginLogger,
} from "../plugins/types.js";
