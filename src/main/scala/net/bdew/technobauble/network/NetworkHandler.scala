package net.bdew.technobauble.network

import net.bdew.lib.network.NetChannel
import net.bdew.technobauble.items.backpack.CurioBackpack
import net.bdew.technobauble.{CurioUtils, Technobauble}
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkHooks

object NetworkHandler extends NetChannel(Technobauble.ModId, "main", "1") {
  regServerHandler(1, CodecClientActivate) { (msg, ctx) =>
    CurioUtils.findCurio(ctx.getSender, classOf[CurioBackpack])
      .foreach(NetworkHooks.openGui(ctx.getSender, _, (pb: PacketBuffer) => {
        pb.writeByte(-1)
      }))
  }
}