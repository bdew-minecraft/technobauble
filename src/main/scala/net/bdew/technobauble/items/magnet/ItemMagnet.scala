package net.bdew.technobauble.items.magnet

import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.technobauble.Config
import net.bdew.technobauble.client.Keybinds
import net.bdew.technobauble.items.PoweredCurioItem
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.world.World

import java.util

class ItemMagnet extends PoweredCurioItem[CurioMagnet] {
  override val cfg: ConfigMagnet = Config.Magnet
  override def makeCurio(s: ItemStack): CurioMagnet = new CurioMagnet(s, this)

  def isDisabled(s: ItemStack): Boolean =
    s.hasTag && s.getTag.contains("disabled") && s.getTag.getBoolean("disabled")

  def setDisabled(s: ItemStack, disabled: Boolean): Unit = {
    s.getOrCreateTag().putBoolean("disabled", disabled)
  }

  override def appendHoverText(stack: ItemStack, world: World, tooltip: util.List[ITextComponent], flag: ITooltipFlag): Unit = {
    tooltip.add(Text.translate("technobauble.label.status",
      if (isDisabled(stack))
        Text.translate("technobauble.label.off").setColor(Text.Color.RED)
      else
        Text.translate("technobauble.label.on").setColor(Text.Color.GREEN)
    ))

    super.appendHoverText(stack, world, tooltip, flag)

    tooltip.add(
      Text.translate("technobauble.magnet.desc",
        Text.translate(
          "technobauble.tooltip.key",
          Keybinds.toggleMagnet.getTranslatedKeyMessage
        ).setColor(Text.Color.YELLOW)
      ).setColor(Text.Color.GRAY)
    )
  }
}


