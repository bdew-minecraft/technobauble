package net.bdew.technobauble.items.magnet

import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.technobauble.Config
import net.bdew.technobauble.client.Keybinds
import net.bdew.technobauble.items.{ItemFeature, PoweredCurioItem}
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

import java.util

class ItemMagnet extends PoweredCurioItem[CurioMagnet] {
  override val cfg: ConfigMagnet = Config.Magnet
  override def makeCurio(s: ItemStack): CurioMagnet = new CurioMagnet(s, this)

  val attract: ItemFeature = ItemFeature("attract")

  override def appendHoverText(stack: ItemStack, world: World, tooltip: util.List[ITextComponent], flag: ITooltipFlag): Unit = {
    tooltip.add(attract.hoverText(stack, Keybinds.toggleRun))
    super.appendHoverText(stack, world, tooltip, flag)
    tooltip.add(Text.translate("technobauble.magnet.desc").setColor(Text.Color.GRAY))
  }
}


