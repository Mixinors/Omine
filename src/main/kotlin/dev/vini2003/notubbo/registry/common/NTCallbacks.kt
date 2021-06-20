package dev.vini2003.notubbo.registry.common

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.text.LiteralText
import org.joda.time.DateTimeZone
import org.joda.time.Instant
import java.util.*

class NTCallbacks {
	companion object {
		val TubboUuid = UUID.fromString("06f7cd12-4f00-4342-85f6-956b1ca48d38")
		
		var LastDay = Instant.now().toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/London"))).dayOfMonth()
		
		var Ticks = 0L
		
		fun init() {
			ServerTickEvents.END_SERVER_TICK.register { server ->
				server.playerManager.playerList.firstOrNull {
					it.uuid.equals(TubboUuid)
				}.also { tubbo ->
					val NowDay = Instant.now().toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone("Europe/London"))).dayOfMonth()
					
					if (LastDay != NowDay) {
						Ticks = 0
						
						LastDay = NowDay
					}
					
					if (tubbo != null) {
						++Ticks
						
						if (Ticks > 3 * 60 * 60 * 20) {
							tubbo.networkHandler.disconnect(LiteralText("Time to sleep, Tubbo."))
						}
					}
				}
			}
		}
	}
}