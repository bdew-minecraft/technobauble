package net.bdew.technobauble.items.legs

import net.bdew.lib.config.PowerConfig
import net.minecraftforge.common.ForgeConfigSpec

class ConfigLegs(spec: ForgeConfigSpec.Builder) extends PowerConfig(spec, 500, 100000) {
  val movingEnergyCost: () => Float = floatVal(spec, "MovingEnergyCost",
    "FE/t used while moving", 100f, 0, 64)

  val jumpEnergyCost: () => Float = floatVal(spec, "JumpEnergyCost",
    "FE used per jump", 500f, 0)

  val jumpBoost: () => Float = floatVal(spec, "JumpBoost",
    "Added jump velocity", 0.25f, 0)

  val runBoost: () => Float = floatVal(spec, "RunBoost",
    "Added run velocity", 0.2f, 0)

  val fallAbsorbCost: () => Float = floatVal(spec, "FallAbsorbCost",
    "FE used to absorb fall damage", 1000f, 0)
}