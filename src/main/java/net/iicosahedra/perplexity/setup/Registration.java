package net.iicosahedra.perplexity.setup;

import com.mojang.serialization.Codec;
import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.common.block.*;
import net.iicosahedra.perplexity.common.item.TestItem;
import net.iicosahedra.perplexity.common.item.TestSetItem;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;


public class Registration {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Perplexity.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Perplexity.MODID);

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Perplexity.MODID);

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Perplexity.MODID);

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Perplexity.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Perplexity.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Perplexity.MODID);

    public static void init(IEventBus modEventBus){
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        SPELLS.register(modEventBus);
        DATA_COMPONENTS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
    }

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.register("test_item", () ->
            new TestItem(new Item.Properties())
    );

    public static final DeferredItem<Item> TEST_SET_ITEM = ITEMS.register("test_set_item", () ->
            new TestSetItem(new Item.Properties()));

    //Entities
    public static final DeferredHolder<EntityType<?>, EntityType<EntityProj>> SPELL_PROJ = ENTITY_TYPES.register("spell_proj", ()->
            EntityType.Builder.<EntityProj>of(EntityProj::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).noSave()
                    .setTrackingRange(32).fireImmune()
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(120)
                    .build(ResourceLoc.create("spell_proj").toString()));


    //Blocks
    public static final DeferredBlock<SpellStructureHead> SPELL_STRUCTURE_HEAD = BLOCKS.register("spell_structure_head", SpellStructureHead::new);
    public static final DeferredItem<BlockItem> SPELL_STRUCTURE_HEAD_ITEM = ITEMS.registerSimpleBlockItem("spell_structure_head", SPELL_STRUCTURE_HEAD);

    public static final DeferredBlock<SpellStructureTail> SPELL_STRUCTURE_TAIL = BLOCKS.register("spell_structure_tail", SpellStructureTail::new);
    public static final DeferredItem<BlockItem> SPELL_STRUCTURE_TAIL_ITEM = ITEMS.registerSimpleBlockItem("spell_structure_tail", SPELL_STRUCTURE_TAIL);

    public static final DeferredBlock<InactiveCircuit> INACTIVE_CIRCUIT = BLOCKS.register("inactive_circuit", InactiveCircuit::new);
    public static final DeferredItem<BlockItem> INACTIVE_CIRCUIT_ITEM = ITEMS.registerSimpleBlockItem("inactive_circuit", INACTIVE_CIRCUIT);

    public static final DeferredBlock<ActiveCircuit> ACTIVE_CIRCUIT = BLOCKS.register("active_circuit", ActiveCircuit::new);
    public static final DeferredItem<BlockItem> ACTIVE_CIRCUIT_ITEM = ITEMS.registerSimpleBlockItem("active_circuit", ACTIVE_CIRCUIT);

    //BlockEntities
    public static final Supplier<BlockEntityType<SpellStructureHeadEntity>> SPELL_STRUCTURE_HEAD_ENTITY = BLOCK_ENTITIES.register("spell_structure_head_entity", () ->
            BlockEntityType.Builder.of(SpellStructureHeadEntity::new, SPELL_STRUCTURE_HEAD.get()).build( null));

    public static final Supplier<BlockEntityType<ActiveCircuitEntity>> ACTIVE_CIRCUIT_ENTITY = BLOCK_ENTITIES.register("active_circuit_entity", ()->
            BlockEntityType.Builder.of(ActiveCircuitEntity::new, ACTIVE_CIRCUIT.get()).build(null));

    public static final Supplier<BlockEntityType<SpellStructureTailEntity>> SPELL_STRUCTURE_TAIL_ENTITY = BLOCK_ENTITIES.register("spell_structure_tail_entity", ()->
            BlockEntityType.Builder.of(SpellStructureTailEntity::new, SPELL_STRUCTURE_TAIL.get()).build(null));

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
   public static DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SPELL = DATA_COMPONENTS.register("spell_key", ()->
       DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.fromCodec(Codec.INT)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("perplexity_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.perplexity")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> TEST_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(TEST_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
}
