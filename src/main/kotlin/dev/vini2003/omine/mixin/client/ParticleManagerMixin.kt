package dev.vini2003.omine.mixin.client

import com.google.common.collect.EvictingQueue
import dev.vini2003.omine.client.util.GasParticleUtils
import dev.vini2003.omine.registry.client.HarmfulGasTextureSheets
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleManager
import net.minecraft.client.particle.ParticleTextureSheet
import org.spongepowered.asm.mixin.Final
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Mutable
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import java.util.*

@Mixin(ParticleManager::class)
class ParticleManagerMixin {
    @Shadow
    @Final
    private val particles: Map<ParticleTextureSheet, Queue<Particle>>? = null
    @Inject(at = [At("HEAD")], method = ["tick"])
    fun harmfulgas_tick(ci: CallbackInfo?) {
        if (GasParticleUtils.shouldClear) {
            if (particles != null) {
                if (particles.containsKey(HarmfulGasTextureSheets.GAS)) {
                    particles[HarmfulGasTextureSheets.GAS]!!.clear()
                }
            }
            GasParticleUtils.shouldClear = false
        }
    }

    private companion object {
        @Final
        @Shadow
        @Mutable
        private var PARTICLE_TEXTURE_SHEETS: MutableList<ParticleTextureSheet>? = null

        @Redirect(
            at = At(
                value = "INVOKE",
                target = "Lcom/google/common/collect/EvictingQueue;create(I)Lcom/google/common/collect/EvictingQueue;"
            ), method = ["method_18125"]
        )
        private fun <E> harmfulgas_method_18125(maxSize: Int): EvictingQueue<E> {
            return EvictingQueue.create(131072)
        }

        init {
            val newSheets: MutableList<ParticleTextureSheet> = ArrayList()
            PARTICLE_TEXTURE_SHEETS?.let { newSheets.addAll(it) }
            newSheets.add(HarmfulGasTextureSheets.GAS)
            PARTICLE_TEXTURE_SHEETS = newSheets
        }
    }
}