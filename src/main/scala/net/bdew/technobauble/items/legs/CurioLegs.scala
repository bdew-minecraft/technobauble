package net.bdew.technobauble.items.legs

import net.bdew.technobauble.PlayerStatusManager
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.vector.Vector3d
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.`type`.capability.ICurio

import scala.collection.mutable

class CurioLegs(stack: ItemStack, item: ItemLegs) extends ICurio {
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
      case entity: ServerPlayerEntity => PlayerStatusManager.updateStepAssist(entity, stepAssist)
      case _ => // pass
    }
  }

  def canSpeedBoost(player: LivingEntity): Boolean =
    player.isOnGround && !player.isPassenger && !player.isSwimming && !player.isUnderWater

  override def curioTick(identifier: String, index: Int, livingEntity: LivingEntity): Unit = {
    if (item.runBoost.enabled(stack) && item.hasCharge(stack, item.cfg.movingEnergyCost())) {
      updateStepAssist(livingEntity, true)
      if (canSpeedBoost(livingEntity)) {
        if (livingEntity.level.isClientSide) {
          if (livingEntity.zza != 0 || livingEntity.xxa != 0)
            livingEntity.moveRelative(item.cfg.runBoost(), new Vector3d(livingEntity.xxa, 0, livingEntity.zza))
        } else {
          if (PlayerPosTracker.positions.get(livingEntity).exists(_.distanceTo(livingEntity.position()) > 0.01))
            item.useCharge(stack, item.cfg.movingEnergyCost())
          PlayerPosTracker.positions += livingEntity -> livingEntity.position()
        }
      }
    } else {
      updateStepAssist(livingEntity, false)
    }
  }

  override def onUnequip(slotContext: SlotContext, newStack: ItemStack): Unit = {
    if (newStack.getItem != item && !slotContext.getWearer.level.isClientSide) {
      updateStepAssist(slotContext.getWearer, false)
      PlayerPosTracker.positions.remove(slotContext.getWearer)
    }
  }
}

object PlayerPosTracker {
  val positions: mutable.Map[LivingEntity, Vector3d] = mutable.WeakHashMap.empty[LivingEntity, Vector3d]
}