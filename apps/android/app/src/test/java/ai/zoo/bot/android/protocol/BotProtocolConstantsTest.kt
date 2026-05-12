package ai.zoo.bot.android.protocol

import org.junit.Assert.assertEquals
import org.junit.Test

class ZooBotProtocolConstantsTest {
  @Test
  fun canvasCommandsUseStableStrings() {
    assertEquals("canvas.present", ZooBotCanvasCommand.Present.rawValue)
    assertEquals("canvas.hide", ZooBotCanvasCommand.Hide.rawValue)
    assertEquals("canvas.navigate", ZooBotCanvasCommand.Navigate.rawValue)
    assertEquals("canvas.eval", ZooBotCanvasCommand.Eval.rawValue)
    assertEquals("canvas.snapshot", ZooBotCanvasCommand.Snapshot.rawValue)
  }

  @Test
  fun a2uiCommandsUseStableStrings() {
    assertEquals("canvas.a2ui.push", ZooBotCanvasA2UICommand.Push.rawValue)
    assertEquals("canvas.a2ui.pushJSONL", ZooBotCanvasA2UICommand.PushJSONL.rawValue)
    assertEquals("canvas.a2ui.reset", ZooBotCanvasA2UICommand.Reset.rawValue)
  }

  @Test
  fun capabilitiesUseStableStrings() {
    assertEquals("canvas", ZooBotCapability.Canvas.rawValue)
    assertEquals("camera", ZooBotCapability.Camera.rawValue)
    assertEquals("screen", ZooBotCapability.Screen.rawValue)
    assertEquals("voiceWake", ZooBotCapability.VoiceWake.rawValue)
    assertEquals("location", ZooBotCapability.Location.rawValue)
    assertEquals("sms", ZooBotCapability.Sms.rawValue)
    assertEquals("device", ZooBotCapability.Device.rawValue)
    assertEquals("notifications", ZooBotCapability.Notifications.rawValue)
    assertEquals("system", ZooBotCapability.System.rawValue)
    assertEquals("appUpdate", ZooBotCapability.AppUpdate.rawValue)
    assertEquals("photos", ZooBotCapability.Photos.rawValue)
    assertEquals("contacts", ZooBotCapability.Contacts.rawValue)
    assertEquals("calendar", ZooBotCapability.Calendar.rawValue)
    assertEquals("motion", ZooBotCapability.Motion.rawValue)
  }

  @Test
  fun cameraCommandsUseStableStrings() {
    assertEquals("camera.list", ZooBotCameraCommand.List.rawValue)
    assertEquals("camera.snap", ZooBotCameraCommand.Snap.rawValue)
    assertEquals("camera.clip", ZooBotCameraCommand.Clip.rawValue)
  }

  @Test
  fun screenCommandsUseStableStrings() {
    assertEquals("screen.record", ZooBotScreenCommand.Record.rawValue)
  }

  @Test
  fun notificationsCommandsUseStableStrings() {
    assertEquals("notifications.list", ZooBotNotificationsCommand.List.rawValue)
    assertEquals("notifications.actions", ZooBotNotificationsCommand.Actions.rawValue)
  }

  @Test
  fun deviceCommandsUseStableStrings() {
    assertEquals("device.status", ZooBotDeviceCommand.Status.rawValue)
    assertEquals("device.info", ZooBotDeviceCommand.Info.rawValue)
    assertEquals("device.permissions", ZooBotDeviceCommand.Permissions.rawValue)
    assertEquals("device.health", ZooBotDeviceCommand.Health.rawValue)
  }

  @Test
  fun systemCommandsUseStableStrings() {
    assertEquals("system.notify", ZooBotSystemCommand.Notify.rawValue)
  }

  @Test
  fun photosCommandsUseStableStrings() {
    assertEquals("photos.latest", ZooBotPhotosCommand.Latest.rawValue)
  }

  @Test
  fun contactsCommandsUseStableStrings() {
    assertEquals("contacts.search", ZooBotContactsCommand.Search.rawValue)
    assertEquals("contacts.add", ZooBotContactsCommand.Add.rawValue)
  }

  @Test
  fun calendarCommandsUseStableStrings() {
    assertEquals("calendar.events", ZooBotCalendarCommand.Events.rawValue)
    assertEquals("calendar.add", ZooBotCalendarCommand.Add.rawValue)
  }

  @Test
  fun motionCommandsUseStableStrings() {
    assertEquals("motion.activity", ZooBotMotionCommand.Activity.rawValue)
    assertEquals("motion.pedometer", ZooBotMotionCommand.Pedometer.rawValue)
  }
}
