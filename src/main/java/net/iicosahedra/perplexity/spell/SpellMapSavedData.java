package net.iicosahedra.perplexity.spell;

import com.mojang.serialization.DataResult;
import net.iicosahedra.perplexity.common.block.SpellStructureHead;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

public class SpellMapSavedData extends SavedData {
    Map<Integer, Spell> dataMap = new HashMap<>();
    public static String name  = "spell_data_map";

    public static SpellMapSavedData create(){
        return new SpellMapSavedData();
    }

    public static SpellMapSavedData load(CompoundTag tag, HolderLookup.Provider provider){
        SpellMapSavedData data = new SpellMapSavedData();
        data.dataMap.clear();
        CompoundTag spellTag = tag.getCompound("spell_data_map");
        for(String key: spellTag.getAllKeys()){
            int spellId = Integer.parseInt(key);
            DataResult<Spell> spellDataResult = Spell.SPELL_CODEC.parse(NbtOps.INSTANCE, spellTag.getCompound(key));
            spellDataResult.resultOrPartial(error -> System.err.println("Failed to parse spell: " + error))
                    .ifPresent(spell -> data.dataMap.put(spellId, spell));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag p_77763_, HolderLookup.Provider p_323640_) {
        CompoundTag tag = new CompoundTag();
        for(Map.Entry<Integer, Spell> entry : dataMap.entrySet()){
            DataResult<Tag> spellTagResult = Spell.SPELL_CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue());
            spellTagResult.resultOrPartial()
                    .ifPresent(spellTag -> {
                        if (spellTag instanceof CompoundTag) {
                            tag.put(entry.getKey().toString(), (CompoundTag) spellTag);
                        }
                    });

        }
        tag.put("spell_data_map", tag);
        return tag;
    }

    public void addSpell(int spellId, Spell spell){
        dataMap.put(spellId, spell);
        setDirty();
    }
    public void removeSpell(int spellId){
        dataMap.remove(spellId);
        setDirty();
    }
    public Spell getSpell(int spellId){
        return dataMap.get(spellId);
    }
    public Map<Integer, Spell> getDataMap(){
        return dataMap;
    }
    public static SpellMapSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<SpellMapSavedData>(SpellMapSavedData::create, SpellMapSavedData::load), name);
    }
}
