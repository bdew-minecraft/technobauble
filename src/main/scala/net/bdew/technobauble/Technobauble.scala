package net.bdew.technobauble

import net.bdew.technobauble.datagen.DataGeneration
import net.bdew.technobauble.network.NetworkHandler
import net.bdew.technobauble.registries.{Blocks, Containers, Items}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.{InterModComms, ModLoadingContext}
import top.theillusivec4.curios.api.{CuriosApi, SlotTypeMessage, SlotTypePreset}

@Mod(Technobauble.ModId)
object Technobauble {
  final val ModId = "technobauble"

  ModLoadingContext.get.registerConfig(ModConfig.Type.COMMON, Config.COMMON)

  Items.init()
  Blocks.init()
  Containers.init()
  NetworkHandler.init()

  FMLJavaModLoadingContext.get.getModEventBus.addListener(this.enqueueIMC)
  FMLJavaModLoadingContext.get.getModEventBus.addListener(DataGeneration.onGatherData)

  MinecraftForge.EVENT_BUS.register(DamageHandler)

  private def enqueueIMC(event: InterModEnqueueEvent): Unit = {
    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () => SlotTypePreset.BACK.getMessageBuilder.build)
    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () => SlotTypePreset.CHARM.getMessageBuilder.build)
    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () => SlotTypePreset.BELT.getMessageBuilder.build)
    InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () => SlotTypePreset.BODY.getMessageBuilder.build)
  }
}
