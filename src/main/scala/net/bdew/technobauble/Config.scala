package net.bdew.technobauble

import net.bdew.lib.config.ConfigSection
import net.bdew.technobauble.blocks.charger.ConfigCharger
import net.minecraftforge.common.ForgeConfigSpec


object Config {
  private val commmonBuilder = new ForgeConfigSpec.Builder

  val Charger: ConfigCharger = ConfigSection(commmonBuilder, "Charger", new ConfigCharger(commmonBuilder))

  val COMMON: ForgeConfigSpec = commmonBuilder.build()
}
