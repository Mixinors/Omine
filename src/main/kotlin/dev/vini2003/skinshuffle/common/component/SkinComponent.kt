package dev.vini2003.skinshuffle.common.component

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent
import dev.vini2003.skinshuffle.common.util.extension.loadSkin
import net.minecraft.client.network.AbstractClientPlayerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import kotlin.properties.Delegates

class SkinComponent(val player: PlayerEntity) : PlayerComponent<SkinComponent>, AutoSyncedComponent {
	var target: String? by Delegates.observable(null) { _, _, _ ->
		if (player is AbstractClientPlayerEntity && target != null) {
			player.loadSkin(target!!)
		}
	}
	
	override fun readFromNbt(tag: NbtCompound) {
		if (tag.contains("target")) {
			target = tag.getString("target")
		}
	}
	
	override fun writeToNbt(tag: NbtCompound) {
		if (target != null) {
			tag.putString("target", target)
		}
	}
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false
		
		other as SkinComponent
		
		if (target != other.target) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		return javaClass.hashCode()
	}
}