package net.iicosahedra.perplexity.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class SpellMapSavedDataManager {
    public static SpellMapSavedDataManager INSTANCE = new SpellMapSavedDataManager();

    public final ServerLevel level;

    SpellMapSavedDataManager() {
        this.level = ServerLifecycleHooks.getCurrentServer().overworld();
    }

    public void addSpell(int key, Spell spell) {
        SpellMapSavedData data = SpellMapSavedData.get(level);
        data.addSpell(key, spell);
    }

    public void removeSpell(int key) {
        SpellMapSavedData data = SpellMapSavedData.get(level);
        data.removeSpell(key);
    }

    public Spell getSpell(Integer key) {
        if(key == null){
            return null;
        }
        SpellMapSavedData data = SpellMapSavedData.get(level);
        return data.getSpell(key);
    }

    public static int hashBlockPos(BlockPos blockPos){
        return blockPos.hashCode();
    }

}
