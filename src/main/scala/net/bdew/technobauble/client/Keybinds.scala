package net.bdew.technobauble.client

import com.mojang.blaze3d.platform.InputConstants
import net.bdew.technobauble.Technobauble
import net.bdew.technobauble.network.{ActivateKind, MsgClientActivate, NetworkHandler}
import net.minecraft.client.KeyMapping
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.settings.{KeyConflictContext, KeyModifier}
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus
import org.lwjgl.glfw.GLFW

@Mod.EventBusSubscriber(value = Array(Dist.CLIENT), modid = Technobauble.ModId, bus = Bus.FORGE)
object Keybinds {
  val openBackpack = new KeyMapping(
    "technobauble.key.backpack",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_E),
    "technobauble.name"
  )

  val toggleMagnet = new KeyMapping(
    "technobauble.key.magnet",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_M),
    "technobauble.name"
  )

  val toggleRun = new KeyMapping(
    "technobauble.key.run",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_R),
    "technobauble.name"
  )

  val toggleJump = new KeyMapping(
    "technobauble.key.jump",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_J),
    "technobauble.name"
  )

  @SubscribeEvent
  def onKeyInput(event: InputEvent.Key): Unit = {
    if (openBackpack.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.BACKPACK))
    if (toggleMagnet.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.TOGGLE_MAGNET))
    if (toggleRun.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.TOGGLE_RUN))
    if (toggleJump.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.TOGGLE_JUMP))
  }
}
