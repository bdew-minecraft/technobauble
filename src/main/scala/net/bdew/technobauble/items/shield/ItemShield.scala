package net.bdew.technobauble.items.shield

import net.bdew.technobauble.Config
import net.bdew.technobauble.items.PoweredCurioItem
import net.minecraft.item.ItemStack

class ItemShield extends PoweredCurioItem[CurioShield] {
  override def cfg: ConfigShield = Config.Shield
  override def makeCurio(s: ItemStack): CurioShield = new CurioShield(s, this)
}