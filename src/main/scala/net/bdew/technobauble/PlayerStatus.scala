package net.bdew.technobauble

import net.bdew.lib.Client
import net.bdew.technobauble.network.{MsgUpdateStatus, NetworkHandler}
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.ServerTickEvent
import net.minecraftforge.event.entity.{EntityJoinWorldEvent, EntityLeaveWorldEvent}
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.server.ServerLifecycleHooks
import org.apache.logging.log4j.{LogManager, Logger}

import java.util.UUID
import scala.collection.mutable

case class PlayerStatus(hasStepAssist: Boolean)

object PlayerStatusManager {
  val log: Logger = LogManager.getLogger

  val defaultStatus: PlayerStatus = PlayerStatus(
    hasStepAssist = false
  )

  var localStatus: PlayerStatus = defaultStatus

  val remoteStatus: mutable.Map[UUID, PlayerStatus] =
    mutable.Map.empty[UUID, PlayerStatus].withDefaultValue(defaultStatus)

  val updates: mutable.Map[UUID, PlayerStatus] =
    mutable.Map.empty[UUID, PlayerStatus]

  @SubscribeEvent
  def onEntityJoinWorldEvent(ev: EntityJoinWorldEvent): Unit = {
    ev.getEntity match {
      case p: Player =>
        if (p.isLocalPlayer)
          updateLocal(defaultStatus)
        else
          remoteStatus += p.getUUID -> defaultStatus
      case _ => //pass
    }
  }
  @SubscribeEvent
  def onEntityLeaveWorldEvent(ev: EntityLeaveWorldEvent): Unit = {
    ev.getEntity match {
      case p: Player if !p.isLocalPlayer =>
        remoteStatus -= p.getUUID
      case _ => //pass
    }
  }

  @SubscribeEvent
  def onServerTick(ev: ServerTickEvent): Unit = {
    if (ev.phase == TickEvent.Phase.END && updates.nonEmpty) {
      for ((uid, status) <- updates) {
        if (remoteStatus(uid) != status) {
          remoteStatus += uid -> status
          NetworkHandler.sendTo(MsgUpdateStatus(status), ServerLifecycleHooks.getCurrentServer.getPlayerList.getPlayer(uid))
        }
      }
      updates.clear()
    }
  }

  def updateStepAssist(p: ServerPlayer, stepAssist: Boolean): Unit = {
    if (updates.getOrElse(p.getUUID, remoteStatus(p.getUUID)).hasStepAssist != stepAssist) {
      val newStatus = remoteStatus(p.getUUID).copy(hasStepAssist = stepAssist)
      updates += p.getUUID -> newStatus
    }
  }

  def updateLocal(s: PlayerStatus): Unit = {
    localStatus = s
    Client.player.maxUpStep = if (s.hasStepAssist) 1.6f else 0.6f
  }
}
