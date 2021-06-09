package net.bdew.technobauble.registries

import net.bdew.lib.managers.ItemManager
import net.bdew.technobauble.items.receiver.ItemReceiver
import net.minecraft.item.{Item, ItemGroup, ItemStack}
import net.minecraftforge.fml.RegistryObject

object CreativeTab extends ItemGroup("compacter") {
  override def makeIcon(): ItemStack = new ItemStack(Items.receiver.get())
}

object Items extends ItemManager(CreativeTab) {
  def nonStackable: Item.Properties = props.stacksTo(1)

  val receiver: RegistryObject[ItemReceiver] = register("receiver", () => new ItemReceiver)
}
