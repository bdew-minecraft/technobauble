package net.bdew.technobauble.items.shield

import net.bdew.technobauble.Config
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.LivingHurtEvent
import top.theillusivec4.curios.api.`type`.capability.ICurio

class CurioShield(stack: ItemStack, item: ItemShield) extends ICurio {
  def doAbsorb(ev: LivingHurtEvent): Unit = {
    if (item.getCharge(stack) <= 0 || ev.getSource.isBypassInvul) return
    val needPower = ev.getAmount * Config.Shield.damageAbsorbCost()
    if (item.useCharge(stack, needPower)) {
      ev.setCanceled(true)
    } else {
      val absorbed = item.getCharge(stack) / Config.Shield.damageAbsorbCost()
      ev.setAmount(ev.getAmount - absorbed)
      item.setCharge(stack, 0)
    }
  }
}