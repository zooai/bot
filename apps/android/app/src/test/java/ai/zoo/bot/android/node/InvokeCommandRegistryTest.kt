package ai.zoo.bot.android.node

import ai.zoo.bot.android.protocol.ZooBotCalendarCommand
import ai.zoo.bot.android.protocol.ZooBotCameraCommand
import ai.zoo.bot.android.protocol.ZooBotCapability
import ai.zoo.bot.android.protocol.ZooBotContactsCommand
import ai.zoo.bot.android.protocol.ZooBotDeviceCommand
import ai.zoo.bot.android.protocol.ZooBotLocationCommand
import ai.zoo.bot.android.protocol.ZooBotMotionCommand
import ai.zoo.bot.android.protocol.ZooBotNotificationsCommand
import ai.zoo.bot.android.protocol.ZooBotPhotosCommand
import ai.zoo.bot.android.protocol.ZooBotSmsCommand
import ai.zoo.bot.android.protocol.ZooBotSystemCommand
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InvokeCommandRegistryTest {
  private val coreCapabilities =
    setOf(
      ZooBotCapability.Canvas.rawValue,
      ZooBotCapability.Screen.rawValue,
      ZooBotCapability.Device.rawValue,
      ZooBotCapability.Notifications.rawValue,
      ZooBotCapability.System.rawValue,
      ZooBotCapability.AppUpdate.rawValue,
      ZooBotCapability.Photos.rawValue,
      ZooBotCapability.Contacts.rawValue,
      ZooBotCapability.Calendar.rawValue,
    )

  private val optionalCapabilities =
    setOf(
      ZooBotCapability.Camera.rawValue,
      ZooBotCapability.Location.rawValue,
      ZooBotCapability.Sms.rawValue,
      ZooBotCapability.VoiceWake.rawValue,
      ZooBotCapability.Motion.rawValue,
    )

  private val coreCommands =
    setOf(
      ZooBotDeviceCommand.Status.rawValue,
      ZooBotDeviceCommand.Info.rawValue,
      ZooBotDeviceCommand.Permissions.rawValue,
      ZooBotDeviceCommand.Health.rawValue,
      ZooBotNotificationsCommand.List.rawValue,
      ZooBotNotificationsCommand.Actions.rawValue,
      ZooBotSystemCommand.Notify.rawValue,
      ZooBotPhotosCommand.Latest.rawValue,
      ZooBotContactsCommand.Search.rawValue,
      ZooBotContactsCommand.Add.rawValue,
      ZooBotCalendarCommand.Events.rawValue,
      ZooBotCalendarCommand.Add.rawValue,
      "app.update",
    )

  private val optionalCommands =
    setOf(
      ZooBotCameraCommand.Snap.rawValue,
      ZooBotCameraCommand.Clip.rawValue,
      ZooBotCameraCommand.List.rawValue,
      ZooBotLocationCommand.Get.rawValue,
      ZooBotMotionCommand.Activity.rawValue,
      ZooBotMotionCommand.Pedometer.rawValue,
      ZooBotSmsCommand.Send.rawValue,
    )

  private val debugCommands = setOf("debug.logs", "debug.ed25519")

  @Test
  fun advertisedCapabilities_respectsFeatureAvailability() {
    val capabilities = InvokeCommandRegistry.advertisedCapabilities(defaultFlags())

    assertContainsAll(capabilities, coreCapabilities)
    assertMissingAll(capabilities, optionalCapabilities)
  }

  @Test
  fun advertisedCapabilities_includesFeatureCapabilitiesWhenEnabled() {
    val capabilities =
      InvokeCommandRegistry.advertisedCapabilities(
        defaultFlags(
          cameraEnabled = true,
          locationEnabled = true,
          smsAvailable = true,
          voiceWakeEnabled = true,
          motionActivityAvailable = true,
          motionPedometerAvailable = true,
        ),
      )

    assertContainsAll(capabilities, coreCapabilities + optionalCapabilities)
  }

  @Test
  fun advertisedCommands_respectsFeatureAvailability() {
    val commands = InvokeCommandRegistry.advertisedCommands(defaultFlags())

    assertContainsAll(commands, coreCommands)
    assertMissingAll(commands, optionalCommands + debugCommands)
  }

  @Test
  fun advertisedCommands_includesFeatureCommandsWhenEnabled() {
    val commands =
      InvokeCommandRegistry.advertisedCommands(
        defaultFlags(
          cameraEnabled = true,
          locationEnabled = true,
          smsAvailable = true,
          motionActivityAvailable = true,
          motionPedometerAvailable = true,
          debugBuild = true,
        ),
      )

    assertContainsAll(commands, coreCommands + optionalCommands + debugCommands)
  }

  @Test
  fun advertisedCommands_onlyIncludesSupportedMotionCommands() {
    val commands =
      InvokeCommandRegistry.advertisedCommands(
        NodeRuntimeFlags(
          cameraEnabled = false,
          locationEnabled = false,
          smsAvailable = false,
          voiceWakeEnabled = false,
          motionActivityAvailable = true,
          motionPedometerAvailable = false,
          debugBuild = false,
        ),
      )

    assertTrue(commands.contains(ZooBotMotionCommand.Activity.rawValue))
    assertFalse(commands.contains(ZooBotMotionCommand.Pedometer.rawValue))
  }

  private fun defaultFlags(
    cameraEnabled: Boolean = false,
    locationEnabled: Boolean = false,
    smsAvailable: Boolean = false,
    voiceWakeEnabled: Boolean = false,
    motionActivityAvailable: Boolean = false,
    motionPedometerAvailable: Boolean = false,
    debugBuild: Boolean = false,
  ): NodeRuntimeFlags =
    NodeRuntimeFlags(
      cameraEnabled = cameraEnabled,
      locationEnabled = locationEnabled,
      smsAvailable = smsAvailable,
      voiceWakeEnabled = voiceWakeEnabled,
      motionActivityAvailable = motionActivityAvailable,
      motionPedometerAvailable = motionPedometerAvailable,
      debugBuild = debugBuild,
    )

  private fun assertContainsAll(actual: List<String>, expected: Set<String>) {
    expected.forEach { value -> assertTrue(actual.contains(value)) }
  }

  private fun assertMissingAll(actual: List<String>, forbidden: Set<String>) {
    forbidden.forEach { value -> assertFalse(actual.contains(value)) }
  }
}
