package net.bdew.technobauble

import net.bdew.lib.config.ConfigSection
import net.bdew.technobauble.blocks.charger.ConfigCharger
import net.bdew.technobauble.items.legs.ConfigLegs
import net.bdew.technobauble.items.magnet.ConfigMagnet
import net.bdew.technobauble.items.shield.ConfigShield
import net.minecraftforge.common.ForgeConfigSpec


object Config {
  private val commmonBuilder = new ForgeConfigSpec.Builder

  commmonBuilder.push("Blocks")

  val Charger: ConfigCharger = ConfigSection(commmonBuilder, "Charger", new ConfigCharger(commmonBuilder))

  commmonBuilder.pop().push("Items")

  val Shield: ConfigShield = ConfigSection(commmonBuilder, "Shield", new ConfigShield(commmonBuilder))
  val Magnet: ConfigMagnet = ConfigSection(commmonBuilder, "Magnet", new ConfigMagnet(commmonBuilder))
  val Legs: ConfigLegs = ConfigSection(commmonBuilder, "Legs", new ConfigLegs(commmonBuilder))

  commmonBuilder.pop()

  val COMMON: ForgeConfigSpec = commmonBuilder.build()
}
