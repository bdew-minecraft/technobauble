package net.bdew.technobauble.datagen

import net.bdew.lib.datagen.BlockStateGenerator
import net.bdew.technobauble.Technobauble
import net.bdew.technobauble.registries.Blocks
import net.minecraft.data.PackOutput
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStates(out: PackOutput, efh: ExistingFileHelper) extends BlockStateGenerator(out, Technobauble.ModId, efh) {
  override def registerStatesAndModels(): Unit = {
    Blocks.all.foreach(x => makeBlock(x.get()))
  }
}
