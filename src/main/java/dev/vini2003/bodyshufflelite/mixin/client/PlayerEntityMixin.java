package dev.vini2003.bodyshufflelite.mixin.client;

import dev.vini2003.bodyshufflelite.component.BodyParts;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	private final BodyParts bodyParts = new BodyParts();
	
	@Inject(at = @At("RETURN"), method = "writeCustomDataToNbt")
	private void bsl_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.put("BodyParts", bodyParts.toNbt());
	}
	
	@Inject(at = @At("RETURN"), method = "readCustomDataFromNbt")
	private void bsl_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		bodyParts.fromNbt(nbt.getCompound("BodyParts"));
	}
}
