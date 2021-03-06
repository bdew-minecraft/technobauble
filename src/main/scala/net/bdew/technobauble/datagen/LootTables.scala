package net.bdew.technobauble.datagen

import net.bdew.lib.datagen.LootTableGenerator
import net.bdew.technobauble.Technobauble
import net.bdew.technobauble.registries.Blocks
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable

class LootTables(gen: DataGenerator) extends LootTableGenerator(gen, Technobauble.ModId) {
  override def makeTables(): Map[ResourceLocation, LootTable] = {
    Blocks.all.map(blockReg => {
      val block = blockReg.get()
      makeBlockEntry(block, makeSimpleDropTable(block))
    }).toMap
  }
}
