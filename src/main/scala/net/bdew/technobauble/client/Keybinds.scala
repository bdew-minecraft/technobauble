package net.bdew.technobauble.client

import net.bdew.technobauble.network.{ActivateKind, MsgClientActivate, NetworkHandler}
import net.minecraft.client.settings.KeyBinding
import net.minecraft.client.util.InputMappings
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.settings.{KeyConflictContext, KeyModifier}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import org.lwjgl.glfw.GLFW

object Keybinds {
  val openBackpack = new KeyBinding(
    "technobauble.key.backpack",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_E),
    "technobauble.name"
  )

  val toggleMagnet = new KeyBinding(
    "technobauble.key.magnet",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_M),
    "technobauble.name"
  )

  val toggleRun = new KeyBinding(
    "technobauble.key.run",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_R),
    "technobauble.name"
  )

  val toggleJump = new KeyBinding(
    "technobauble.key.jump",
    KeyConflictContext.IN_GAME,
    KeyModifier.CONTROL,
    InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_J),
    "technobauble.name"
  )

  private def onKeyInput(event: InputEvent.KeyInputEvent): Unit = {
    if (openBackpack.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.BACKPACK))
    if (toggleMagnet.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.TOGGLE_MAGNET))
    if (toggleRun.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.TOGGLE_RUN))
    if (toggleJump.isDown) NetworkHandler.sendToServer(MsgClientActivate(ActivateKind.TOGGLE_JUMP))
  }

  def init(): Unit = {
    ClientRegistry.registerKeyBinding(openBackpack)
    ClientRegistry.registerKeyBinding(toggleMagnet)
    ClientRegistry.registerKeyBinding(toggleRun)
    ClientRegistry.registerKeyBinding(toggleJump)
    MinecraftForge.EVENT_BUS.addListener(this.onKeyInput)
  }
}
