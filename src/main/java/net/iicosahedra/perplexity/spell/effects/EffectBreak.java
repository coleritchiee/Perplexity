package net.iicosahedra.perplexity.spell.effects;

import com.mojang.authlib.GameProfile;
import net.iicosahedra.perplexity.spell.Affinities;
import net.iicosahedra.perplexity.spell.SpellCasting;
import net.iicosahedra.perplexity.spell.SpellContext;
import net.iicosahedra.perplexity.spell.components.AbstractEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.NeoForgeEventHandler;
import net.neoforged.neoforge.common.UsernameCache;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

public class EffectBreak extends AbstractEffect {
    public static final EffectBreak INSTANCE = new EffectBreak();

    public EffectBreak() {
        super("effect.break", Affinities.EARTH);
    }

    @Override
    public void onCastOnBlock(BlockHitResult blockHitResult, Level world, LivingEntity caster, SpellContext spellContext, SpellCasting resolver) {
        ItemStack stack = Items.NETHERITE_PICKAXE.getDefaultInstance();
        if(caster instanceof Player){
            //FakePlayer player = FakePlayerFactory.get((ServerLevel) world, new GameProfile(caster.getUUID(), UsernameCache.getLastKnownUsername(caster.getUUID())));
            Block block =   caster.level().getBlockState(blockHitResult.getBlockPos()).getBlock();
            BlockState state = world.getBlockState(blockHitResult.getBlockPos());
            if(world.getBlockState(blockHitResult.getBlockPos()).getDestroySpeed(world, blockHitResult.getBlockPos())>=0) {
                stack.mineBlock(world, state, blockHitResult.getBlockPos(),(Player) caster);
                boolean removed = state.onDestroyedByPlayer(world, blockHitResult.getBlockPos(), (Player) caster, state.canHarvestBlock(world, blockHitResult.getBlockPos(), (Player) caster), world.getFluidState(blockHitResult.getBlockPos()));
                if (removed) {
                    state.getBlock().destroy(world, blockHitResult.getBlockPos(), state);
                }
                block.playerDestroy(world, (Player) caster, blockHitResult.getBlockPos(), state, world.getBlockEntity(blockHitResult.getBlockPos()), stack);
                if(!world.isClientSide) {
                    int exp = CommonHooks.onBlockBreakEvent(world, GameType.SURVIVAL, (ServerPlayer) caster, blockHitResult.getBlockPos());
                    if (removed && exp > 0) {
                        state.getBlock().popExperience((ServerLevel) world, blockHitResult.getBlockPos(), exp);
                    }
                }
            }
        }
    }
}