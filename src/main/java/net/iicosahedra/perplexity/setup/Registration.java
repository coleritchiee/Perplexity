package net.iicosahedra.perplexity.setup;

import com.mojang.serialization.Codec;
import cpw.mods.modlauncher.api.ITransformationService;
import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.common.block.TestBlock;
import net.iicosahedra.perplexity.common.item.TestItem;
import net.iicosahedra.perplexity.common.item.TestSetItem;
import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.components.ISpellComponent;
import net.iicosahedra.perplexity.spell.effects.EffectBreak;
import net.iicosahedra.perplexity.spell.effects.EffectDamage;
import net.iicosahedra.perplexity.spell.effects.EffectFling;
import net.iicosahedra.perplexity.spell.entity.EntityProj;
import net.iicosahedra.perplexity.spell.shapes.ShapeProj;
import net.iicosahedra.perplexity.spell.shapes.ShapeSelf;
import net.iicosahedra.perplexity.spell.shapes.ShapeTouch;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
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

import javax.swing.*;
import java.rmi.registry.RegistryHandler;
import java.util.function.Supplier;


public class Registration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Perplexity.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Perplexity.MODID);

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Perplexity.MODID);

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Perplexity.MODID);

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Perplexity.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Perplexity.MODID);
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", TestBlock::new);
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
        SPELLS.register(modEventBus);
        DATA_COMPONENTS.register(modEventBus);
    }
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.register("test_item", () ->
            new TestItem(new Item.Properties())
    );

    public static final DeferredItem<Item> TEST_SET_ITEM = ITEMS.register("test_set_item", () ->
            new TestSetItem(new Item.Properties()));

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
    public static final Supplier<AttachmentType<Integer>> TIER = ATTACHMENT_TYPES.register("tier", ()-> AttachmentType.<Integer>builder(()-> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final Supplier<AttachmentType<Integer>> AFFINITY = ATTACHMENT_TYPES.register("affinity", ()-> AttachmentType.<Integer>builder(()-> -1).serialize(Codec.INT).copyOnDeath().build());

    //spell registry
    public static final ResourceKey<Registry<ISpellComponent>> SPELL_KEY = ResourceKey.createRegistryKey(ResourceLoc.create("spell_components"));
    public static final Registry<ISpellComponent>SPELL_REGISTRY = new RegistryBuilder<>(SPELL_KEY)
            .sync(true)
            .create();

    public static final DeferredRegister<ISpellComponent> SPELLS = DeferredRegister.create(SPELL_REGISTRY,Perplexity.MODID);
    public static final Supplier<ISpellComponent> EFFECT_BREAK = SPELLS.register("effect.break", EffectBreak::new);
    public static final Supplier<ISpellComponent> EFFECT_DAMAGE = SPELLS.register("effect.damage", EffectDamage::new);
    public static final Supplier<ISpellComponent> EFFECT_FLING = SPELLS.register("effect.fling", EffectFling::new);
    public static final Supplier<ISpellComponent> SHAPE_PROJ = SPELLS.register("shape.proj", ShapeProj::new);
    public static final Supplier<ISpellComponent> SHAPE_SELF = SPELLS.register("shape.self", ShapeSelf::new);
    public static final Supplier<ISpellComponent> SHAPE_TOUCH = SPELLS.register("shape.touch", ShapeTouch::new);

    //Data Components
   public static DeferredHolder<DataComponentType<?>, DataComponentType<Spell>> SPELL = DATA_COMPONENTS.register("spell", ()->
       DataComponentType.<Spell>builder().persistent(Spell.SPELL_CODEC).networkSynchronized(ByteBufCodecs.fromCodec(Spell.SPELL_CODEC)).build());

}
