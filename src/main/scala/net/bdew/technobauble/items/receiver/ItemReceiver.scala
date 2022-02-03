package net.bdew.technobauble.items.receiver

import net.bdew.lib.PimpVanilla.pimpNBT
import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.lib.block.BlockPosDim
import net.bdew.lib.capabilities.SimpleCapProvider
import net.bdew.technobauble.Caps
import net.bdew.technobauble.registries.{Blocks, Items}
import net.minecraft.Util
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.{Item, ItemStack, TooltipFlag}
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.ICapabilityProvider

import java.util

class ItemReceiver extends Item(Items.nonStackable) {
  def getBind(stack: ItemStack): Option[BlockPosDim] =
    if (!stack.hasTag || !stack.getTag.contains("bind"))
      None
    else
      stack.getTag.getVal[BlockPosDim]("bind")

  def setBind(stack: ItemStack, bind: BlockPosDim): Unit = {
    stack.getOrCreateTag.setVal("bind", bind)
  }

  override def useOn(ctx: UseOnContext): InteractionResult = {
    if (ctx.getLevel.getBlockState(ctx.getClickedPos).getBlock != Blocks.charger.block.get())
      return InteractionResult.PASS
    if (ctx.getLevel.isClientSide) return InteractionResult.SUCCESS
    setBind(ctx.getItemInHand, BlockPosDim(ctx.getClickedPos, ctx.getLevel.dimension()))
    ctx.getPlayer.sendMessage(
      Text.translate("technobauble.bound",
        "%d, %d, %d".format(ctx.getClickedPos.getX, ctx.getClickedPos.getY, ctx.getClickedPos.getZ),
        ctx.getLevel.dimension.location.toString
      ), Util.NIL_UUID
    )
    InteractionResult.CONSUME
  }

  override def appendHoverText(stack: ItemStack, world: Level, toolTip: util.List[Component], flags: TooltipFlag): Unit = {
    getBind(stack) match {
      case Some(p) =>
        toolTip.add(Text.translate("technobauble.bound", "%d, %d, %d".format(p.x, p.y, p.z), p.dim.location.toString))
      case None =>
        toolTip.add(Text.translate("technobauble.unbound").setColor(Text.Color.RED))
    }
    toolTip.add(
      Text.translate("technobauble.receiver.desc",
        Blocks.charger.block.get().getName.setColor(Text.Color.YELLOW)
      ).setColor(Text.Color.GRAY)
    )
  }

  override def initCapabilities(stack: ItemStack, nbt: CompoundTag): ICapabilityProvider =
    SimpleCapProvider(Caps.CURIO, new CurioReceiver(stack, this))
}
