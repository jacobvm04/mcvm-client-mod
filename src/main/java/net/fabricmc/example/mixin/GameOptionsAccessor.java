package net.fabricmc.example.mixin;

import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptionsScreen.class)
public interface GameOptionsAccessor {
    @Accessor
    public GameOptions getGameOptions();
}
