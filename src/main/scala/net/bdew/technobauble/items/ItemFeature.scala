package net.bdew.technobauble.items

import net.bdew.lib.PimpVanilla._
import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.lib.items.StackProperty
import net.minecraft.Util
import net.minecraft.client.KeyMapping
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.{Dist, OnlyIn}

case class ItemFeature(id: String) {
  val flag = new StackProperty[Boolean](id)

  def enabled(stack: ItemStack): Boolean =
    flag.get(stack, true)

  def setEnabled(stack: ItemStack, v: Boolean): Unit =
    flag.set(stack, v)

  def label: MutableComponent = Text.translate(s"technobauble.feature.$id")

  def toggle(stack: ItemStack, owner: Player): Boolean = {
    val newVal = !enabled(stack)
    setEnabled(stack, newVal)

    owner.sendMessage(Text.translate("technobauble.message.toggle",
      label.setColor(Text.Color.YELLOW),
      if (newVal)
        Text.translate("technobauble.label.on").setColor(Text.Color.GREEN)
      else
        Text.translate("technobauble.label.off").setColor(Text.Color.RED)
    ), Util.NIL_UUID)

    newVal
  }

  @OnlyIn(Dist.CLIENT)
  def hoverText(stack: ItemStack, keybind: KeyMapping): MutableComponent = {
    Text.translate("technobauble.label.feature",
      label,
      if (enabled(stack))
        Text.translate("technobauble.label.on").setColor(Text.Color.GREEN)
      else
        Text.translate("technobauble.label.off").setColor(Text.Color.RED),
      Text.translate(
        "technobauble.tooltip.key",
        keybind.getTranslatedKeyMessage
      ).setColor(Text.Color.YELLOW)
    )
  }
}
