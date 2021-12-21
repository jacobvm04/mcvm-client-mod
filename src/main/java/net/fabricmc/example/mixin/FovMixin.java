package net.fabricmc.example.mixin;

import net.fabricmc.example.McvmClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class FovMixin {
	@Inject(at = @At("RETURN"), method = "getFov", cancellable = true)
	private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
		if (changingFov) {
			if (McvmClient.zoomEnabled)
				cir.setReturnValue(McvmClient.zoomFov.getOption());
			else if (McvmClient.fovLocked.getOption())
				cir.setReturnValue(((GameRendererAccessor)this).getClient().options.fov);
		}
	}
}
