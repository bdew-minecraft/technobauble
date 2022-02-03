package net.bdew.technobauble.items.backpack

import net.bdew.lib.Text
import net.bdew.lib.inventory.StackInventory
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.`type`.capability.ICurio

class CurioBackpack(stack: ItemStack, item: ItemBackpack) extends ICurio with MenuProvider {
  def inventory() = new StackInventory(stack, 64)

  override def getStack: ItemStack = stack

  override def getDisplayName: Component = Text.translate("item.technobauble.backpack")

  override def createMenu(id: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu =
    new ContainerBackpack(inventory(), playerInventory, id)
}