package net.bdew.technobauble

import net.bdew.technobauble.items.legs.{CurioLegs, ItemLegs}
import net.bdew.technobauble.items.shield.{CurioShield, ItemShield}
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent
import net.minecraftforge.event.entity.living.{LivingAttackEvent, LivingHurtEvent}
import net.minecraftforge.eventbus.api.SubscribeEvent


object PlayerEventHandler {
  @SubscribeEvent
  def onLivingHurt(ev: LivingHurtEvent): Unit = {
    if (ev.getEntity.level.isClientSide || ev.isCanceled || ev.getAmount <= 0) return
    ev.getEntity match {
      case player: PlayerEntity =>
        Utils.findCurioHandler(player, classOf[ItemShield], classOf[CurioShield])
          .foreach(_.doAbsorb(ev))
      case _ => //pass
    }
  }

  @SubscribeEvent
  def onLivingAttack(ev: LivingAttackEvent): Unit = {
    if (ev.getEntity.level.isClientSide || ev.isCanceled || ev.getAmount <= 0) return
    if (ev.getSource == DamageSource.FALL) {
      ev.getEntity match {
        case player: PlayerEntity =>
          Utils.findCurioHandler(player, classOf[ItemLegs], classOf[CurioLegs])
            .foreach(_.doAbsorb(ev))
        case _ => //pass
      }
    }
  }


  @SubscribeEvent
  def onLivingJump(ev: LivingJumpEvent): Unit = {
    ev.getEntity match {
      case player: PlayerEntity =>
        Utils.findCurioHandler(player, classOf[ItemLegs], classOf[CurioLegs])
          .foreach(_.doJump(ev))
      case _ => //pass
    }


  }
}
