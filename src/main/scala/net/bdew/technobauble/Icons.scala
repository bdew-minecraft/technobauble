package net.bdew.technobauble

import net.bdew.lib.Client
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus

@Mod.EventBusSubscriber(value = Array(Dist.CLIENT), modid = Technobauble.ModId, bus = Bus.MOD)
object Icons {
  @SubscribeEvent
  def preTextureStitch(ev: TextureStitchEvent.Pre): Unit = {
    if (ev.getMap.location() == Client.blocksAtlas) {
      ev.addSprite(new ResourceLocation(Technobauble.ModId, "slot/legs"))
      ev.addSprite(new ResourceLocation(Technobauble.ModId, "slot/gadget"))
    }
  }
}
