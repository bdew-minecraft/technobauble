package net.bdew.technobauble.registries

import net.bdew.lib.managers.BlockManager
import net.bdew.technobauble.blocks.charger.{BlockCharger, TileCharger}
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.Material

object Blocks extends BlockManager(Items) {
  def machineProps: Properties = props(Material.STONE)
    .sound(SoundType.STONE)
    .strength(2, 8)

  val charger: Blocks.Def[BlockCharger, TileCharger, BlockItem] = define("charger", () => new BlockCharger)
    .withTE(new TileCharger(_, _, _))
    .withDefaultItem
    .register
}
