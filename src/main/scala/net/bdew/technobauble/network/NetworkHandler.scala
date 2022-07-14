package net.bdew.technobauble.network

import net.bdew.lib.network.NetChannel
import net.bdew.technobauble.items.ItemFeature
import net.bdew.technobauble.items.backpack.{CurioBackpack, ItemBackpack}
import net.bdew.technobauble.items.legs.ItemLegs
import net.bdew.technobauble.items.magnet.ItemMagnet
import net.bdew.technobauble.{PlayerStatusManager, Technobauble, Utils}
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraftforge.network.NetworkHooks

object NetworkHandler extends NetChannel(Technobauble.ModId, "main", "1") {
  def toggleFeature[T <: Item](cls: Class[T], feature: T => ItemFeature, owner: Player): Unit = {
    Utils.findCurioStack(owner, cls).foreach(stack => {
      feature(stack.getItem.asInstanceOf[T]).toggle(stack, owner)
    })
  }

  regServerHandler(1, CodecClientActivate) { (msg, ctx) =>
    msg.kind match {
      case ActivateKind.BACKPACK =>
        Utils.findCurioHandler(ctx.getSender, classOf[ItemBackpack], classOf[CurioBackpack])
          .foreach(NetworkHooks.openScreen(ctx.getSender, _, (pb: FriendlyByteBuf) => {
            pb.writeByte(-1)
          }))
      case ActivateKind.TOGGLE_MAGNET =>
        toggleFeature[ItemMagnet](classOf[ItemMagnet], _.attract, ctx.getSender)
      case ActivateKind.TOGGLE_RUN =>
        toggleFeature[ItemLegs](classOf[ItemLegs], _.runBoost, ctx.getSender)
      case ActivateKind.TOGGLE_JUMP =>
        toggleFeature[ItemLegs](classOf[ItemLegs], _.jumpBoost, ctx.getSender)
    }
  }

  regClientHandler(2, CodecUpdateStatus) { msg =>
    PlayerStatusManager.updateLocal(msg.status)
  }
}