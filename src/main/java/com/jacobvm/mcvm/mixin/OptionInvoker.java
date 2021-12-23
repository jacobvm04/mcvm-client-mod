package com.jacobvm.mcvm.mixin;

import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Option.class)
public interface OptionInvoker {
    @Invoker
    Text invokeGetGenericLabel(int value);
}
