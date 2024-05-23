package net.iicosahedra.perplexity.spell;

import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BurnoutSavedData extends SavedData {
    private static int THRESHHOLD = 100;
    private static final String name = "burnout_data";
    private final Map<UUID, Integer> manaSpent = new HashMap<>();
    private final Map<UUID, Integer> burnoutTimeRemaining = new HashMap<>();

    public static BurnoutSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<BurnoutSavedData>(BurnoutSavedData::create, BurnoutSavedData::load), name);
    }

    public static BurnoutSavedData create(){
        return new BurnoutSavedData();
    }

    public void addMana(UUID playerId, int mana) {
        manaSpent.put(playerId, manaSpent.getOrDefault(playerId, 0) + mana);
        if (manaSpent.get(playerId) >= THRESHHOLD) {
            burnoutTimeRemaining.put(playerId, 20 * 20);
        }
        setDirty();
    }

    public boolean isInBurnout(UUID playerId) {
        return burnoutTimeRemaining.getOrDefault(playerId, 0) > 0;
    }

    public void decrementBurnoutTime() {
        for (UUID playerId : burnoutTimeRemaining.keySet()) {
            int timeRemaining = burnoutTimeRemaining.get(playerId);
            if (timeRemaining > 0) {
                burnoutTimeRemaining.put(playerId, timeRemaining - 1);
            }
            if (timeRemaining <= 1) {
                manaSpent.put(playerId, 0);
                burnoutTimeRemaining.remove(playerId);
            }
        }
        setDirty();
    }

    public static BurnoutSavedData load(CompoundTag tag, HolderLookup.Provider provider) {
        BurnoutSavedData data = new BurnoutSavedData();

        CompoundTag manaTag = tag.getCompound("ManaSpent");
        data.manaSpent.clear();
        manaTag.getAllKeys().forEach(key -> data.manaSpent.put(UUID.fromString(key), manaTag.getInt(key)));

        CompoundTag burnoutTag = tag.getCompound("BurnoutTimeRemaining");
        data.burnoutTimeRemaining.clear();
        burnoutTag.getAllKeys().forEach(key -> data.burnoutTimeRemaining.put(UUID.fromString(key), burnoutTag.getInt(key)));

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider p_323640_) {
        CompoundTag manaTag = new CompoundTag();
        manaSpent.forEach((uuid, mana) -> manaTag.putInt(uuid.toString(), mana));
        tag.put("ManaSpent", manaTag);

        CompoundTag burnoutTag = new CompoundTag();
        burnoutTimeRemaining.forEach((uuid, time) -> burnoutTag.putInt(uuid.toString(), time));
        tag.put("BurnoutTimeRemaining", burnoutTag);

        return tag;
    }
}