package net.bdew.technobauble.network

import net.bdew.technobauble.PlayerStatus
import net.minecraft.network.FriendlyByteBuf

case class MsgUpdateStatus(status: PlayerStatus) extends NetworkHandler.Message

object CodecUpdateStatus extends NetworkHandler.Codec[MsgUpdateStatus] {
  override def encodeMsg(m: MsgUpdateStatus, p: FriendlyByteBuf): Unit = {
    p.writeBoolean(m.status.hasStepAssist)
  }

  override def decodeMsg(p: FriendlyByteBuf): MsgUpdateStatus = {
    val stepAssist = p.readBoolean()
    MsgUpdateStatus(PlayerStatus(
      hasStepAssist = stepAssist
    ))
  }
}