package com.jacobvm.mcvm.mixin;

import com.jacobvm.mcvm.McvmClient;
import com.jacobvm.mcvm.McvmOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public abstract class OptionsMixin extends Screen {
    protected OptionsMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void createOptions(CallbackInfo ci) {
        if (McvmClient.controlsPageEnabled.getOption()) {
            int i = this.width / 2 - 155;
            int k = this.height / 6 - 12;

            this.addDrawableChild(new ButtonWidget(i, k + 48 + 24, 150, 20, new TranslatableText("MCVM Client Settings"), button -> this.client.setScreen(new McvmOptionsScreen(this))));
        }
    }
}
