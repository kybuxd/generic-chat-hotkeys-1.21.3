package dev.kybu.genericchathotkeys.mixins;

import dev.kybu.genericchathotkeys.GenericChatHotkeysMain;
import dev.kybu.genericchathotkeys.data.Hotkey;
import dev.kybu.genericchathotkeys.helpers.HotkeyHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftClient.class)
public class HotkeyPressTickMixin {

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(final CallbackInfo callbackInfo) {
        if(MinecraftClient.getInstance() == null) {
            return;
        }

        final ClientPlayerEntity clientPlayerEntity = MinecraftClient.getInstance().player;
        if(clientPlayerEntity == null) {
            return;
        }

        for (final Map.Entry<Hotkey, KeyBinding> hotkey : Hotkey.getRegisteredHotkeys().entrySet()) {
            if(hotkey.getValue().isPressed()) {
                if(HotkeyHelper.isDebugMode()) {
                    GenericChatHotkeysMain.getInstance().getLogger().info("Hotkey pressed: {}", hotkey.getKey().name());
                }
                hotkey.getKey().trigger(clientPlayerEntity);
            }
        }
    }

}
