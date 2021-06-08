package net.bdew.technobauble

import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig

@Mod(Techobauble.ModId)
object Techobauble {
  final val ModId = "technobauble"

  ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON)

}
