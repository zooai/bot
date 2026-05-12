// Narrow plugin-sdk surface for the bundled team extension.
// Keep this list additive and scoped to symbols used under extensions/team.

export type {
  BotPluginApi,
  ZooBotPluginHttpRouteHandler,
} from "../plugins/types.js";
export type { BotConfig } from "../config/config.js";

export { emptyPluginConfigSchema } from "../plugins/config-schema.js";
export { resolvePreferredZooBotTmpDir } from "../infra/tmp-bot-dir.js";
