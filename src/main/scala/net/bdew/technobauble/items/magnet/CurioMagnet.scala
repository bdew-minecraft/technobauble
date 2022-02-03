package net.bdew.technobauble.items.magnet

import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.AABB
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.`type`.capability.ICurio

import scala.jdk.CollectionConverters._
import scala.util.Random

class CurioMagnet(stack: ItemStack, item: ItemMagnet) extends ICurio {
  var tickDelay: Int = Random.nextInt(10)

  override def getStack: ItemStack = stack

  override def curioTick(ctx: SlotContext): Unit = {
    if (ctx.entity.level.isClientSide || !item.attract.enabled(stack) || item.getCharge(stack) < item.cfg.energyPerItem()) return

    if (tickDelay > 0) {
      tickDelay -= 1
      return
    }

    tickDelay = 10

    val radius = item.cfg.attractRadius()
    val radiusSq = radius * radius

    val area = new AABB(ctx.entity.position.add(-radius, -radius, -radius), ctx.entity.position.add(1 + radius, 1 + radius, 1 + radius))
    for {
      itemEnt <- ctx.entity.level.getEntitiesOfClass(classOf[ItemEntity], area, EntitySelector.ENTITY_STILL_ALIVE).asScala
      if !itemEnt.hasPickUpDelay && itemEnt.position().distanceToSqr(ctx.entity.position()) <= radiusSq
    } {
      if (!item.useCharge(stack, item.cfg.energyPerItem())) return
      itemEnt.setPos(ctx.entity.getX, ctx.entity.getY, ctx.entity.getZ)
    }
  }
}