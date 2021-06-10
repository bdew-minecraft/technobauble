package net.bdew.technobauble.network

import net.minecraft.network.PacketBuffer

object ActivateKind extends Enumeration {
  val BACKPACK: Value = Value(0, "BACKPACK")
}

case class MsgClientActivate(kind: ActivateKind.Value) extends NetworkHandler.Message

object CodecClientActivate extends NetworkHandler.Codec[MsgClientActivate] {
  override def encodeMsg(m: MsgClientActivate, p: PacketBuffer): Unit =
    p.writeByte(m.kind.id)

  override def decodeMsg(p: PacketBuffer): MsgClientActivate =
    MsgClientActivate(ActivateKind(p.readByte()))
}