package net.iicosahedra.perplexity.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category.perplexity";
    public static final String KEY_ABILITY_1 = "key.perplexity.ability_1";
    public static final String KEY_ABILITY_2 = "key.perplexity.ability_2";
    public static final String KEY_ABILITY_3 = "key.perplexity.ability_3";
    public static final String KEY_ABILITY_4 = "key.perplexity.ability_4";

    public static final KeyMapping ABILITY_1 = new KeyMapping(KEY_ABILITY_1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY);
    public static final KeyMapping ABILITY_2 = new KeyMapping(KEY_ABILITY_2, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY);
    public static final KeyMapping ABILITY_3 = new KeyMapping(KEY_ABILITY_3, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY);
    public static final KeyMapping ABILITY_4 = new KeyMapping(KEY_ABILITY_4, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY);
}
