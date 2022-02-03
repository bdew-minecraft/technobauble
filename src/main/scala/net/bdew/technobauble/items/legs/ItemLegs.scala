package net.bdew.technobauble.items.legs

import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.technobauble.Config
import net.bdew.technobauble.client.Keybinds
import net.bdew.technobauble.items.{ItemFeature, PoweredCurioItem}
import net.minecraft.network.chat.Component
import net.minecraft.world.item.{ItemStack, TooltipFlag}
import net.minecraft.world.level.Level

import java.util

class ItemLegs extends PoweredCurioItem[CurioLegs] {
  override val cfg: ConfigLegs = Config.Legs
  override def makeCurio(s: ItemStack): CurioLegs = new CurioLegs(s, this)

  val jumpBoost: ItemFeature = ItemFeature("jump_boost")
  val runBoost: ItemFeature = ItemFeature("run_boost")

  override def appendHoverText(stack: ItemStack, world: Level, tooltip: util.List[Component], flag: TooltipFlag): Unit = {
    tooltip.add(jumpBoost.hoverText(stack, Keybinds.toggleJump))
    tooltip.add(runBoost.hoverText(stack, Keybinds.toggleRun))
    super.appendHoverText(stack, world, tooltip, flag)
    tooltip.add(Text.translate("technobauble.legs.desc").setColor(Text.Color.GRAY))
  }
}


