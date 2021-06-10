package net.bdew.technobauble.items.backpack

import net.bdew.lib.PimpVanilla.pimpLazyOpt
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.lib.capabilities.handlers.InventoryItemHandler
import net.bdew.lib.{Misc, Text}
import net.bdew.technobauble.Caps
import net.bdew.technobauble.client.Keybinds
import net.bdew.technobauble.registries.Items
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.{PlayerEntity, ServerPlayerEntity}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.PacketBuffer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.{ActionResult, Direction, Hand}
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.{Capability, ICapabilityProvider}
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fml.network.NetworkHooks
import top.theillusivec4.curios.api.`type`.capability.ICurio

import java.util

class ItemBackpack extends Item(Items.nonStackable) {
  override def initCapabilities(stack: ItemStack, nbt: CompoundNBT): ICapabilityProvider =
    new CapHolder(new CurioBackpack(stack, this))

  override def appendHoverText(stack: ItemStack, world: World, toolTip: util.List[ITextComponent], flags: ITooltipFlag): Unit = {
    toolTip.add(
      Text.translate("technobauble.backpack.desc",
        Text.translate(
          "technobauble.tooltip.key",
          Keybinds.openBackpack.getTranslatedKeyMessage
        ).setColor(Text.Color.YELLOW)
      ).setColor(Text.Color.GRAY)
    )
  }

  override def use(world: World, player: PlayerEntity, hand: Hand): ActionResult[ItemStack] = {
    val stack = player.getItemInHand(hand)
    if (world.isClientSide) return ActionResult.success(stack)

    player match {
      case p: ServerPlayerEntity =>
        stack.getCapability(Caps.CURIO).toScala
          .flatMap(x => Misc.asInstanceOpt(x, classOf[CurioBackpack]))
          .foreach(x => {
            val ls = if (hand == Hand.MAIN_HAND) player.inventory.selected else 40 // 40 is offhand slot
            NetworkHooks.openGui(p, x, (pb: PacketBuffer) => {
              pb.writeByte(ls)
            })
            Misc.asInstanceOpt(player.containerMenu, classOf[ContainerBackpack]).foreach(_.lockSlot = ls)
          })
        ActionResult.consume(stack)
      case _ => ActionResult.fail(stack)
    }
  }

  class CapHolder(curio: CurioBackpack) extends ICapabilityProvider {
    val curioOpt: LazyOptional[ICurio] = LazyOptional.of(() => curio)

    val itemOpt: LazyOptional[InventoryItemHandler] =
      LazyOptional.of(() => new InventoryItemHandler(curio.inventory(), _ => true, (_, _) => true))

    //noinspection ComparingUnrelatedTypes
    override def getCapability[T](cap: Capability[T], side: Direction): LazyOptional[T] =
      if (cap == Caps.CURIO)
        curioOpt.cast()
      else if (cap == Caps.ITEM_HANDLER)
        itemOpt.cast()
      else
        LazyOptional.empty()
  }
}
