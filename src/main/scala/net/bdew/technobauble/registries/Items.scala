package net.bdew.technobauble.registries

import net.bdew.lib.managers.ItemManager
import net.minecraft.item.{Item, ItemGroup, ItemStack}

object CreativeTab extends ItemGroup("compacter") {
  override def makeIcon(): ItemStack = ???
}

object Items extends ItemManager(CreativeTab) {
  def nonStackable: Item.Properties = props.stacksTo(1)
}
