package net.bdew.technobauble.items.magnet

import net.bdew.lib.config.PowerConfig
import net.minecraftforge.common.ForgeConfigSpec

class ConfigMagnet(spec: ForgeConfigSpec.Builder) extends PowerConfig(spec, 500, 100000) {
  val attractRadius: () => Float = floatVal(spec, "AttractRadius",
    "Radius to pull items (meters)", 8f, 0, 64)

  val energyPerItem: () => Float = floatVal(spec, "EnergyPerItem",
    "FE used to pull 1 item", 50f, 0)
}