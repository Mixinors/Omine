package dev.vini2003.bodyshufflelite.mixin.client;

import dev.vini2003.bodyshufflelite.client.model.RootPartProvider;
import dev.vini2003.bodyshufflelite.client.util.PartsKt;
import dev.vini2003.bodyshufflelite.common.component.BodyPartComponent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.AxolotlEntityRenderer;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	@Shadow protected M model;
	
	@Inject(at = @At("RETURN"), method = "render")
	void bodyshuffle_setModelPose(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if (BodyPartComponent.has(livingEntity)) {
			if (model instanceof SinglePartEntityModel model) {
				var part = model.getPart();
				
				var comp = BodyPartComponent.get(livingEntity);
				
				PartsKt.getParts(part).forEach(childPart -> {
					if (comp.has(childPart.getId())) {
						childPart.getPart().visible = comp.get(childPart.getId());
					}
				});
			}
			
			if (model instanceof RootPartProvider model) {
				var part = model.getBsl_root();
				
				if (part != null) {
					var comp = BodyPartComponent.get(livingEntity);
					
					PartsKt.getParts(part).forEach(childPart -> {
						if (comp.has(childPart.getId())) {
							childPart.getPart().visible = comp.get(childPart.getId());
						}
					});
				}
			}
		}
	}
}
