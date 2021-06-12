package net.bdew.technobauble.network

import net.bdew.lib.network.NetChannel
import net.bdew.technobauble.items.backpack.{CurioBackpack, ItemBackpack}
import net.bdew.technobauble.items.magnet.{CurioMagnet, ItemMagnet}
import net.bdew.technobauble.{CurioUtils, Technobauble}
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkHooks
import org.apache.logging.log4j.{LogManager, Logger}

object NetworkHandler extends NetChannel(Technobauble.ModId, "main", "1") {
  val log: Logger = LogManager.getLogger
  regServerHandler(1, CodecClientActivate) { (msg, ctx) =>
    msg.kind match {
      case ActivateKind.BACKPACK =>
        CurioUtils.findCurio(ctx.getSender, classOf[ItemBackpack], classOf[CurioBackpack])
          .foreach(NetworkHooks.openGui(ctx.getSender, _, (pb: PacketBuffer) => {
            pb.writeByte(-1)
          }))
      case ActivateKind.MAGNET =>
        CurioUtils.findCurio(ctx.getSender, classOf[ItemMagnet], classOf[CurioMagnet])
          .foreach(_.toggle(ctx.getSender))
    }
  }
}