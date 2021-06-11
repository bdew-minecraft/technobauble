package net.bdew.technobauble

import net.bdew.technobauble.items.shield.{CurioShield, ItemShield}
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.eventbus.api.SubscribeEvent


object DamageHandler {
  @SubscribeEvent
  def onLivingHurt(ev: LivingHurtEvent): Unit = {
    ev.getEntity match {
      case player: PlayerEntity if !player.level.isClientSide =>
        CurioUtils.findCurio(player, classOf[ItemShield], classOf[CurioShield])
          .foreach(_.doAbsorb(ev))
      case _ => //pass
    }
  }
}
