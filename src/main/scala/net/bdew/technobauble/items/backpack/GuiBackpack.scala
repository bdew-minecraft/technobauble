package net.bdew.technobauble.items.backpack

import net.bdew.lib.gui.widgets.WidgetLabel
import net.bdew.lib.gui.{BaseScreen, Color, Rect, Sprite, Texture}
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.text.ITextComponent

class GuiBackpack(container: ContainerBackpack, playerInv: PlayerInventory, title: ITextComponent)
  extends BaseScreen(container, playerInv, title) {
  override val background: Sprite = Texture("minecraft", "textures/gui/container/generic_54.png", Rect(0, 0, 176, 222))

  override def init(): Unit = {
    initGui(176, 222)
    widgets.add(new WidgetLabel(title, 8, 6, Color.darkGray))
    widgets.add(new WidgetLabel(playerInv.getName, 8, rect.h - 94, Color.darkGray))
  }
}
