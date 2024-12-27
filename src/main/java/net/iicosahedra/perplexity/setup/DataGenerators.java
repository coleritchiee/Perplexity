package net.iicosahedra.perplexity.setup;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.client.KeyBinding;
import net.iicosahedra.perplexity.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new ModLanguageProvider(packOutput, "en_us"));
        gen.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, helper));
        gen.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(packOutput, event.getLookupProvider()));

    }

}

class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Perplexity.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(Registration.DEATHS_DANCE.value());
        handheldItem(Registration.DIVINE_SUNDERER.value());
        handheldItem(Registration.NAVORI_FLICKERBLADE.value());
        handheldItem(Registration.WARMOGS_ARMOR.value());
        handheldItem(Registration.KRAKEN_SLAYER.value());
        handheldItem(Registration.INFINITY_EDGE.value());
        handheldItem(Registration.TRINITY_FORCE.value());
        handheldItem(Registration.THE_COLLECTOR.value());
        handheldItem(Registration.DUSKBLADE.value());
        handheldItem(Registration.STORMRAZOR.value());
        handheldItem(Registration.RAPIDFIRECANNON.value());
        handheldItem(Registration.ECLIPSE.value());
        handheldItem(Registration.IMMORTAL_SHIELDBOW.value());
        handheldItem(Registration.ESSENCE_REAVER.value());
        handheldItem(Registration.HEXTECH_GUNBLADE.value());
        handheldItem(Registration.HEXTECH_ROCKETBELT.value());
        handheldItem(Registration.YOUMUUS_GHOSTBLADE.value());
        handheldItem(Registration.PROWLERS_CLAW.value());
        handheldItem(Registration.SPECTRAL_CUTLASS.value());
        handheldItem(Registration.GOREDRINKER.value());
        handheldItem(Registration.STRIDEBREAKER.value());
        handheldItem(Registration.RANDUINS_OMEN.value());
        handheldItem(Registration.GARGOYLE_STONEPLATE.value());
        handheldItem(Registration.NASHORS_TOOTH_ITEM.value());
        handheldItem(Registration.RABADONS_DEATHCAP_ITEM.value());
        handheldItem(Registration.RIFTMAKER_ITEM.value());
        handheldItem(Registration.SERAPHS_ITEM.value());
        handheldItem(Registration.LICH_BANE_ITEM.value());
        handheldItem(Registration.MORELLONOMICON_ITEM.value());
        handheldItem(Registration.CHEMPUNK_CHAINSWORD_ITEM.value());
        handheldItem(Registration.THORNMAIL_ITEM.value());
        handheldItem(Registration.VOID_STAFF_ITEM.value());
        handheldItem(Registration.PERPLEXITY_ITEM.value());
        handheldItem(Registration.TWILIGHTS_EDGE_ITEM.value());
    }
}

class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Perplexity.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }
}

