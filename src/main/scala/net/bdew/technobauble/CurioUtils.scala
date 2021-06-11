package net.bdew.technobauble

import net.bdew.lib.Misc
import net.bdew.lib.PimpVanilla.pimpLazyOpt
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.{Item, ItemStack}
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.`type`.capability.ICurio

object CurioUtils {
  def findCurio[I <: Item, C <: ICurio](player: PlayerEntity, itemCls: Class[I], curioCls: Class[C]): Option[C] = {
    CuriosApi.getCuriosHelper.findEquippedCurio((s: ItemStack) => itemCls.isInstance(s.getItem), player)
      .map[Option[ItemStack]](found => Some(found.right)).orElse(None)
      .flatMap(_.getCapability(Caps.CURIO).toScala)
      .flatMap(x => Misc.asInstanceOpt(x, curioCls))
  }
}
