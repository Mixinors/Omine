package dev.vini2003.countdown.registry.common

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.text.LiteralText
import org.joda.time.DateTime
import java.time.Duration

class CDCommands {
	companion object {
		fun init() {
			CommandRegistrationCallback.EVENT.register { dispatcher, server ->
				val root = literal("countdown").build()
				
				val reset = literal("reset").executes {
					CDCallbacks.End = DateTime.now().plusDays(Duration.ofDays(31).toDays().toInt())
					
					it.source.player.sendMessage(LiteralText(CDCallbacks.End.toString()), false)
					
					return@executes 1
				}.build()
				
				root.addChild(reset)
				
				dispatcher.root.addChild(root)
			}
		}
	}
}