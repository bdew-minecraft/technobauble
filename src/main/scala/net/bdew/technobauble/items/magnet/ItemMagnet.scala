package net.bdew.technobauble.items.magnet

import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.technobauble.Config
import net.bdew.technobauble.client.Keybinds
import net.bdew.technobauble.items.{ItemFeature, PoweredCurioItem}
import net.minecraft.network.chat.Component
import net.minecraft.world.item.{ItemStack, TooltipFlag}
import net.minecraft.world.level.Level

import java.util

class ItemMagnet extends PoweredCurioItem[CurioMagnet] {
  override val cfg: ConfigMagnet = Config.Magnet
  override def makeCurio(s: ItemStack): CurioMagnet = new CurioMagnet(s, this)

  val attract: ItemFeature = ItemFeature("attract")

  override def appendHoverText(stack: ItemStack, world: Level, tooltip: util.List[Component], flag: TooltipFlag): Unit = {
    tooltip.add(attract.hoverText(stack, Keybinds.toggleMagnet))
    super.appendHoverText(stack, world, tooltip, flag)
    tooltip.add(Text.translate("technobauble.magnet.desc").setColor(Text.Color.GRAY))
  }
}


