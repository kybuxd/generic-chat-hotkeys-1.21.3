package dev.kybu.genericchathotkeys.mixins;

import dev.kybu.genericchathotkeys.GenericChatHotkeysMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class TutorialMessageMixin {

    @Inject(at = @At("TAIL"), method = "onGameJoin")
    private void onGameJoin(final GameJoinS2CPacket gameJoinS2CPacket, final CallbackInfo callbackInfo) {
        if(MinecraftClient.getInstance().player == null) {
            return;
        }

        if(GenericChatHotkeysMain.getInstance().getHotkeysConfig().getHotkeys().stream().anyMatch(e -> e.name().equalsIgnoreCase("test"))) {
            Text message =
                    Text.literal("Hi! Thank you for the generic chat hotkeys mod! This is probably your first startup, so here's a quick tutorial on how to use the mod: ")
                    .append(Text.literal("\n"))
                    .append(Text.literal("To edit your hotkeys, click on "))
                    .append(Text.literal("this text")
                            .styled(style -> style
                                    .withColor(TextColor.fromFormatting(Formatting.AQUA))
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, GenericChatHotkeysMain.getInstance().getConfigFile().getAbsolutePath()))
                            )
                    )
                    .append(Text.literal(" or go to the config folder and open " + GenericChatHotkeysMain.getInstance().getConfigFile().getName() + " in a text editor."));
            MinecraftClient.getInstance().player.sendMessage(message, false);

            MinecraftClient.getInstance().player.sendMessage(Text.literal("If you change or register a keybind, make sure to restart the client!"), false);
            MinecraftClient.getInstance().player.sendMessage(Text.literal("Once you've registered the keybinds, you can edit the mappings in the normal minecraft controls!"), false);
            MinecraftClient.getInstance().player.sendMessage(Text.literal("And that's it already! If you don't want this message on every startup, start editing the config and remove the test entry! :D"), false);
        }
    }

}
