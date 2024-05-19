package net.iicosahedra.perplexity.ritual.rituals;

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
        if (!world.isClientSide) {
            ItemStack stack = new ItemStack(Registration.TEST_ITEM.asItem());
            stack.set(Registration.SPELL, SpellMapSavedDataManager.hashBlockPos(context.activationPoint));
            BlockPos spawnPos = context.activationPoint.above();
            ItemEntity entity = new ItemEntity(world, spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5, stack);
            world.addFreshEntity(entity);
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
