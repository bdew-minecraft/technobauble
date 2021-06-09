package net.bdew.technobauble.items.receiver

import net.bdew.lib.PimpVanilla.pimpNBT
import net.bdew.lib.Text
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.lib.block.BlockPosDim
import net.bdew.lib.capabilities.SimpleCapProvider
import net.bdew.technobauble.Caps
import net.bdew.technobauble.registries.{Blocks, Items}
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.{Item, ItemStack, ItemUseContext}
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.{ActionResultType, Util}
import net.minecraft.world.World
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

  override def useOn(ctx: ItemUseContext): ActionResultType = {
    if (ctx.getLevel.getBlockState(ctx.getClickedPos).getBlock != Blocks.charger.block.get())
      return ActionResultType.PASS
    if (ctx.getLevel.isClientSide) return ActionResultType.SUCCESS
    setBind(ctx.getItemInHand, BlockPosDim(ctx.getClickedPos, ctx.getLevel.dimension()))
    ctx.getPlayer.sendMessage(
      Text.translate("technobauble.bound",
        "%d, %d, %d".format(ctx.getClickedPos.getX, ctx.getClickedPos.getY, ctx.getClickedPos.getZ),
        ctx.getLevel.dimension.location.toString
      ), Util.NIL_UUID
    )
    ActionResultType.CONSUME
  }

  override def appendHoverText(stack: ItemStack, world: World, toolTip: util.List[ITextComponent], flags: ITooltipFlag): Unit = {
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

  override def initCapabilities(stack: ItemStack, nbt: CompoundNBT): ICapabilityProvider =
    SimpleCapProvider(Caps.CURIO, new CurioReceiver(stack, this))
}
