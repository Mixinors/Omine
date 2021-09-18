package dev.vini2003.omine.common.component

import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent
import net.minecraft.entity.damage.DamageSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import dev.vini2003.omine.common.util.BlockPosUtils
import dev.vini2003.omine.common.util.DirectionUtils
import dev.vini2003.omine.common.util.GasUtils
import dev.vini2003.omine.common.util.TimeUtils
import dev.vini2003.omine.registry.client.HarmfulGasNetworking
import dev.vini2003.omine.registry.common.HarmfulGasComponents
import java.util.*

class WorldGasComponent(private val world: World?) : Component,
    ServerTickingComponent {
    private val nodes: MutableSet<BlockPos> = HashSet()
    private val nodesIndexed: MutableList<BlockPos> = ArrayList()
    private val nodesToAdd: MutableSet<BlockPos> = HashSet()
    private val particles: MutableMap<UUID, MutableSet<BlockPos>> = HashMap()
    private val cooldowns: MutableMap<UUID, Int> = HashMap()
    var originPos = BlockPos(0, 0, 0)
    var isPaused = false
    var age: Long = 0
        private set
    private var progress = 0
    var speed = 20

    fun add(pos: BlockPos) {
        nodesToAdd.add(pos)
    }

    override fun serverTick() {
        if (world == null) return
        if (!isPaused) {
            ++age
            val start = nodesIndexed.size / speed * progress
            val end = Math.min(Math.max(256, start + nodesIndexed.size / speed), nodesIndexed.size)
            for (i in start until end) {
                val pos = nodesIndexed[i]
                val maxDist: Int = TimeUtils.calculateMinimumDistance(age, speed)
                val posDist: Double = BlockPosUtils.minimumSquaredDistance(world.players, pos)
                for (direction in DirectionUtils.DIRECTIONS) {
                    val sidePos = pos.offset(direction)
                    val sidePosDist: Double = BlockPosUtils.minimumSquaredDistance(world.players, sidePos)
                    if (!nodes.contains(sidePos)
                        && sidePos.y < TimeUtils.calculateMaximumHeight(
                            world,
                            sidePos,
                            age,
                            speed
                        ) && sidePos.y > TimeUtils.calculateMinimumHeight(
                            age,
                            speed
                        ) && ((age % TimeUtils.calculateFollowTime(age)) == 0L && sidePosDist < posDist || age % speed == 0L && sidePos.isWithinDistance(
                            originPos,
                            maxDist.toDouble()
                        ))
                    ) {
                        val sideState = world.getBlockState(sidePos)
                        val centerState = world.getBlockState(pos)
                        if (GasUtils.isTraversableForPropagation(
                                world,
                                centerState,
                                pos,
                                sideState,
                                sidePos,
                                direction
                            )
                        ) {
                            nodesToAdd.add(sidePos)
                        }
                    }
                }
                for (player in world.players) {
                    val distance: Double = BlockPosUtils.squaredDistance(player, pos)
                    cooldowns.putIfAbsent(player.uuid, 150)
                    if (distance < 1.0 && cooldowns[player.uuid]!! <= 0) {
                        player.damage(DamageSource.DROWN, 1.0f)
                    }
                    if (pos.x % 3 == 0 && pos.z % 3 == 0) {
                        particles.putIfAbsent(player.uuid, HashSet())
                        if (!particles[player.uuid]!!.contains(pos)) {
                            HarmfulGasNetworking.sendAddGasCloudPacket(player, pos)
                            particles[player.uuid]!!.add(pos)
                        }
                    }
                }
            }
            for (player in world.players) {
                cooldowns.putIfAbsent(player.uuid, 150)
                if (cooldowns[player.uuid]!! > 0) {
                    cooldowns[player.uuid] = cooldowns[player.uuid]!! - 1
                }
            }
            nodes.addAll(nodesToAdd)
            nodesIndexed.addAll(nodesToAdd)
            nodesToAdd.clear()
            ++progress
            if (progress >= speed) {
                progress = 0
            }
        }
    }

    override fun writeToNbt(tag: NbtCompound) {
        if (world == null) return
        val dataTag = NbtCompound()
        dataTag.putInt("OriginX", originPos.x)
        dataTag.putInt("OriginY", originPos.y)
        dataTag.putInt("OriginZ", originPos.z)
        dataTag.putBoolean("Paused", isPaused)
        dataTag.putLong("Age", age)
        dataTag.putInt("Progress", progress)
        dataTag.putInt("Speed", speed)
        var i = 0
        for (pos in nodes) {
            val pointTag = NbtCompound()
            pointTag.putLong("Pos", pos.asLong())
            dataTag.put(i.toString(), pointTag)
            ++i
        }
        tag.put("Data", dataTag)
    }

    override fun readFromNbt(tag: NbtCompound) {
        if (world == null) return
        val dataTag: NbtCompound = tag.getCompound("Data")
        originPos = BlockPos(
            dataTag.getInt("OriginX"),
            dataTag.getInt("OriginY"),
            dataTag.getInt("OriginZ")
        )
        isPaused = dataTag.getBoolean("Paused")
        age = dataTag.getLong("Age")
        progress = dataTag.getInt("Progress")
        speed = dataTag.getInt("Speed")
        for (key in dataTag.getKeys()) {
            val pointTag: NbtCompound = dataTag.getCompound(key)
            nodesToAdd.add(BlockPos.fromLong(pointTag.getLong("Pos")))
        }
    }

    fun getParticles(): MutableMap<UUID, MutableSet<BlockPos>> {
        return particles
    }

    fun getParticles(uuid: UUID): MutableSet<BlockPos> {
        return particles[uuid]!!
    }

    fun getCooldowns(): MutableMap<UUID, Int> {
        return cooldowns
    }

    companion object {
        operator fun <V> get(v: V): WorldGasComponent? {
            return try {
                HarmfulGasComponents.WORLD_GAS_COMPONENT.get(v)
            } catch (justShutUpAlready: Exception) {
                null
            }
        }
    }
}