package net.bdew.technobauble.items.shield

import net.bdew.lib.Text
import net.bdew.lib.capabilities.handlers.ItemEnergyHandler
import net.bdew.lib.power.ItemPoweredBase
import net.bdew.technobauble.registries.Items
import net.bdew.technobauble.{Caps, Config}
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.Direction
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.{Capability, ICapabilityProvider}
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.IEnergyStorage
import top.theillusivec4.curios.api.`type`.capability.ICurio

import java.util

class ItemShield extends Item(Items.nonStackable) with ItemPoweredBase {
  override def maxCharge: Float = Config.Shield.capacity()
  override def maxReceive: Float = Config.Shield.maxReceive()
  override def maxExtract: Float = 0

  override def initCapabilities(stack: ItemStack, nbt: CompoundNBT): ICapabilityProvider =
    new CapHolder(new CurioShield(stack, this), new ItemEnergyHandler(this, stack, true, false))

  override def appendHoverText(stack: ItemStack, world: World, tooltip: util.List[ITextComponent], flag: ITooltipFlag): Unit =
    tooltip.add(Text.energyCap(getCharge(stack), maxCharge))

  class CapHolder(curio: CurioShield, energyStorage: IEnergyStorage) extends ICapabilityProvider {
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
