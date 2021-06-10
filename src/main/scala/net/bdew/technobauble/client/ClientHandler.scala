package net.bdew.technobauble.client

import net.bdew.technobauble.Technobauble
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Mod.EventBusSubscriber(value = Array(Dist.CLIENT), modid = Technobauble.ModId, bus = Bus.MOD)
object ClientHandler {
  @SubscribeEvent
  def clientSetup(ev: FMLClientSetupEvent): Unit = {
    ev.enqueueWork(new Runnable {
      override def run(): Unit = {
        Keybinds.init()
      }
    })
  }
}
