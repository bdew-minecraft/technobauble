package net.bdew.technobauble.items

import net.bdew.lib.Text
import net.bdew.lib.capabilities.handlers.ItemEnergyHandler
import net.bdew.lib.config.PowerConfig
import net.bdew.lib.power.ItemPoweredBase
import net.bdew.technobauble.Caps
import net.bdew.technobauble.registries.Items
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.item.{Item, ItemStack, TooltipFlag}
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.{Capability, ICapabilityProvider}
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.IEnergyStorage
import top.theillusivec4.curios.api.`type`.capability.ICurio

import java.util

abstract class PoweredCurioItem[C <: ICurio] extends Item(Items.nonStackable) with ItemPoweredBase {
  def cfg: PowerConfig
  def makeCurio(s: ItemStack): C

  override def maxCharge: Float = cfg.capacity()
  override def maxReceive: Float = cfg.maxReceive()
  override def maxExtract: Float = 0

  override def initCapabilities(stack: ItemStack, nbt: CompoundTag): ICapabilityProvider =
    new CapHolder(makeCurio(stack), new ItemEnergyHandler(this, stack, true, false))

  override def appendHoverText(stack: ItemStack, world: Level, tooltip: util.List[Component], flag: TooltipFlag): Unit = {
    tooltip.add(Text.energyCap(getCharge(stack), maxCharge))
    super.appendHoverText(stack, world, tooltip, flag)
  }

  class CapHolder(curio: C, energyStorage: IEnergyStorage) extends ICapabilityProvider {
    val curioOpt: LazyOptional[ICurio] = LazyOptional.of(() => curio)
    val energyOpt: LazyOptional[IEnergyStorage] = LazyOptional.of(() => energyStorage)

    //noinspection ComparingUnrelatedTypes
    override def getCapability[T](cap: Capability[T], side: Direction): LazyOptional[T] =
      if (cap == Caps.CURIO)
        curioOpt.cast()
      else if (cap == Caps.ENERGY)
        energyOpt.cast()
      else
        LazyOptional.empty()
  }
}
