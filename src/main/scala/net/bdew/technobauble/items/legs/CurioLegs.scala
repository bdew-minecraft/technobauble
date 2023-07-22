package net.bdew.technobauble.items.legs

import net.bdew.technobauble.PlayerStatusManager
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.Vec3
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.`type`.capability.ICurio

import scala.collection.mutable

class CurioLegs(stack: ItemStack, item: ItemLegs) extends ICurio {
  override def getStack: ItemStack = stack

  def doJump(ev: LivingJumpEvent): Unit = {
    if (!item.jumpBoost.enabled(stack)) return
    if (item.useCharge(stack, item.cfg.jumpEnergyCost())) {
      ev.getEntity.setDeltaMovement(ev.getEntity.getDeltaMovement.add(0, item.cfg.jumpBoost(), 0))
    }
  }

  def doAbsorb(ev: LivingAttackEvent): Unit = {
    if (item.useCharge(stack, item.cfg.fallAbsorbCost())) {
      ev.setCanceled(true)
    }
  }

  def updateStepAssist(wearer: LivingEntity, stepAssist: Boolean): Unit = {
    wearer match {
      case entity: ServerPlayer => PlayerStatusManager.updateStepAssist(entity, stepAssist)
      case _ => // pass
    }
  }

  def canSpeedBoost(player: LivingEntity): Boolean =
    player.onGround() && !player.isPassenger && !player.isSwimming && !player.isUnderWater


  override def curioTick(ctx: SlotContext): Unit = {
    if (item.runBoost.enabled(stack) && item.hasCharge(stack, item.cfg.movingEnergyCost())) {
      updateStepAssist(ctx.entity, true)
      if (canSpeedBoost(ctx.entity)) {
        if (ctx.entity.level.isClientSide) {
          if (ctx.entity.zza != 0 || ctx.entity.xxa != 0)
            ctx.entity.moveRelative(item.cfg.runBoost(), new Vec3(ctx.entity.xxa, 0, ctx.entity.zza))
        } else {
          if (PlayerPosTracker.positions.get(ctx.entity).exists(_.distanceTo(ctx.entity.position()) > 0.01))
            item.useCharge(stack, item.cfg.movingEnergyCost())
          PlayerPosTracker.positions += ctx.entity -> ctx.entity.position()
        }
      }
    } else {
      updateStepAssist(ctx.entity, false)
    }
  }

  override def onUnequip(slotContext: SlotContext, newStack: ItemStack): Unit = {
    if (newStack.getItem != item && !slotContext.entity.level.isClientSide) {
      updateStepAssist(slotContext.entity, false)
      PlayerPosTracker.positions.remove(slotContext.entity)
    }
  }
}

object PlayerPosTracker {
  val positions: mutable.Map[LivingEntity, Vec3] = mutable.WeakHashMap.empty[LivingEntity, Vec3]
}