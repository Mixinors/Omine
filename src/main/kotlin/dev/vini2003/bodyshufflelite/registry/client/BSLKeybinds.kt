package dev.vini2003.bodyshufflelite.registry.client

import dev.vini2003.bodyshufflelite.client.screen.BodyPartScreen
import dev.vini2003.bodyshufflelite.registry.common.BSLNetworks
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.network.PacketByteBuf
import org.lwjgl.glfw.GLFW

object BSLKeybinds {
	val BodyPartSelector = keybinding("part_selector", GLFW.GLFW_KEY_V) { client ->
		client.openScreen(BodyPartScreen())
	}
	
	fun init() {
	}
	
	private fun keybinding(name: String, key: Int, action: (MinecraftClient) -> Unit): KeyBinding {
		val keybinding = KeyBindingHelper.registerKeyBinding(KeyBinding("key.bodyshufflelite.$name", InputUtil.Type.KEYSYM, key, "category.bodyshufflelite.bodyshufflelite"))
		
		ClientTickEvents.END_CLIENT_TICK.register { client ->
			if (keybinding.wasPressed()) {
				action.invoke(client)
			}
		}
		
		return keybinding
	}
}