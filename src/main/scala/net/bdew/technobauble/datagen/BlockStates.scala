package net.bdew.technobauble.datagen

import net.bdew.lib.datagen.BlockStateGenerator
import net.bdew.technobauble.Technobauble
import net.bdew.technobauble.registries.Blocks
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStates(gen: DataGenerator, efh: ExistingFileHelper) extends BlockStateGenerator(gen, Technobauble.ModId, efh) {
  override def registerStatesAndModels(): Unit = {
    Blocks.all.foreach(x => makeBlock(x.get()))
  }
}
