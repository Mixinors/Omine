package dev.vini2003.omine.client.util

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity

object ClientUtils {
    val player: ClientPlayerEntity?
        get() = MinecraftClient.getInstance().player
}