package ai.zoo.bot.android.node

import ai.zoo.bot.android.protocol.ZooBotCalendarCommand
import ai.zoo.bot.android.protocol.ZooBotCanvasA2UICommand
import ai.zoo.bot.android.protocol.ZooBotCanvasCommand
import ai.zoo.bot.android.protocol.ZooBotCameraCommand
import ai.zoo.bot.android.protocol.ZooBotCapability
import ai.zoo.bot.android.protocol.ZooBotContactsCommand
import ai.zoo.bot.android.protocol.ZooBotDeviceCommand
import ai.zoo.bot.android.protocol.ZooBotLocationCommand
import ai.zoo.bot.android.protocol.ZooBotMotionCommand
import ai.zoo.bot.android.protocol.ZooBotNotificationsCommand
import ai.zoo.bot.android.protocol.ZooBotPhotosCommand
import ai.zoo.bot.android.protocol.ZooBotScreenCommand
import ai.zoo.bot.android.protocol.ZooBotSmsCommand
import ai.zoo.bot.android.protocol.ZooBotSystemCommand

data class NodeRuntimeFlags(
  val cameraEnabled: Boolean,
  val locationEnabled: Boolean,
  val smsAvailable: Boolean,
  val voiceWakeEnabled: Boolean,
  val motionActivityAvailable: Boolean,
  val motionPedometerAvailable: Boolean,
  val debugBuild: Boolean,
)

enum class InvokeCommandAvailability {
  Always,
  CameraEnabled,
  LocationEnabled,
  SmsAvailable,
  MotionActivityAvailable,
  MotionPedometerAvailable,
  DebugBuild,
}

enum class NodeCapabilityAvailability {
  Always,
  CameraEnabled,
  LocationEnabled,
  SmsAvailable,
  VoiceWakeEnabled,
  MotionAvailable,
}

data class NodeCapabilitySpec(
  val name: String,
  val availability: NodeCapabilityAvailability = NodeCapabilityAvailability.Always,
)

data class InvokeCommandSpec(
  val name: String,
  val requiresForeground: Boolean = false,
  val availability: InvokeCommandAvailability = InvokeCommandAvailability.Always,
)

object InvokeCommandRegistry {
  val capabilityManifest: List<NodeCapabilitySpec> =
    listOf(
      NodeCapabilitySpec(name = ZooBotCapability.Canvas.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.Screen.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.Device.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.Notifications.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.System.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.AppUpdate.rawValue),
      NodeCapabilitySpec(
        name = ZooBotCapability.Camera.rawValue,
        availability = NodeCapabilityAvailability.CameraEnabled,
      ),
      NodeCapabilitySpec(
        name = ZooBotCapability.Sms.rawValue,
        availability = NodeCapabilityAvailability.SmsAvailable,
      ),
      NodeCapabilitySpec(
        name = ZooBotCapability.VoiceWake.rawValue,
        availability = NodeCapabilityAvailability.VoiceWakeEnabled,
      ),
      NodeCapabilitySpec(
        name = ZooBotCapability.Location.rawValue,
        availability = NodeCapabilityAvailability.LocationEnabled,
      ),
      NodeCapabilitySpec(name = ZooBotCapability.Photos.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.Contacts.rawValue),
      NodeCapabilitySpec(name = ZooBotCapability.Calendar.rawValue),
      NodeCapabilitySpec(
        name = ZooBotCapability.Motion.rawValue,
        availability = NodeCapabilityAvailability.MotionAvailable,
      ),
    )

  val all: List<InvokeCommandSpec> =
    listOf(
      InvokeCommandSpec(
        name = ZooBotCanvasCommand.Present.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasCommand.Hide.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasCommand.Navigate.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasCommand.Eval.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasCommand.Snapshot.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasA2UICommand.Push.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasA2UICommand.PushJSONL.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotCanvasA2UICommand.Reset.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotScreenCommand.Record.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = ZooBotSystemCommand.Notify.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotCameraCommand.List.rawValue,
        requiresForeground = true,
        availability = InvokeCommandAvailability.CameraEnabled,
      ),
      InvokeCommandSpec(
        name = ZooBotCameraCommand.Snap.rawValue,
        requiresForeground = true,
        availability = InvokeCommandAvailability.CameraEnabled,
      ),
      InvokeCommandSpec(
        name = ZooBotCameraCommand.Clip.rawValue,
        requiresForeground = true,
        availability = InvokeCommandAvailability.CameraEnabled,
      ),
      InvokeCommandSpec(
        name = ZooBotLocationCommand.Get.rawValue,
        availability = InvokeCommandAvailability.LocationEnabled,
      ),
      InvokeCommandSpec(
        name = ZooBotDeviceCommand.Status.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotDeviceCommand.Info.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotDeviceCommand.Permissions.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotDeviceCommand.Health.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotNotificationsCommand.List.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotNotificationsCommand.Actions.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotPhotosCommand.Latest.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotContactsCommand.Search.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotContactsCommand.Add.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotCalendarCommand.Events.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotCalendarCommand.Add.rawValue,
      ),
      InvokeCommandSpec(
        name = ZooBotMotionCommand.Activity.rawValue,
        availability = InvokeCommandAvailability.MotionActivityAvailable,
      ),
      InvokeCommandSpec(
        name = ZooBotMotionCommand.Pedometer.rawValue,
        availability = InvokeCommandAvailability.MotionPedometerAvailable,
      ),
      InvokeCommandSpec(
        name = ZooBotSmsCommand.Send.rawValue,
        availability = InvokeCommandAvailability.SmsAvailable,
      ),
      InvokeCommandSpec(
        name = "debug.logs",
        availability = InvokeCommandAvailability.DebugBuild,
      ),
      InvokeCommandSpec(
        name = "debug.ed25519",
        availability = InvokeCommandAvailability.DebugBuild,
      ),
      InvokeCommandSpec(name = "app.update"),
    )

  private val byNameInternal: Map<String, InvokeCommandSpec> = all.associateBy { it.name }

  fun find(command: String): InvokeCommandSpec? = byNameInternal[command]

  fun advertisedCapabilities(flags: NodeRuntimeFlags): List<String> {
    return capabilityManifest
      .filter { spec ->
        when (spec.availability) {
          NodeCapabilityAvailability.Always -> true
          NodeCapabilityAvailability.CameraEnabled -> flags.cameraEnabled
          NodeCapabilityAvailability.LocationEnabled -> flags.locationEnabled
          NodeCapabilityAvailability.SmsAvailable -> flags.smsAvailable
          NodeCapabilityAvailability.VoiceWakeEnabled -> flags.voiceWakeEnabled
          NodeCapabilityAvailability.MotionAvailable -> flags.motionActivityAvailable || flags.motionPedometerAvailable
        }
      }
      .map { it.name }
  }

  fun advertisedCommands(flags: NodeRuntimeFlags): List<String> {
    return all
      .filter { spec ->
        when (spec.availability) {
          InvokeCommandAvailability.Always -> true
          InvokeCommandAvailability.CameraEnabled -> flags.cameraEnabled
          InvokeCommandAvailability.LocationEnabled -> flags.locationEnabled
          InvokeCommandAvailability.SmsAvailable -> flags.smsAvailable
          InvokeCommandAvailability.MotionActivityAvailable -> flags.motionActivityAvailable
          InvokeCommandAvailability.MotionPedometerAvailable -> flags.motionPedometerAvailable
          InvokeCommandAvailability.DebugBuild -> flags.debugBuild
        }
      }
      .map { it.name }
  }
}
