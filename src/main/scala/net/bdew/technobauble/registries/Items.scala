package net.bdew.technobauble.registries

import net.bdew.lib.managers.ItemManager
import net.bdew.technobauble.items.backpack.ItemBackpack
import net.bdew.technobauble.items.legs.ItemLegs
import net.bdew.technobauble.items.magnet.ItemMagnet
import net.bdew.technobauble.items.receiver.ItemReceiver
import net.bdew.technobauble.items.shield.ItemShield
import net.minecraft.world.item.{CreativeModeTab, Item, ItemStack}
import net.minecraftforge.registries.RegistryObject

object CreativeTab extends CreativeModeTab("technobauble") {
  override def makeIcon(): ItemStack = new ItemStack(Items.fluxCore.get())
}

object Items extends ItemManager(CreativeTab) {
  def nonStackable: Item.Properties = props.stacksTo(1)

  val fluxCrystal: RegistryObject[Item] = simple("flux_crystal", props)
  val fluxCore: RegistryObject[Item] = simple("flux_core", props)

  val receiver: RegistryObject[ItemReceiver] = register("receiver", () => new ItemReceiver)
  val backpack: RegistryObject[ItemBackpack] = register("backpack", () => new ItemBackpack)
  val shield: RegistryObject[ItemShield] = register("shield", () => new ItemShield)
  val magnet: RegistryObject[ItemMagnet] = register("magnet", () => new ItemMagnet)
  val legs: RegistryObject[ItemLegs] = register("legs", () => new ItemLegs)
}
