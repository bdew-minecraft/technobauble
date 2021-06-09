package net.bdew.technobauble.datagen

import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

object DataGeneration {
  def onGatherData(ev: GatherDataEvent): Unit = {
    val dataGenerator = ev.getGenerator
    val efh = ev.getExistingFileHelper
    dataGenerator.addProvider(new LootTables(dataGenerator))
    dataGenerator.addProvider(new BlockStates(dataGenerator, efh))
  }
}
