package net.bdew.technobauble.registries

import net.bdew.lib.managers.BlockManager
import net.bdew.technobauble.blocks.charger.{BlockCharger, TileCharger}
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material

object Blocks extends BlockManager(Items) {
  def machineProps: Properties = props(Material.STONE)
    .sound(SoundType.STONE)
    .strength(2, 8)

  define("charger", () => new BlockCharger)
    .withTE(new TileCharger(_))
    .register
}
