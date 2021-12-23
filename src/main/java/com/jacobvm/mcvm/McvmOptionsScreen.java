package com.jacobvm.mcvm;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class McvmOptionsScreen extends GameOptionsScreen {
    public McvmOptionsScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, new TranslatableText("MCVM Settings"));
    }

    @Override
    protected void init() {
        super.init();
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;

        this.addDrawableChild(McvmClient.zoomToggledOption.createButton(this.gameOptions, i, k, 150));
        this.addDrawableChild(McvmClient.zoomFovOption.createButton(this.gameOptions, j, k, 150));
        this.addDrawableChild(McvmClient.zoomScrollEnabledOption.createButton(this.gameOptions, i, k + 24, 150));
        this.addDrawableChild(McvmClient.fovLockedOption.createButton(this.gameOptions, j, k + 24, 150));
        this.addDrawableChild(McvmClient.controlsPageEnabledOption.createButton(this.gameOptions, i, k + 24 + 24, 150));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 29, 200, 20, ScreenTexts.DONE, button -> this.client.setScreen(this.parent)));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        ControlsOptionsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
