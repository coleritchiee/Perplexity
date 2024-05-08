package net.iicosahedra.perplexity.setup;

import com.mojang.serialization.Codec;
import cpw.mods.modlauncher.api.ITransformationService;
import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.common.item.TestItem;
import net.iicosahedra.perplexity.spell.entity.EntityProj;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;


public class Registration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Perplexity.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Perplexity.MODID);

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Perplexity.MODID);

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Perplexity.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Perplexity.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("perplexity_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.perplexity")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public static void init(IEventBus modEventBus){
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);

    }
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.register("test_item", () ->
            new TestItem(new Item.Properties())
    );

    public static final DeferredHolder<EntityType<?>, EntityType<EntityProj>> SPELL_PROJ = ENTITY_TYPES.register("spell_proj", ()->
            EntityType.Builder.<EntityProj>of(EntityProj::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).noSave()
                    .setTrackingRange(32).fireImmune()
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(120)
                    .build(ResourceLoc.create("spell_proj").toString()));

    //Stats

    public static final Supplier<AttachmentType<Integer>> MANA = ATTACHMENT_TYPES.register("mana", ()-> AttachmentType.<Integer>builder(()-> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> ABILITY_POWER = ATTACHMENT_TYPES.register("ability_power", ()-> AttachmentType.<Integer>builder(()-> 1).serialize(Codec.INT).copyOnDeath().build());

    public static final Supplier<AttachmentType<Integer>> MAGIC_RESIST = ATTACHMENT_TYPES.register("magic_resist", ()-> AttachmentType.<Integer>builder(()-> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> ABILITY_HASTE = ATTACHMENT_TYPES.register("ability_haste", ()-> AttachmentType.<Integer>builder(()-> 1).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> CRIT_CHANCE = ATTACHMENT_TYPES.register("crit_chance", ()-> AttachmentType.<Integer>builder(()-> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> LIFE_STEAL = ATTACHMENT_TYPES.register("life_steal", ()-> AttachmentType.<Integer>builder(()-> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Boolean>> SPELL_CRIT = ATTACHMENT_TYPES.register("spell_crit", ()-> AttachmentType.<Boolean>builder(()-> false).serialize(Codec.BOOL).copyOnDeath().build());
}
