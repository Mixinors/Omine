package dev.vini2003.omine.registry.client

import dev.vini2003.omine.Omine
import dev.vini2003.omine.client.util.GasParticleUtils
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

object HarmfulGasNetworking {
    val ADD_GAS_CLOUD: Identifier = Omine.identifier("add_gas_cloud")
    val REFRESH_GAS_CLOUD: Identifier = Omine.identifier("refresh_gas_cloud")
    private fun receiveAddGasCloudPacket(
        client: MinecraftClient,
        handler: ClientPlayNetworkHandler,
        buf: PacketByteBuf,
        sender: PacketSender
    ) {
        if (client.world != null) {
            client.world!!.addParticle(
                HarmfulGasParticleTypes.GAS,
                buf.readInt().toDouble(),
                buf.readInt().toDouble(),
                buf.readInt().toDouble(),
                0.0,
                0.0,
                0.0
            )
        }
    }

    private fun receiveRefreshGasCloudPacket(
        client: MinecraftClient,
        handler: ClientPlayNetworkHandler,
        buf: PacketByteBuf,
        sender: PacketSender
    ) {
        GasParticleUtils.shouldClear = true
    }

    fun sendAddGasCloudPacket(player: PlayerEntity?, pos: BlockPos) {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeInt(pos.x)
        buf.writeInt(pos.y)
        buf.writeInt(pos.z)
        ServerPlayNetworking.send(player as ServerPlayerEntity?, ADD_GAS_CLOUD, buf)
    }

    fun sendRefreshGasCloudPacket(player: PlayerEntity?) {
        val buf = PacketByteBuf(Unpooled.buffer())
        ServerPlayNetworking.send(player as ServerPlayerEntity?, REFRESH_GAS_CLOUD, buf)
    }

    fun initialize() {
        ClientPlayNetworking.registerGlobalReceiver(
            ADD_GAS_CLOUD
        ) { client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf, sender: PacketSender ->
            receiveAddGasCloudPacket(
                client,
                handler,
                buf,
                sender
            )
        }
        ClientPlayNetworking.registerGlobalReceiver(
            REFRESH_GAS_CLOUD
        ) { client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf, sender: PacketSender ->
            receiveRefreshGasCloudPacket(
                client,
                handler,
                buf,
                sender
            )
        }
    }
}