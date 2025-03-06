package dev.kybu.genericchathotkeys.helpers;

import org.lwjgl.glfw.GLFW;

public class HotkeyHelper {

    private static boolean DEBUG_MODE = false;

    public static int getGLFWKeyCode(String button) {
        if (button == null || button.isEmpty())
            return GLFW.GLFW_KEY_UNKNOWN;
        char c = Character.toUpperCase(button.charAt(0));
        return GLFW.GLFW_KEY_A + (c - 'A');
    }

    public static boolean toggleDebugMode() {
        DEBUG_MODE = !DEBUG_MODE;
        return DEBUG_MODE;
    }

    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

}
