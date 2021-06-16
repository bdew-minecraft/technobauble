package net.bdew.technobauble.network

import net.bdew.technobauble.PlayerStatus
import net.minecraft.network.PacketBuffer

case class MsgUpdateStatus(status: PlayerStatus) extends NetworkHandler.Message

object CodecUpdateStatus extends NetworkHandler.Codec[MsgUpdateStatus] {
  override def encodeMsg(m: MsgUpdateStatus, p: PacketBuffer): Unit = {
    p.writeBoolean(m.status.hasStepAssist)
  }

  override def decodeMsg(p: PacketBuffer): MsgUpdateStatus = {
    val stepAssist = p.readBoolean()
    MsgUpdateStatus(PlayerStatus(
      hasStepAssist = stepAssist
    ))
  }
}