package net.bdew.technobauble.datagen

import net.bdew.lib.datagen.LootTableSimpleGenerator
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.minecraftforge.data.event.GatherDataEvent

object DataGeneration {
  def onGatherData(ev: GatherDataEvent): Unit = {
    val dataGenerator = ev.getGenerator
    val efh = ev.getExistingFileHelper
    dataGenerator.addProvider(ev.includeServer, new LootTableSimpleGenerator(_, LootContextParamSets.BLOCK, () => new BlockLootTablesSubProvider))
    dataGenerator.addProvider(ev.includeClient, new BlockStates(_, efh))
  }
}
