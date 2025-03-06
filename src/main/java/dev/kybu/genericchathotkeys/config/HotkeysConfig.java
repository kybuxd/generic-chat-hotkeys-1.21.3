package dev.kybu.genericchathotkeys.config;

import dev.kybu.genericchathotkeys.data.Hotkey;

import java.util.HashSet;
import java.util.Set;

public class HotkeysConfig {

    private Set<Hotkey> hotkeys = new HashSet<>() {{
        add(new Hotkey("test", "B", "This is a test! Change me in the config!", true));
    }};

    public Set<Hotkey> getHotkeys() {
        return hotkeys;
    }
}
