package net.iicosahedra.perplexity.common.events;

import net.iicosahedra.perplexity.Perplexity;
import net.iicosahedra.perplexity.common.block.ActiveCircuit;
import net.iicosahedra.perplexity.common.block.ActiveCircuitEntity;
import net.iicosahedra.perplexity.common.block.SpellStructureTailEntity;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.SpellMapSavedDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Perplexity.MODID, bus = EventBusSubscriber.Bus.GAME)
public class BlockBreakEventHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof ActiveCircuit) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ActiveCircuitEntity) {
                BlockPos headPos = ((ActiveCircuitEntity) blockEntity).getHeadPos();
                int spellKey = SpellMapSavedDataManager.hashBlockPos(headPos);
                SpellMapSavedDataManager.INSTANCE.removeSpell(spellKey);
            }
        } else if (state.getBlock() == Registration.SPELL_STRUCTURE_HEAD.get()) {
            int spellKey = SpellMapSavedDataManager.hashBlockPos(pos);
            SpellMapSavedDataManager.INSTANCE.removeSpell(spellKey);
        } else if (state.getBlock() == Registration.SPELL_STRUCTURE_TAIL.get()) {
            //TODO: Figure out why it doesnt work
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SpellStructureTailEntity) {
                BlockPos headPos = ((SpellStructureTailEntity) blockEntity).getHeadPos();
                int spellKey = SpellMapSavedDataManager.hashBlockPos(headPos);
                SpellMapSavedDataManager.INSTANCE.removeSpell(spellKey);
            }
        }
    }
}
