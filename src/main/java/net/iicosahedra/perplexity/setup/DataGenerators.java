package net.iicosahedra.perplexity.setup;

import net.iicosahedra.perplexity.Perplexity;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(Perplexity.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
       // generator.addProvider(event.includeServer(), new ModRecipes(generator));
        ModBlockTags blockTags = new ModBlockTags(generator, event.getLookupProvider() ,event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new LootTableProvider(event.getGenerator().getPackOutput(), Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModLootTables::new, LootContextParamSets.BLOCK)), event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new ModItemTags(generator, event.getLookupProvider(), blockTags.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModBlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(generator, "en_us"));
    }
}

class ModBlockStates extends BlockStateProvider {
    public ModBlockStates(DataGenerator gen, ExistingFileHelper helper){
        super(gen.getPackOutput(), Perplexity.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels(){

    }
}

class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper){
        super(generator.getPackOutput(), Perplexity.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){

    }
}

class ModBlockTags extends BlockTagsProvider {
    public ModBlockTags(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider,ExistingFileHelper existingFileHelper){
        super(generator.getPackOutput(), lookupProvider ,Perplexity.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}

class ModItemTags extends ItemTagsProvider{
    public ModItemTags(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper){
        super(generator.getPackOutput(), lookupProvider, blockTagsProvider, Perplexity.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}

class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator generator, String locale){
        super(generator.getPackOutput(), Perplexity.MODID, locale);
    }

    @Override
    protected void addTranslations(){
        add(Registration.CREATIVE_MODE_TABS.getNamespace(), "Arcanism");
    }
}

class ModRecipes extends RecipeProvider{
    public ModRecipes(DataGenerator generator, CompletableFuture<HolderLookup.Provider> completableFuture){
        super(generator.getPackOutput(), completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {

    }
}

class ModLootTables extends VanillaBlockLoot{
    @Override
    protected void generate() {
        super.generate();
    }
}