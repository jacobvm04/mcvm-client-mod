package net.fabricmc.example.mixin;

import net.fabricmc.example.McvmClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseScrollMixin {
    private static final int fovChangeMultiplier = 2;

    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (McvmClient.zoomScrollEnabled.getOption() && McvmClient.zoomEnabled) {
            double newFov = McvmClient.zoomFov.getOption() - fovChangeMultiplier * vertical;
            if (newFov >= McvmClient.zoomFovOption.getMin() && newFov <= McvmClient.zoomFovOption.getMax())
                McvmClient.zoomFov.setOption(McvmClient.zoomFov.getOption() - fovChangeMultiplier * vertical);

            ci.cancel();
        }
    }
}
