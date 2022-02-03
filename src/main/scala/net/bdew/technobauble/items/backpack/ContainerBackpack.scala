package net.bdew.technobauble.items.backpack

import net.bdew.lib.container.BaseContainer
import net.bdew.technobauble.registries.Containers
import net.minecraft.world.Container
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{ClickType, Slot}

class ContainerBackpack(inv: Container, playerInventory: Inventory, id: Int)
  extends BaseContainer(inv, Containers.backpack.get(), id) {
  var lockSlot: Int = -1

  for (y <- 0 until 6; x <- 0 until 9) {
    this.addSlot(new Slot(
      inv,
      x + y * 9,
      8 + x * 18,
      18 + y * 18
    ))
  }

  override def clicked(slotNum: Int, button: Int, clickType: ClickType, player: Player): Unit = {
    if (clickType == ClickType.SWAP && button == lockSlot) {
      return
    } else if (slotNum >= 0 && slotNum < slots.size) {
      val slot = getSlot(slotNum)
      if (slot.container == playerInventory && slot.getSlotIndex == lockSlot)
        return
    }
    super.clicked(slotNum, button, clickType, player)
  }

  bindPlayerInventory(playerInventory, 8, 140, 198)
}