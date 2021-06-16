package net.bdew.technobauble.items.magnet

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.item.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.EntityPredicates
import net.minecraft.util.math.AxisAlignedBB
import top.theillusivec4.curios.api.`type`.capability.ICurio

import scala.jdk.CollectionConverters._
import scala.util.Random

class CurioMagnet(stack: ItemStack, item: ItemMagnet) extends ICurio {
  var tickDelay: Int = Random.nextInt(10)

  override def curioTick(identifier: String, index: Int, player: LivingEntity): Unit = {
    if (player.level.isClientSide || !item.attract.enabled(stack) || item.getCharge(stack) < item.cfg.energyPerItem()) return

    if (tickDelay > 0) {
      tickDelay -= 1
      return
    }

    tickDelay = 10

    val radius = item.cfg.attractRadius()
    val radiusSq = radius * radius

    val area = new AxisAlignedBB(player.position.add(-radius, -radius, -radius), player.position.add(1 + radius, 1 + radius, 1 + radius))
    for {
      itemEnt <- player.level.getEntitiesOfClass(classOf[ItemEntity], area, EntityPredicates.ENTITY_STILL_ALIVE).asScala
      if !itemEnt.hasPickUpDelay && itemEnt.position().distanceToSqr(player.position()) <= radiusSq
    } {
      if (!item.useCharge(stack, item.cfg.energyPerItem())) return
      itemEnt.setPos(player.getX, player.getY, player.getZ)
    }
  }
}