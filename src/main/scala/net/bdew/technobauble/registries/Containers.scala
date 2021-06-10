package net.bdew.technobauble.registries

import net.bdew.lib.inventory.SimpleInventory
import net.bdew.lib.managers.ContainerManager
import net.bdew.technobauble.items.backpack.{ContainerBackpack, GuiBackpack}
import net.minecraft.inventory.container.ContainerType
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

object Containers extends ContainerManager {
  val backpack: RegistryObject[ContainerType[ContainerBackpack]] =
    registerSimple("backpack") {
      (id, inv, pb) => {
        val cont = new ContainerBackpack(new SimpleInventory(54), inv, id)
        cont.lockSlot = pb.readByte()
        cont
      }
    }

  @OnlyIn(Dist.CLIENT)
  override def onClientSetup(ev: FMLClientSetupEvent): Unit = {
    registerScreen(backpack) {
      new GuiBackpack(_, _, _)
    }
  }
}