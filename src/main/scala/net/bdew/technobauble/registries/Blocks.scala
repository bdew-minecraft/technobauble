package net.bdew.technobauble.registries

import net.bdew.lib.managers.BlockManager
import net.bdew.technobauble.blocks.charger.{BlockCharger, TileCharger}
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.BlockItem

object Blocks extends BlockManager(Items) {
  def machineProps: Properties = props(Material.STONE)
    .sound(SoundType.STONE)
    .strength(2, 8)

  val charger: Blocks.Def[BlockCharger, TileCharger, BlockItem] = define("charger", () => new BlockCharger)
    .withTE(new TileCharger(_))
    .withDefaultItem
    .register
}
