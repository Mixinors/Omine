package dev.vini2003.skinshuffle.common.util.extension

import com.mojang.authlib.minecraft.MinecraftProfileTexture
import dev.vini2003.skinshuffle.common.util.getProfile
import dev.vini2003.skinshuffle.minecraftClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minecraft.client.network.AbstractClientPlayerEntity

fun AbstractClientPlayerEntity.loadSkin(username: String) {
	GlobalScope.launch {
		val networkHandler = minecraftClient.networkHandler!!
		val listEntry = networkHandler.getPlayerListEntry(uuid)!!
		
		val profile = getProfile(username)
		val listTextures = listEntry.textures
		
		minecraftClient.skinProvider.loadSkin(profile, { type, id, textures ->
			listTextures[type] = id
			
			if (type == MinecraftProfileTexture.Type.SKIN) {
				val model = textures.getMetadata("model") ?: "default"
				
				listEntry.model = model
			}
		}, true)
	}
}