package net.bdew.technobauble.items.shield

import net.bdew.lib.config.PowerConfig
import net.minecraftforge.common.ForgeConfigSpec

class ConfigShield(spec: ForgeConfigSpec.Builder) extends PowerConfig(spec, 20000, 5000000) {
  val damageAbsorbCost: () => Float = floatVal(spec, "DamageAbsorbCost",
    "FE used to absorb 1 point of damange", 10000f, 0)
}