class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Perplexity.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(Registration.DEATHS_DANCE.value(), "Death's Dance");
        add(Registration.DEATHS_DANCE_EFFECT.value(), "Ignore Pain");
        add("tooltip.perplexity.deathsdance", "§ePassive§f: Ignore Pain \nMitigates and stores §730% of all damage§f received for two instances of damage. On the third instance of damage, receive §750% of stored damage§f.");

        add(Registration.DIVINE_SUNDERER.value(), "Divine Sunderer");
        add(Registration.DIVINE_SUNDERER_EFFECT.value(), "Divine Spellblade");
        add("tooltip.perplexity.divinesunderer","§ePassive§f: Divine Spellblade \nEvery 10 seconds your next attack will deal an additional §6160% AD§f + §24% of the targets max health§f. Additionally, heal for §664% AD§f + §21.6% of the targets max health§f.");

        add(Registration.NAVORI_FLICKERBLADE.value(), "Navori Flickerblade");
        add(Registration.NAVORI_EFFECT.value(), "Transcendence");
        add("tooltip.perplexity.navori", "§ePassive§f: Transcendence \nAttacks reduce remaining cooldowns by 15%.");

        add(Registration.WARMOGS_ARMOR.value(), "Warmog's Armor");
        add(Registration.WARMOGS_EFFECT.value(), "Warmog's Heart");
        add("tooltip.perplexity.warmogs", "§ePassive§f: Warmog's Heart \nIf you have at least §220 bonus health§f and have not taken damage in the last 6 seconds, regenerate §22.5% max health§f every second.");

        add(Registration.KRAKEN_SLAYER.value(), "Kraken Slayer");
        add(Registration.KRAKEN_EFFECT.value(), "Bring It Down");
        add("tooltip.perplexity.kraken", "§ePassive§f: Bring It Down \nEvery third attack deals an additional §74§f damage increased up to §2100% based on missing target health§f.");

        add(Registration.INFINITY_EDGE.value(), "Infinity Edge");

        add(Registration.TRINITY_FORCE.value(), "Trinity Force");
        add(Registration.TRINITY_EFFECT.value(), "Trinity Spellblade");
        add("tooltip.perplexity.triforce", "§ePassive§f: Trinity Spellblade \nEvery 10 seconds your next attack will deal an additional §6200% AD§f damage.");

        add(Registration.THE_COLLECTOR.value(), "The Collector");
        add(Registration.COLLECTOR_EFFECT.value(), "Death");
        add("tooltip.perplexity.collector", "§ePassive§f: Death \nIf you deal post-mitigation damage that would leave a entity below §25% of their maximum health§f, execute them.");

        add(Registration.DUSKBLADE.value(), "Duskblade of Draktharr");
        add(Registration.DUSKBLADE_EFFECT.value(), "Nightstalker");
        add("tooltip.perplexity.duskblade", "§ePassive§f: Nightstalker \nYou deal up to §222.5% increased damage based on target's missing health§f. Killing a player make you §4untargetable§f for 1.5 seconds.");

        add(Registration.ENERGIZE.value(), "Energized");
        add("tooltip.perplexity.energize", "§ePassive§f: Energized \nSprinting generates Energize stacks up to 100.");

        add(Registration.STORMRAZOR.value(), "Stormrazor");
        add(Registration.STORMRAZOR_EFFECT.value(), "Bolt");
        add("tooltip.perplexity.stormrazor", "§ePassive§f: Bolt \nWhen fully §eEnergized§f, your next attack deals §920 bonus magic damage§f and grants you §650% movement speed for 1.5 seconds§f.");

        add(Registration.RAPIDFIRECANNON.value(), "Rapid Firecannon");
        add(Registration.RAPIDFIRECANNON_EFFECT.value(), "Sharpshooter");
        add("tooltip.perplexity.rfc", "§ePassive§f: Sharpshooter \nWhen fully §eEnergized§f, your next attack deals §94 bonus magic damage§f. §eEnergized§f attacks grant §7200% bonus range§f.");

        add(Registration.ECLIPSE.value(), "Eclipse");
        add(Registration.ECLIPSE_EFFECT.value(), "Ever Rising Moon");
        add("tooltip.perplexity.eclipse","§ePassive§f: Ever Rising Moon \nEvery 6 seconds deal bonus damage equal to §26% of target's maximum health§f and gain §716§f + §640% AD§f absorption for 2 seconds.");

        add(Registration.IMMORTAL_SHIELDBOW.value(), "Immortal Shieldbow");
        add(Registration.SHIELDBOW_EFFECT.value(), "Lifeline");
        add("tooltip.perplexity.shieldbow","§ePassive§f: Lifeline \nIf you would take damage that would reduce you below §230% of your maximum health§f, you gain §740§f absorption for 5 seconds (60 second cooldown).");

        add(Registration.ESSENCE_REAVER.value(), "Essence Reaver");
        add(Registration.ESSENCE_REAVER_EFFECT.value(), "Essence Drain");
        add("tooltip.perplexity.essence_reaver","§ePassive§f: Essence Drain \nKilling an entity restores §bmana proportional to your current mana§f and §2the target's maximum health§f.");

        add(Registration.HEXTECH_GUNBLADE.value(), "Hextech Gunblade");
        add("tooltip.perplexity.gunblade","§dActive§f: Lightning Bolt \nShocks the target(an entity within your §7attack range + 6§f), dealing §715§f + §630% AD§f + §930% AP§f magic damage and §bslows them by 40%§f for 2 seconds (60 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.HEXTECH_ROCKETBELT.value(), "Hextech Rocketbelt");
        add("tooltip.perplexity.rocketbelt","§dActive§f: Supersonic \nPreform a short dash, unleashing an arc of 7 projectiles that deal §710§f + §910% AP§f magic damage (40 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.YOUMUUS_GHOSTBLADE.value(), "Youmuu's Ghostblade");
        add("tooltip.perplexity.youmuus","§dActive§f: Wraith Step \nGain §7200% bonus movement speed§f for 6 seconds (45 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.PROWLERS_CLAW.value(), "Prowler's Claw");
        add("tooltip.perplexity.prowlers","§dActive§f: Sandswipe \nTeleport behind a target within §732 blocks§f, dealing §710§f + §630% AD§f damage (90 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.SPECTRAL_CUTLASS.value(), "Spectral Cutlass");
        add("tooltip.perplexity.spectral","§dActive§f: Soul Anchor \nMark your current location. After 10 seconds, you will teleport back to the marked location (15 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.GOREDRINKER.value(), "Goredrinker");
        add("tooltip.perplexity.goredrinker","§dActive§f: Thirsting Slash \nDeal §660% AD§f to all entities within a 4.5 block radius. Heal for §620% AD§f + §28% missing health§f for each entity hit (15 second cooldown). \n§bMana Cost: 80§f");

        add(Registration.STRIDEBREAKER.value(), "Stridebreaker");
        add("tooltip.perplexity.stridebreaker","§dActive§f: Halting Slash \nPerform a small dash, dealing §675% AD§f damage to all entities within a 4.5 block radius and §bslowing them by 40%§f for 2 seconds (20 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.RANDUINS_OMEN.value(), "Randuin's Omen");
        add(Registration.OMEN_EFFECT.value(), "Critical Resilience");
        add("tooltip.perplexity.omen.active","§dActive§f: Humility \nUnleash a shockwave around you that §bslows entities within 5 blocks by 70%§f for 2 seconds (90 second cooldown). \n§bMana Cost: 50§f");
        add("tooltip.perplexity.omen.passive","§ePassive§f: Critical Resilience \nReduce damage increase of critical strikes by 30%.");

        add(Registration.GARGOYLE_STONEPLATE.value(), "Gargoyle Stoneplate");
        add("tooltip.perplexity.gargoyle","§dActive§f: Unbreakable \nGain §2100% health§f absorption for 5 seconds (60 second cooldown). \n§bMana Cost: 50§f");

        add(Registration.NASHORS_TOOTH_ITEM.value(), "Nashor's Tooth");
        add(Registration.NASHORS_EFFECT.value(), "Icathian Bite");
        add("tooltip.perplexity.nashors", "§ePassive§f: Icathian Bite \nYour attacks deal an additional §71.5§f + §915% AP§f magic damage.");

        add(Registration.RABADONS_DEATHCAP_ITEM.value(), "Rabadon's Deathcap");
        add("tooltip.perplexity.rabadons", "§ePassive§f: Magical Opus");
        add("tooltip,perplexity.raba_desc", "Increase your §9Ability Power§f by 30%.");

        add(Registration.RIFTMAKER_ITEM.value(), "Riftmaker");
        add(Registration.RIFT_EFFECT.value(), "Void Corruption");
        add("tooltip.perplexity.riftmaker_corr", "§ePassive§f: Void Corruption \nFor each instance of damage dealt, deal 1% increased damage, stacking up to 8 times. At maximum stacks, gain §410% omnivamp§f.");
        add("tooltip.perplexity.riftmaker_infu", "§ePassive§f: Void Infusion \n Gain §9Ability Power§f equal to §22% maximum health§f.");

        add(Registration.SERAPHS_ITEM.value(), "Seraph's Embrace");
        add(Registration.SERAPHS_EFFECT.value(), "Mana Lifeline");
        add("tooltip.perplexity.seraphs_awe", "§ePassive§f: Awe \nGrants §9Ability Power§f equal to §b2% mana§f.");
        add("tooltip.perplexity.seraphs_lifeline", "§ePassive§f: Mana Lifeline \nIf you were to take damage that would reduce you below §230% of your maximum health§f, you first gain absorption equal to §720§f + §b2% mana§f.");


        add(Registration.LICH_BANE_ITEM.value(), "Lich Bane");
        add(Registration.LICH_EFFECT.value(), "Lich Spellblade");
        add("tooltip.perplexity.lich", "§ePassive§f: Lich Spellblade \nAfter using an active ability, gain §650% Attack Speed§f and deal an additional §675% AD§f + §940% AP§f magic damage.");

        add(Registration.GRIEVOUS_WOUNDS_EFFECT.value(), "Grievous Wounds");

        add(Registration.MORELLONOMICON_ITEM.value(), "Morellonomicon");
        add(Registration.MORELLO_EFFECT.value(), "Grievous Wounds");
        add("tooltip.perplexity.morello", "§ePassive§f: Grievous Wounds \nDealing damage inflicts the target with Grievous Wounds for 3 seconds.");

        add(Registration.CHEMPUNK_CHAINSWORD_ITEM.value(), "Chempunk Chainsword");
        add(Registration.CHAINSWORD_EFFECT.value(), "Hackshorn");
        add("tooltip.perplexity.chainsword", "§ePassive§f: Hackshorn \nDealing damage inflicts the target with Grievous Wounds for 3 seconds.");

        add(Registration.THORNMAIL_ITEM.value(), "Thornmail");
        add(Registration.THORNMAIL_EFFECT.value(), "Thorns");
        add("tooltip.perplexity.thornmail", "§ePassive§f: Thorns \nReceiving damage inflicts the source with Grievous Wounds for 3 seconds.");

        add(Registration.VOID_STAFF_ITEM.value(), "Void Staff");

        add(Registration.PERPLEXITY_ITEM.value(), "Perplexity");
        add(Registration.PERPLEXITY_EFFECT.value(), "Giant Slayer");
        add("tooltip.perplexity.perplexity", "§ePassive§f: Giant Slayer \nDeal increased damage to targets with greater §2maximum health§f than you.");

        add(Registration.TWILIGHTS_EDGE_ITEM.value(), "Twilight's Edge");
        add(Registration.TWILIGHTS_EFFECT.value(), "The Path Between");
        add("tooltip.perplexity.twilights","§dActive§f: The Path Between \nIf you have not dealt damage in the last 6 seconds, preform a dash in the direction you are looking. This ability has no cooldown. \n§bMana Cost: 1§f");

        add(Registration.ABILITY_HASTE.value().getDescriptionId(),"Ability Haste");
        add(Registration.CRIT_CHANCE.value().getDescriptionId(),"% Critical Hit Chance");
        add(Registration.ABILITY_POWER.value().getDescriptionId(),"Ability Power");
        add(Registration.CRIT_DAMAGE.value().getDescriptionId(),"% Critical Hit Damage");
        add(Registration.LETHALITY.value().getDescriptionId(), "Lethality");
        add(Registration.OMNIVAMP.value().getDescriptionId(), "% Omnivamp");
        add(Registration.MAGIC_RESIST.value().getDescriptionId(),"Magic Resist");
        add(Registration.ARMOR_PEN.value().getDescriptionId(),"% Armor Penetration");
        add(Registration.MAGIC_PEN.value().getDescriptionId(),"% Magic Penetration");

        add(KeyBinding.KEY_CATEGORY, "Perplexity");
        add(KeyBinding.KEY_ABILITY_1, "Use slot 1 ability");
        add(KeyBinding.KEY_ABILITY_2, "Use slot 2 ability");
        add(KeyBinding.KEY_ABILITY_3, "Use slot 3 ability");
        add(KeyBinding.KEY_ABILITY_4, "Use slot 4 ability");

        add("curios.identifier.relic", "Relic");
        add("curios.modifiers.relic", "Relic");

        add("creativetab.perplexity", "Perplexity");
    }
}

class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Perplexity.MODID);
    }

    @Override
    protected void start() {
        List<ResourceLocation> targetLootTables = List.of(
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/abandoned_mineshaft"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/bastion_bridge"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/bastion_hoglin_stable"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/bastion_other"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/buried_treasure"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/desert_pyramid"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/igloo_chest"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/jungle_temple"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/nether_bridge"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/pillager_outpost"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/ruined_portal"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/shipwreck_map"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/shipwreck_supply"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/shipwreck_treasure"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/simple_dungeon"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/spawn_bonus_chest"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/stronghold_corridor"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/stronghold_crossing"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/underwater_ruin_big"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/underwater_ruin_small"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/woodland_mansion")
        );
        List<ResourceLocation> higherLootTables = List.of(
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/stronghold_library"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/end_city_treasure"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "chests/bastion_treasure"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "trial_chambers/reward/vault"),
                ResourceLocation.fromNamespaceAndPath("minecraft", "trial_chambers/reward/ominous_vault")
        );

        List<Item> items = List.of(
                Registration.DEATHS_DANCE.value(),
                Registration.DIVINE_SUNDERER.value(),
                Registration.NAVORI_FLICKERBLADE.value(),
                Registration.WARMOGS_ARMOR.value(),
                Registration.KRAKEN_SLAYER.value(),
                Registration.INFINITY_EDGE.value(),
                Registration.TRINITY_FORCE.value(),
                Registration.THE_COLLECTOR.value(),
                Registration.DUSKBLADE.value(),
                Registration.STORMRAZOR.value(),
                Registration.RAPIDFIRECANNON.value(),
                Registration.ECLIPSE.value(),
                Registration.IMMORTAL_SHIELDBOW.value(),
                Registration.ESSENCE_REAVER.value(),
                Registration.HEXTECH_GUNBLADE.value(),
                Registration.HEXTECH_ROCKETBELT.value(),
                Registration.YOUMUUS_GHOSTBLADE.value(),
                Registration.PROWLERS_CLAW.value(),
                Registration.SPECTRAL_CUTLASS.value(),
                Registration.GOREDRINKER.value(),
                Registration.STRIDEBREAKER.value(),
                Registration.RANDUINS_OMEN.value(),
                Registration.GARGOYLE_STONEPLATE.value(),
                Registration.NASHORS_TOOTH_ITEM.value(),
                Registration.RABADONS_DEATHCAP_ITEM.value(),
                Registration.RIFTMAKER_ITEM.value(),
                Registration.SERAPHS_ITEM.value(),
                Registration.LICH_BANE_ITEM.value(),
                Registration.MORELLONOMICON_ITEM.value(),
                Registration.CHEMPUNK_CHAINSWORD_ITEM.value(),
                Registration.THORNMAIL_ITEM.value(),
                Registration.VOID_STAFF_ITEM.value(),
                Registration.PERPLEXITY_ITEM.value(),
                Registration.TWILIGHTS_EDGE_ITEM.value()
        );

        for (ResourceLocation lootTable : targetLootTables) {
            for (Item item : items) {
                add(lootTable.getPath() + "_add_random_item_" + item.getDescriptionId(),
                        new AddItemModifier(
                                new LootItemCondition[]{
                                        LootTableIdCondition.builder(lootTable).build()
                                },
                                item,
                                0.00058
                        )
                );
            }
        }
        for (ResourceLocation lootTable : higherLootTables) {
            for (Item item : items) {
                add(lootTable.getPath() + "_add_random_item_" + item.getDescriptionId(),
                        new AddItemModifier(
                                new LootItemCondition[]{
                                        LootTableIdCondition.builder(lootTable).build()
                                },
                                item,
                                0.0058
                        )
                );
            }
        }
    }
}