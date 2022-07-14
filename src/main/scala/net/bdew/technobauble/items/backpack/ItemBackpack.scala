package net.bdew.technobauble.items.backpack

import net.bdew.lib.PimpVanilla.pimpLazyOpt
import net.bdew.lib.Text.pimpTextComponent
import net.bdew.lib.capabilities.handlers.InventoryItemHandler
import net.bdew.lib.{Misc, Text}
import net.bdew.technobauble.Caps
import net.bdew.technobauble.client.Keybinds
import net.bdew.technobauble.registries.Items
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.{Item, ItemStack, TooltipFlag}
import net.minecraft.world.level.Level
import net.minecraft.world.{InteractionHand, InteractionResultHolder}
import net.minecraftforge.common.capabilities.{Capability, ICapabilityProvider}
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.network.NetworkHooks
import top.theillusivec4.curios.api.`type`.capability.ICurio

import java.util

class ItemBackpack extends Item(Items.nonStackable) {
  override def initCapabilities(stack: ItemStack, nbt: CompoundTag): ICapabilityProvider =
    new CapHolder(new CurioBackpack(stack, this))

  override def appendHoverText(stack: ItemStack, world: Level, toolTip: util.List[Component], flags: TooltipFlag): Unit = {
    toolTip.add(
      Text.translate("technobauble.backpack.desc",
        Text.translate(
          "technobauble.tooltip.key",
          Keybinds.openBackpack.getTranslatedKeyMessage
        ).setColor(Text.Color.YELLOW)
      ).setColor(Text.Color.GRAY)
    )
  }

  override def use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder[ItemStack] = {
    val stack = player.getItemInHand(hand)
    if (world.isClientSide) return InteractionResultHolder.success(stack)

    player match {
      case p: ServerPlayer =>
        stack.getCapability(Caps.CURIO).toScala
          .flatMap(x => Misc.asInstanceOpt(x, classOf[CurioBackpack]))
          .foreach(x => {
            val ls = if (hand == InteractionHand.MAIN_HAND) player.getInventory.selected else 40 // 40 is offhand slot
            NetworkHooks.openScreen(p, x, (pb: FriendlyByteBuf) => {
              pb.writeByte(ls)
            })
            Misc.asInstanceOpt(player.containerMenu, classOf[ContainerBackpack]).foreach(_.lockSlot = ls)
          })
        InteractionResultHolder.consume(stack)
      case _ => InteractionResultHolder.fail(stack)
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
