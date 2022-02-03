package net.bdew.technobauble.items.receiver

import net.bdew.lib.Misc
import net.bdew.lib.PimpVanilla._
import net.bdew.lib.capabilities.helpers.EnergyHelper
import net.bdew.technobauble.Caps
import net.bdew.technobauble.blocks.charger.TileCharger
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.`type`.capability.ICurio
import top.theillusivec4.curios.api.{CuriosApi, SlotContext}

import scala.jdk.CollectionConverters._

class CurioReceiver(stack: ItemStack, item: ItemReceiver) extends ICurio {
  override def getStack: ItemStack = stack

  override def curioTick(ctx: SlotContext): Unit = {
    if (ctx.entity.level.isClientSide) return
    for {
      bind <- item.getBind(stack)
      world <- bind.world(ctx.entity.level.getServer) if world.isLoaded(bind.pos)
      tile <- world.getTileSafe[TileCharger](bind.pos) if tile.power.stored > 0
    } {
      // Charge other curios
      for {
        curios <- CuriosApi.getCuriosHelper.getEquippedCurios(ctx.entity)
        slot <- 0 until curios.getSlots
        stack = curios.getStackInSlot(slot) if !stack.isEmpty && stack != this.stack
        handler <- stack.getCapability(Caps.ENERGY)
      } {
        EnergyHelper.pushEnergy(tile.chargeHandler, handler, false)
      }

      // Charge armor
      for {
        stack <- ctx.entity.getArmorSlots.asScala if !stack.isEmpty
        handler <- stack.getCapability(Caps.ENERGY)
      } {
        EnergyHelper.pushEnergy(tile.chargeHandler, handler, false)
      }

      // Charge inventory
      for {
        player <- Misc.asInstanceOpt(ctx.entity, classOf[Player])
        stack <- player.getInventory.items.asScala
        handler <- stack.getCapability(Caps.ENERGY)
      } {
        EnergyHelper.pushEnergy(tile.chargeHandler, handler, false)
      }
    }
  }
}
