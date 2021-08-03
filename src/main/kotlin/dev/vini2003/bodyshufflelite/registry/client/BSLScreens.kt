package dev.vini2003.bodyshufflelite.registry.client

import dev.vini2003.blade.client.handler.BaseHandledScreen
import dev.vini2003.blade.common.handler.BaseScreenHandler
import dev.vini2003.bodyshufflelite.registry.common.BSLScreenHandlers
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.screen.ScreenHandlerType

object BSLScreens {
	fun init() {
		register(BSLScreenHandlers.BodyPartSelector)
	}
	
	private fun <S : BaseScreenHandler> register(type: ScreenHandlerType<S>) {
		ScreenRegistry.register(type, ::BaseHandledScreen)
	}
}