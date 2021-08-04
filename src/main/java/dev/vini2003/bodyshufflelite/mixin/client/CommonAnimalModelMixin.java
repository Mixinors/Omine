package dev.vini2003.bodyshufflelite.mixin.client;

import dev.vini2003.bodyshufflelite.client.model.RootPartProvider;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.*;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({
		AxolotlEntityModel.class,
		BeeEntityModel.class,
		BipedEntityModel.class,
		ChickenEntityModel.class,
		ElytraEntityModel.class,
		FoxEntityModel.class,
		HoglinEntityModel.class,
		HorseEntityModel.class,
		OcelotEntityModel.class
})
public class CommonAnimalModelMixin implements RootPartProvider {
	private ModelPart bsl_root;
	
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/client/model/ModelPart;)V")
	private void bsl_init(ModelPart root, CallbackInfo ci) {
		bsl_root = root;
	}
	
	@NotNull
	@Override
	public ModelPart getBsl_root() {
		return bsl_root;
	}
}
