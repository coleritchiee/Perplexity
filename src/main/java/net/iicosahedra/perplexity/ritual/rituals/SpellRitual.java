package net.iicosahedra.perplexity.ritual.rituals;

import net.iicosahedra.perplexity.common.block.ActiveCircuitEntity;
import net.iicosahedra.perplexity.common.block.InactiveCircuit;
import net.iicosahedra.perplexity.common.block.SpellStructureTailEntity;
import net.iicosahedra.perplexity.ritual.AbstractRitual;
import net.iicosahedra.perplexity.ritual.RitualContext;
import net.iicosahedra.perplexity.setup.Registration;
import net.iicosahedra.perplexity.spell.Spell;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellMapSavedDataManager;
import net.iicosahedra.perplexity.spell.components.ISpellComponent;
import net.iicosahedra.perplexity.spell.effects.EffectBreak;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellRitual extends AbstractRitual {
    public static final SpellRitual INSTANCE = new SpellRitual("spell_ritual");

    public SpellRitual(String name) {
        super(name);
    }

    @Override
    public void activate(Level world, RitualContext context) {
        if(world.getBlockState(context.activationPoint).getBlock()!=Registration.SPELL_STRUCTURE_HEAD.get()){
            return;
        }
        Spell spell = createSpellFromShapeStack(world, context);
        if(spell==null){
            return;
        }
        SpellMapSavedDataManager.INSTANCE.addSpell(SpellMapSavedDataManager.hashBlockPos(context.activationPoint), spell);

        transformCircuits(world, context.activationPoint, spell);

        if (!world.isClientSide) {
            ItemStack stack = new ItemStack(Registration.TEST_ITEM.asItem());
            stack.set(Registration.SPELL, SpellMapSavedDataManager.hashBlockPos(context.activationPoint));
            BlockPos spawnPos = context.activationPoint.above();
            ItemEntity entity = new ItemEntity(world, spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5, stack);
            world.addFreshEntity(entity);
        }

    }

    private void transformCircuits(Level world, BlockPos activationPoint, Spell spell) {
        for (int i = 1; i < 16; i++) {
            BlockPos levelPos = activationPoint.below(i);
            Map<BlockPos, Block> shapeAtLevel = getShapeAtLevel(world, levelPos);

            ISpellComponent component = getComponentFromShape(shapeAtLevel);
            if (component != null) {
                for (BlockPos offset : component.getShape().keySet()) {
                    BlockPos pos = levelPos.offset(offset);
                    BlockState state = world.getBlockState(pos);
                    if (state.getBlock() instanceof InactiveCircuit) {
                        InactiveCircuit inactiveCircuit = (InactiveCircuit) state.getBlock();
                        Block activeBlock = inactiveCircuit.getActiveCircuit();
                        world.setBlock(pos, activeBlock.defaultBlockState(), 3);
                        BlockEntity blockEntity = world.getBlockEntity(pos);
                        if (blockEntity instanceof ActiveCircuitEntity) {
                            ((ActiveCircuitEntity) blockEntity).setHeadPos(activationPoint);
                        }
                    }
                }
            } else {
                break;
            }
            BlockState tailState = world.getBlockState(levelPos);
            if (tailState.getBlock() == Registration.SPELL_STRUCTURE_TAIL.get()) {
                BlockEntity tailEntity = world.getBlockEntity(levelPos);
                if (tailEntity instanceof SpellStructureTailEntity) {
                    ((SpellStructureTailEntity) tailEntity).setHeadPos(activationPoint);
                }
                break;
            }
        }
    }

    public Spell createSpellFromShapeStack(Level world, RitualContext context) {
        BlockPos headPos = context.activationPoint;
        Spell spell = new Spell("spell");
        int i = 2;
        BlockPos offSet = new BlockPos(0, -1 * i, 0);
        Block check = world.getBlockState(headPos.offset(offSet)).getBlock();
        while (check != Registration.SPELL_STRUCTURE_TAIL.get()) {
            if (check == Blocks.BEDROCK || i > 16) {
                return null;
            }
            offSet = new BlockPos(0, -1 * ++i, 0);
            check = world.getBlockState(headPos.offset(offSet)).getBlock();
        }
        int endBound = i;

        for (int j = 1; j < endBound; j++) {
            BlockPos basePos = headPos.below(j);
            ISpellComponent component = getComponentFromShape(getShapeAtLevel(world, basePos));
            if (component != null) {
                spell.add(component);
            } else {
                return null;
            }
        }
        return spell;
    }

    private Map<BlockPos, Block> getShapeAtLevel(Level world, BlockPos basePos) {
        Map<BlockPos, Block> shape = new HashMap<>();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos pos = basePos.offset(x, 0, z);
                Block block = world.getBlockState(pos).getBlock();
                if (block != Blocks.AIR) {
                    shape.put(new BlockPos(x, 0, z), block);
                }
            }
        }
        return shape;
    }

    private ISpellComponent getComponentFromShape(Map<BlockPos, Block> shape) {
        if (matchesShape(shape, Registration.EFFECT_BREAK.get().getShape())) {
            return Registration.EFFECT_BREAK.get();
        }
        if (matchesShape(shape, Registration.SHAPE_PROJ.get().getShape())) {
            return Registration.SHAPE_PROJ.get();
        }
        return null;
    }

    private boolean matchesShape(Map<BlockPos, Block> shape, Map<BlockPos, Block> referenceShape) {
        if (shape.size() != referenceShape.size()) {
            return false;
        }
        for (Map.Entry<BlockPos, Block> entry : referenceShape.entrySet()) {
            if (!shape.containsKey(entry.getKey()) || shape.get(entry.getKey()) != entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
