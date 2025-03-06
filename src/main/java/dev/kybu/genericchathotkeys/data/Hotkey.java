package dev.kybu.genericchathotkeys.data;

import dev.kybu.genericchathotkeys.GenericChatHotkeysMain;
import dev.kybu.genericchathotkeys.helpers.HotkeyHelper;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public record Hotkey(String name, String defaultMapping, String message, boolean applyCooldown) {

    private static final Map<Hotkey, KeyBinding> REGISTERED_HOTKEYS = new HashMap<>();
    private static final AtomicLong LAST_PRESSED_HOTKEY = new AtomicLong(System.currentTimeMillis());

    public void registerKeybinding() {
        final KeyBinding keyBinding = new KeyBinding("generic-chat-hotkeys.key." + name, HotkeyHelper.getGLFWKeyCode(defaultMapping), "category.generic-chat-hotkeys");
        KeyBindingHelper.registerKeyBinding(keyBinding);
        REGISTERED_HOTKEYS.put(this, keyBinding);
        GenericChatHotkeysMain.getInstance().getLogger().info("Registered keybinding for hotkey: " + name);
    }

    public void trigger(final ClientPlayerEntity clientPlayerEntity) {
        if(System.currentTimeMillis() - LAST_PRESSED_HOTKEY.get() <= 500) {
            return;
        }

        if(applyCooldown) {
            LAST_PRESSED_HOTKEY.set(System.currentTimeMillis());
        }

        if(this.message.equalsIgnoreCase("debug-mode")) {
            clientPlayerEntity.sendMessage(Text.literal("§cGCH: Debug Mode " + (HotkeyHelper.toggleDebugMode() ? "enabled" : "disabled")), true);
            return;
        }

        if(this.message.startsWith("/")) {
            clientPlayerEntity.networkHandler.sendCommand(this.message.replace("/", ""));
            return;
        }
        clientPlayerEntity.networkHandler.sendChatMessage(this.message);
    }

    public static Map<Hotkey, KeyBinding> getRegisteredHotkeys() {
        return REGISTERED_HOTKEYS;
    }

}
