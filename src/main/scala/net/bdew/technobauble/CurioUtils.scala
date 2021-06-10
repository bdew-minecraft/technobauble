package net.bdew.technobauble

import net.bdew.lib.Misc
import net.bdew.lib.PimpVanilla.pimpLazyOpt
import net.bdew.technobauble.items.backpack.ItemBackpack
import net.minecraft.entity.player.PlayerEntity
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.`type`.capability.ICurio

object CurioUtils {
  def findCurio[T <: ICurio](player: PlayerEntity, cls: Class[T]): Option[T] = {
    CuriosApi.getCuriosHelper.getEquippedCurios(player).toScala.flatMap(curios => {
      (0 until curios.getSlots)
        .map(curios.getStackInSlot)
        .find(s => !s.isEmpty && s.getItem.isInstanceOf[ItemBackpack])
        .flatMap(_.getCapability(Caps.CURIO).toScala)
        .flatMap(x => Misc.asInstanceOpt(x, cls))
    })
  }
}
