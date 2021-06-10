package net.bdew.technobauble.items.receiver

import net.bdew.lib.Misc
import net.bdew.lib.PimpVanilla._
import net.bdew.lib.capabilities.helpers.EnergyHelper
import net.bdew.technobauble.Caps
import net.bdew.technobauble.blocks.charger.TileCharger
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.`type`.capability.ICurio

import scala.jdk.CollectionConverters._

class CurioReceiver(stack: ItemStack, item: ItemReceiver) extends ICurio {
  override def curioTick(identifier: String, index: Int, entity: LivingEntity): Unit = {
    if (entity.level.isClientSide) return
    for {
      bind <- item.getBind(stack)
      world <- bind.world(entity.level.getServer) if world.isLoaded(bind.pos)
      tile <- world.getTileSafe[TileCharger](bind.pos) if tile.power.stored > 0
    } {
      // Charge other curios
      for {
        curios <- CuriosApi.getCuriosHelper.getEquippedCurios(entity)
        slot <- 0 until curios.getSlots
        stack = curios.getStackInSlot(slot) if !stack.isEmpty && stack != this.stack
        handler <- stack.getCapability(Caps.ENERGY)
      } {
        EnergyHelper.pushEnergy(tile.chargeHandler, handler, false)
      }

      // Charge armor
      for {
        stack <- entity.getArmorSlots.asScala if !stack.isEmpty
        handler <- stack.getCapability(Caps.ENERGY)
      } {
        EnergyHelper.pushEnergy(tile.chargeHandler, handler, false)
      }

      // Charge inventory
      for {
        player <- Misc.asInstanceOpt(entity, classOf[PlayerEntity])
        stack <- player.inventory.items.asScala
        handler <- stack.getCapability(Caps.ENERGY)
      } {
        EnergyHelper.pushEnergy(tile.chargeHandler, handler, false)
      }
    }
  }
}
