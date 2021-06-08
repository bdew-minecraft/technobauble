package net.bdew.technobauble.blocks.charger

import net.bdew.lib.capabilities.Capabilities
import net.bdew.lib.capabilities.handlers.PowerEnergyHandler
import net.bdew.lib.data.base.TileDataSlots
import net.bdew.lib.power.DataSlotPower
import net.bdew.lib.tile.TileExtended
import net.bdew.technobauble.Config
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.IEnergyStorage

class TileCharger(teType: TileEntityType[_]) extends TileExtended(teType) with TileDataSlots {
  val power: DataSlotPower = DataSlotPower("power", this)

  val powerHandler: LazyOptional[IEnergyStorage] = PowerEnergyHandler.create(power, true, false)
  val chargeHandler: PowerEnergyHandler = new PowerEnergyHandler(power, false, true)

  power.configure(Config.Charger)

  //noinspection ComparingUnrelatedTypes
  override def getCapability[T](cap: Capability[T], side: Direction): LazyOptional[T] = {
    if (cap == Capabilities.CAP_ENERGY_HANDLER)
      powerHandler.cast()
    else
      super.getCapability(cap, side)
  }

  override def invalidateCaps(): Unit = {
    super.invalidateCaps()
    powerHandler.invalidate()
  }
}
