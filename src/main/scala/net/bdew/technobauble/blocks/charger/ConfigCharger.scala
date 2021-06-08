package net.bdew.technobauble.blocks.charger

import net.bdew.lib.config.PowerConfig
import net.minecraftforge.common.ForgeConfigSpec

class ConfigCharger(spec: ForgeConfigSpec.Builder) extends PowerConfig(spec, 50000, 1000000) {
  val transferLoss: () => Float = floatVal(spec, "TransferLoss", "Energy loss ratio when charging", 0.25f, 0f, 1f)
}