package net.iicosahedra.perplexity.setup;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.ability.*;
import net.iicosahedra.perplexity.effect.*;
import net.iicosahedra.perplexity.entity.RocketbeltProjectileEntity;
import net.iicosahedra.perplexity.item.*;
import net.iicosahedra.perplexity.loot.AddItemModifier;
import net.iicosahedra.perplexity.util.ResourceLoc;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;


public class Registration {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Perplexity.MODID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Perplexity.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Perplexity.MODID);
    public static final DeferredRegister<DataComponentType<?>> DATA_TYPES = DeferredRegister
            .createDataComponents(ResourceKey.createRegistryKey(ResourceLoc.create("data_types")),Perplexity.MODID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Perplexity.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Perplexity.MODID);

    public static final ResourceKey<Registry<ActiveAbility>> ABILITY_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLoc.create("abilities"));
    public static Registry<ActiveAbility> ABILITY_REGISTRY = new RegistryBuilder<ActiveAbility>(ABILITY_REGISTRY_KEY).defaultKey(ResourceLoc.create("empty")).sync(true).create();
    public static final DeferredRegister<ActiveAbility> ABILITIES = DeferredRegister.create(ABILITY_REGISTRY, Perplexity.MODID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS_SER = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Perplexity.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Perplexity.MODID);

    public static void init(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
        ITEMS.register(modEventBus);
        EFFECTS.register(modEventBus);
        DATA_TYPES.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
        ABILITIES.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        LOOT_MODIFIERS_SER.register(modEventBus);
        CREATIVE_MODE_TAB.register(modEventBus);
    }

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name, UnaryOperator<DataComponentType.Builder<T>> builderUnaryOperator){
        return DATA_TYPES.register(name, ()-> builderUnaryOperator.apply(DataComponentType.builder()).build());
    }

    //Attributes
    public static final Holder<Attribute> ABILITY_POWER = ATTRIBUTES.register(
            "ability_power",
            () -> new RangedAttribute("attribute.perplexity.player.ability_power", 0.0, 0.0, 1024.0).setSyncable(true)
    );

    public static final Holder<Attribute> ABILITY_HASTE = ATTRIBUTES.register(
            "ability_haste",
            () -> new RangedAttribute("attribute.perplexity.player.ability_haste", 0.0, 0.0, 1024.0).setSyncable(true)
    );
    public static final Holder<Attribute> CRIT_CHANCE = ATTRIBUTES.register(
            "crit_chance",
            () -> new RangedAttribute("attribute.perplexity.player.crit_chance", 0.0, 0.0, 100.0).setSyncable(true)
    );
    public static final Holder<Attribute> CRIT_DAMAGE = ATTRIBUTES.register(
            "crit_damage",
            () -> new RangedAttribute("attribute.perplexity.player.crit_damage", 0.0, 0.0, 1024.0).setSyncable(true)
    );
    public static final Holder<Attribute> LETHALITY = ATTRIBUTES.register(
            "lethality",
            () -> new RangedAttribute("attribute.perplexity.player.lethality", 0.0, 0.0, 1024.0).setSyncable(true)
    );
    public static final Holder<Attribute> OMNIVAMP = ATTRIBUTES.register(
            "omnivamp",
            () -> new RangedAttribute("attribute.perplexity.player.omnivamp", 0.0, 0.0, 1024.0).setSyncable(true)
    );
    public static final Holder<Attribute> MAGIC_RESIST = ATTRIBUTES.register(
            "magic_resist",
            () -> new RangedAttribute("attribute.perplexity.player.magic_resist", 0.0, 0.0, 1024.0).setSyncable(true)
    );

    public static final Holder<Attribute> ARMOR_PEN = ATTRIBUTES.register(
            "armor_penetration",
            () -> new RangedAttribute("attribute.perplexity.player.armor_penetration", 0.0, 0.0, 100).setSyncable(true)
    );

    public static final Holder<Attribute> MAGIC_PEN = ATTRIBUTES.register(
            "magic_penetration",
            () -> new RangedAttribute("attribute.perplexity.player.magic_penetration", 0.0, 0.0, 100).setSyncable(true)
    );

    //Items
    public static final Holder<Item> DEATHS_DANCE = ITEMS.register("deaths_dance_item", DeathsDanceItem::new);
    public static final Holder<Item> DIVINE_SUNDERER = ITEMS.register("divine_sunderer_item", DivineSundererItem::new);
    public static final Holder<Item> NAVORI_FLICKERBLADE = ITEMS.register("navori_flickerblade_item", NavoriFlickerbladeItem::new);
    public static final Holder<Item> WARMOGS_ARMOR = ITEMS.register("warmogs_armor_item", WarmogsArmorItem::new);
    public static final Holder<Item> KRAKEN_SLAYER = ITEMS.register("kraken_slayer_item", KrakenSlayerItem::new);
    public static final Holder<Item> INFINITY_EDGE = ITEMS.register("infinity_edge_item", InfinityEdgeItem::new);
    public static final Holder<Item> TRINITY_FORCE = ITEMS.register("trinity_force_item", TrinityForceItem::new);
    public static final Holder<Item> THE_COLLECTOR = ITEMS.register("the_collector_item", TheCollectorItem::new);
    public static final Holder<Item> DUSKBLADE = ITEMS.register("duskblade_item", DuskbladeOfDraktharrItem::new);
    public static final Holder<Item> STORMRAZOR = ITEMS.register("stormrazor_item", StormrazorItem::new);
    public static final Holder<Item> RAPIDFIRECANNON = ITEMS.register("rapid_firecannon_item", RapidFirecannonItem::new);
    public static final Holder<Item> ECLIPSE = ITEMS.register("eclipse_item", EclipseItem::new);
    public static final Holder<Item> IMMORTAL_SHIELDBOW = ITEMS.register("immortal_shieldbow_item", ImmortalShieldbowItem::new);
    public static final Holder<Item> ESSENCE_REAVER = ITEMS.register("essence_reaver_item", EssenceReaverItem::new);
    public static final Holder<Item> HEXTECH_GUNBLADE = ITEMS.register("hextech_gunblade_item", HextechGunbladeItem::new);
    public static final Holder<Item> HEXTECH_ROCKETBELT = ITEMS.register("hextech_rocketbelt_item", HextechRocketbeltItem::new);
    public static final Holder<Item> YOUMUUS_GHOSTBLADE = ITEMS.register("youmuus_ghostblade_item", YoumuusGhostbladeItem::new);
    public static final Holder<Item> PROWLERS_CLAW = ITEMS.register("prowlers_claw_item", ProwlersClawItem::new);
    public static final Holder<Item> SPECTRAL_CUTLASS = ITEMS.register("spectral_cutlass_item", SpectralCutlassItem::new);
    public static final Holder<Item> GOREDRINKER = ITEMS.register("goredrinker_item", GoredrinkerItem::new);
    public static final Holder<Item> STRIDEBREAKER = ITEMS.register("stridebreaker_item", StridebreakerItem::new);
    public static final Holder<Item> RANDUINS_OMEN = ITEMS.register("randuins_omen_item", RanduinsOmenItem::new);
    public static final Holder<Item> GARGOYLE_STONEPLATE = ITEMS.register("gargoyle_stoneplate_item", GargoyleStoneplateItem::new);
    public static final Holder<Item> NASHORS_TOOTH_ITEM = ITEMS.register("nashors_tooth_item", NashorsToothItem::new);
    public static final Holder<Item> RABADONS_DEATHCAP_ITEM = ITEMS.register("rabadons_deathcap_item", RabadonsDeathcapItem::new);
    public static final Holder<Item> RIFTMAKER_ITEM = ITEMS.register("riftmaker_item", RiftmakerItem::new);
    public static final Holder<Item> SERAPHS_ITEM = ITEMS.register("seraphs_embrace_item", SeraphsEmbraceItem::new);
    public static final Holder<Item> LICH_BANE_ITEM = ITEMS.register("lich_bane_item", LichBaneItem::new);
    public static final Holder<Item> MORELLONOMICON_ITEM = ITEMS.register("morellonomicon_item", MorellonomiconItem::new);
    public static final Holder<Item> CHEMPUNK_CHAINSWORD_ITEM = ITEMS.register("chempunk_chainsword_item", ChempunkChainswordItem::new);
    public static final Holder<Item> THORNMAIL_ITEM = ITEMS.register("thornmail_item", ThornmailItem::new);
    public static final Holder<Item> VOID_STAFF_ITEM = ITEMS.register("void_staff_item", VoidStaffItem::new);
    public static final Holder<Item> PERPLEXITY_ITEM = ITEMS.register("perplexity_item", PerplexityItem::new);
    public static final Holder<Item> TWILIGHTS_EDGE_ITEM = ITEMS.register("twilights_edge_item", TwilightsEdgeItem::new);

    //Effects
    public static final Holder<MobEffect> DEATHS_DANCE_EFFECT = EFFECTS.register("ignore_pain", DeathsDanceEffect::new);
    public static final Holder<MobEffect> DIVINE_SUNDERER_EFFECT = EFFECTS.register("divine_spellblade", DivineSundererEffect::new);
    public static final Holder<MobEffect> NAVORI_EFFECT = EFFECTS.register("transcendence", NavoriFlickerBladeEffect::new);
    public static final Holder<MobEffect> WARMOGS_EFFECT = EFFECTS.register("warmogs_heart", WarmogsArmorEffect::new);
    public static final Holder<MobEffect> KRAKEN_EFFECT = EFFECTS.register("bring_it_down", KrakenSlayerEffect::new);
    public static final Holder<MobEffect> TRINITY_EFFECT = EFFECTS.register("trinity_spellblade", TrinityForceEffect::new);
    public static final Holder<MobEffect> COLLECTOR_EFFECT = EFFECTS.register("death", TheCollectorEffect::new);
    public static final Holder<MobEffect> DUSKBLADE_EFFECT = EFFECTS.register("nightstalker", DuskbladeOfDraktharrEffect::new);
    public static final Holder<MobEffect> ENERGIZE = EFFECTS.register("energize", EnergizeEffect::new);
    public static final Holder<MobEffect> STORMRAZOR_EFFECT = EFFECTS.register("bolt", StormrazorEffect::new);
    public static final Holder<MobEffect> RAPIDFIRECANNON_EFFECT = EFFECTS.register("sharpshooter", RapidFirecannonEffect::new);
    public static final Holder<MobEffect> ECLIPSE_EFFECT = EFFECTS.register("ever_rising_moon", EclipseEffect::new);
    public static final Holder<MobEffect> SHIELDBOW_EFFECT = EFFECTS.register("lifeline", ImmortalShieldbowEffect::new);
    public static final Holder<MobEffect> ESSENCE_REAVER_EFFECT = EFFECTS.register("essence_drain", EssenceReaverEffect::new);
    public static final Holder<MobEffect> OMEN_EFFECT = EFFECTS.register("critical_resilience", RanduinsOmenEffect::new);
    public static final Holder<MobEffect> NASHORS_EFFECT = EFFECTS.register("icathian_bite", NashorsToothEffect::new);
    public static final Holder<MobEffect> RIFT_EFFECT = EFFECTS.register("void_corruption", RiftmakerEffect::new);
    public static final Holder<MobEffect> SERAPHS_EFFECT = EFFECTS.register("mana_lifeline", SeraphsEmbraceEffect::new);
    public static final Holder<MobEffect> LICH_EFFECT = EFFECTS.register("lich_spellblade", LichBaneEffect::new);
    public static final Holder<MobEffect> GRIEVOUS_WOUNDS_EFFECT = EFFECTS.register("grievous_wounds", GrievousWoundsEffect::new);
    public static final Holder<MobEffect> MORELLO_EFFECT = EFFECTS.register("morello_wounds", MorellonomiconEffect::new);
    public static final Holder<MobEffect> CHAINSWORD_EFFECT = EFFECTS.register("hackshorn", ChempunkChainswordEffect::new);
    public static final Holder<MobEffect> THORNMAIL_EFFECT = EFFECTS.register("thorns", ThornmailEffect::new);
    public static final Holder<MobEffect> PERPLEXITY_EFFECT = EFFECTS.register("giant_slayer", PerplexityEffect::new);
    public static final Holder<MobEffect> TWILIGHTS_EFFECT = EFFECTS.register("the_path_between", TwilightsEdgeEffect::new);

    //Abilities
    public static final Holder<ActiveAbility> NO_ABILITY = ABILITIES.register("no_ability", NoAbility::new);
    public static final Holder<ActiveAbility> GUNBLADE_ABILITY = ABILITIES.register("lightning_bolt", HextechGunbladeAbility::new);
    public static final Holder<ActiveAbility> ROCKETBELT_ABILITY = ABILITIES.register("supersonic", HextechRocketbeltAbility::new);
    public static final Holder<ActiveAbility> YOUMUUS_ABILITY = ABILITIES.register("wraith_step", YoumuusGhostbladeAbility::new);
    public static final Holder<ActiveAbility> PROWLERS_ABILITY = ABILITIES.register("sandswipe", ProwlersClawAbility::new);
    public static final Holder<ActiveAbility> SPECTRAL_ABILITY = ABILITIES.register("soul_anchor", SpectralCutlassAbility::new);
    public static final Holder<ActiveAbility> GOREDRINKER_ABILITY = ABILITIES.register("thirsting_slash", GoredrinkerAbility::new);
    public static final Holder<ActiveAbility> STRIDEBREAKER_ABILITY = ABILITIES.register("halting_slash", StridebreakerAbility::new);
    public static final Holder<ActiveAbility> OMEN_ABILITY = ABILITIES.register("humility", RanduinsOmenAbility::new);
    public static final Holder<ActiveAbility> GARGOYLE_ABILITY = ABILITIES.register("unbreakable", GargoyleStoneplateAbility::new);
    public static final Holder<ActiveAbility> TWILIGHTS_ABILITY = ABILITIES.register("the_path_between", TwilightsEdgeAbility::new);

    //Attachments
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> MANA = ATTACHMENT_TYPES.register("mana", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DEATHS_DANCE_STACKS = ATTACHMENT_TYPES.register("deaths_dance_stacks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DEATHS_DANCE_STORED_DAMAGE = ATTACHMENT_TYPES.register("deaths_dance_stored_damage", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DIVINE_SUNDERER_COOLDOWN = ATTACHMENT_TYPES.register("divine_sunderer_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> WARMOGS_REGEN_COOLDOWN = ATTACHMENT_TYPES.register("warmogs_regen_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> WARMOGS_COMBAT_COOLDOWN = ATTACHMENT_TYPES.register("warmogs_combat_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> KRAKEN_SLAYER_STACKS = ATTACHMENT_TYPES.register("kraken_slayer_stacks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TRIFORCE_COOLDOWN = ATTACHMENT_TYPES.register("triforce_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DUSKBLADE_COOLDOWN = ATTACHMENT_TYPES.register("duskblade_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DUSKBLADE_IMMUNITY = ATTACHMENT_TYPES.register("duskblade_immunity", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ENERGIZE_STACKS = ATTACHMENT_TYPES.register("energize_stacks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ECLIPSE_COOLDOWN = ATTACHMENT_TYPES.register("eclipse_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> SHIELDBOW_COOLDOWN = ATTACHMENT_TYPES.register("shieldbow_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GUNBLADE_COOLDOWN = ATTACHMENT_TYPES.register("gunblade_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ROCKETBELT_COOLDOWN = ATTACHMENT_TYPES.register("rocketbelt_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> YOUMUUS_COOLDOWN = ATTACHMENT_TYPES.register("youmuus_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> PROWLERS_COOLDOWN = ATTACHMENT_TYPES.register("prowlers_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> SPECTRAL_COOLDOWN = ATTACHMENT_TYPES.register("spectral_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GOREDRINKER_COOLDOWN = ATTACHMENT_TYPES.register("goredrinker_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> STRIDEBREAKER_COOLDOWN = ATTACHMENT_TYPES.register("stridebreaker_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> OMEN_COOLDOWN = ATTACHMENT_TYPES.register("omen_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GARGOYLE_COOLDOWN = ATTACHMENT_TYPES.register("gargoyle_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RIFT_STACKS = ATTACHMENT_TYPES.register("rift_stacks", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RIFT_COOLDOWN = ATTACHMENT_TYPES.register("rift_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> SERAPHS_COOLDOWN = ATTACHMENT_TYPES.register("seraphs_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> LICH_FLAG = ATTACHMENT_TYPES.register("lich_flag", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> TWILIGHTS_COOLDOWN = ATTACHMENT_TYPES.register("twilights_cooldown", () -> AttachmentType.<Integer>builder(() -> 0).serialize(Codec.INT).copyOnDeath().build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> ABILITY_SLOT_1 = ATTACHMENT_TYPES.register("ability_slot_1", () -> AttachmentType.<ResourceLocation>builder(() -> NO_ABILITY.unwrapKey().get().location()).serialize(ResourceLocation.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> ABILITY_SLOT_2 = ATTACHMENT_TYPES.register("ability_slot_2", () -> AttachmentType.<ResourceLocation>builder(() -> NO_ABILITY.unwrapKey().get().location()).serialize(ResourceLocation.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> ABILITY_SLOT_3 = ATTACHMENT_TYPES.register("ability_slot_3", () -> AttachmentType.<ResourceLocation>builder(() -> NO_ABILITY.unwrapKey().get().location()).serialize(ResourceLocation.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> ABILITY_SLOT_4 = ATTACHMENT_TYPES.register("ability_slot_4", () -> AttachmentType.<ResourceLocation>builder(() -> NO_ABILITY.unwrapKey().get().location()).serialize(ResourceLocation.CODEC).build());

    //Entities
    public static DeferredHolder<EntityType<?>, EntityType<RocketbeltProjectileEntity>> ROCKETBELT_PROJ = ENTITY_TYPES.register("rocketbelt_proj",()->
            EntityType.Builder.<RocketbeltProjectileEntity>of(RocketbeltProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).noSave()
                    .setTrackingRange(32).fireImmune()
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(120)
                    .build(ResourceLoc.create("rocketbelt_proj").toString())
    );

    //Loot Modifier
    public static final Holder<MapCodec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIERS_SER.register("add_item", AddItemModifier.CODEC);

    public static final Supplier<CreativeModeTab> TAB = CREATIVE_MODE_TAB.register("perplexity_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(PERPLEXITY_ITEM.value()))
                    .title(Component.translatable("creativetab.perplexity"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(DEATHS_DANCE.value());
                        output.accept(CHEMPUNK_CHAINSWORD_ITEM.value());
                        output.accept(DIVINE_SUNDERER.value());
                        output.accept(DUSKBLADE.value());
                        output.accept(ECLIPSE.value());
                        output.accept(ESSENCE_REAVER.value());
                        output.accept(GARGOYLE_STONEPLATE.value());
                        output.accept(GOREDRINKER.value());
                        output.accept(HEXTECH_GUNBLADE.value());
                        output.accept(HEXTECH_ROCKETBELT.value());
                        output.accept(IMMORTAL_SHIELDBOW.value());
                        output.accept(INFINITY_EDGE.value());
                        output.accept(KRAKEN_SLAYER.value());
                        output.accept(LICH_BANE_ITEM.value());
                        output.accept(MORELLONOMICON_ITEM.value());
                        output.accept(NASHORS_TOOTH_ITEM.value());
                        output.accept(NAVORI_FLICKERBLADE.value());
                        output.accept(PERPLEXITY_ITEM.value());
                        output.accept(PROWLERS_CLAW.value());
                        output.accept(RABADONS_DEATHCAP_ITEM.value());
                        output.accept(RANDUINS_OMEN.value());
                        output.accept(RAPIDFIRECANNON.value());
                        output.accept(RIFTMAKER_ITEM.value());
                        output.accept(SERAPHS_ITEM.value());
                        output.accept(SPECTRAL_CUTLASS.value());
                        output.accept(STORMRAZOR.value());
                        output.accept(STRIDEBREAKER.value());
                        output.accept(THE_COLLECTOR.value());
                        output.accept(THORNMAIL_ITEM.value());
                        output.accept(TRINITY_FORCE.value());
                        output.accept(TWILIGHTS_EDGE_ITEM.value());
                        output.accept(VOID_STAFF_ITEM.value());
                        output.accept(WARMOGS_ARMOR.value());
                        output.accept(YOUMUUS_GHOSTBLADE.value());
                    }).build());
}