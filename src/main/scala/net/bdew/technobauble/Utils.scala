package net.bdew.technobauble

import net.bdew.lib.Misc
import net.bdew.lib.PimpVanilla.pimpLazyOpt
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.{Item, ItemStack}
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.`type`.capability.ICurio

object Utils {
  def findCurioStack[T <: Item](owner: Player, itemCls: Class[T]): Option[ItemStack] = {
    CuriosApi.getCuriosHelper.findFirstCurio(owner, (x: ItemStack) => itemCls.isInstance(x.getItem))
      .map[Option[ItemStack]](x => Some(x.stack())).orElse(None)
  }

  def findCurioHandler[I <: Item, C <: ICurio](player: Player, itemCls: Class[I], curioCls: Class[C]): Option[C] = {
    CuriosApi.getCuriosHelper.findFirstCurio(player, (s: ItemStack) => itemCls.isInstance(s.getItem))
      .map[Option[ItemStack]](found => Some(found.stack())).orElse(None)
      .flatMap(_.getCapability(Caps.CURIO).toScala)
      .flatMap(x => Misc.asInstanceOpt(x, curioCls))
  }
}
