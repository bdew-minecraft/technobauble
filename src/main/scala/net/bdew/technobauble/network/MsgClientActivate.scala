package net.bdew.technobauble.network

import net.minecraft.network.FriendlyByteBuf

object ActivateKind extends Enumeration {
  val BACKPACK: Value = Value(0, "BACKPACK")
  val TOGGLE_MAGNET: Value = Value(1, "TOGGLE_MAGNET")
  val TOGGLE_RUN: Value = Value(2, "TOGGLE_RUN")
  val TOGGLE_JUMP: Value = Value(3, "TOGGLE_JUMP")
}

case class MsgClientActivate(kind: ActivateKind.Value) extends NetworkHandler.Message

object CodecClientActivate extends NetworkHandler.Codec[MsgClientActivate] {
  override def encodeMsg(m: MsgClientActivate, p: FriendlyByteBuf): Unit =
    p.writeByte(m.kind.id)

  override def decodeMsg(p: FriendlyByteBuf): MsgClientActivate =
    MsgClientActivate(ActivateKind(p.readByte()))
}