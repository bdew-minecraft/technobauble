package net.bdew.technobauble.items.backpack

import net.bdew.lib.Text
import net.bdew.lib.inventory.StackInventory
import net.minecraft.entity.player.{PlayerEntity, PlayerInventory}
import net.minecraft.inventory.container.{Container, INamedContainerProvider}
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import top.theillusivec4.curios.api.`type`.capability.ICurio

class CurioBackpack(stack: ItemStack, item: ItemBackpack) extends ICurio with INamedContainerProvider {
  def inventory() = new StackInventory(stack, 64)

  override def getDisplayName: ITextComponent = Text.translate("item.technobauble.backpack")

  override def createMenu(id: Int, playerInventory: PlayerInventory, player: PlayerEntity): Container =
    new ContainerBackpack(inventory(), playerInventory, id)
}