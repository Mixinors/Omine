package dev.vini2003.bodyshufflelite.mixin.client;

import dev.vini2003.bodyshufflelite.client.model.RootPartProvider;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
		QuadrupedEntityModel.class
)
public class QuadrupedAnimalModelMixin implements RootPartProvider {
	private ModelPart bsl_root;
	
	@Inject(at = @At("RETURN"), method = "<init>")
	private void bsl_init(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset, CallbackInfo ci) {
		bsl_root = root;
	}
	
	@NotNull
	@Override
	public ModelPart getBsl_root() {
		return bsl_root;
	}
}
