package net.bdew.technobauble.client

import net.bdew.technobauble.Technobauble
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus

@Mod.EventBusSubscriber(value = Array(Dist.CLIENT), modid = Technobauble.ModId, bus = Bus.MOD)
object ClientHandler {
  @SubscribeEvent
  def registerKeybinds(ev: RegisterKeyMappingsEvent): Unit = {
    ev.register(Keybinds.openBackpack)
    ev.register(Keybinds.toggleMagnet)
    ev.register(Keybinds.toggleRun)
    ev.register(Keybinds.toggleJump)
  }
}
