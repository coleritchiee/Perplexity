package net.iicosahedra.perplexity.client.particle;

import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import static net.iicosahedra.perplexity.client.particle.LineEffect.hexToVector3f;

public class ShockwaveEffect {

    public static void createShockwaveParticles(Player player,  double maxRadius, int color, float size) {
        createShockwaveParticles(player, maxRadius, hexToVector3f(color), size);
    }

    public static void createShockwaveParticles(Player player, double maxRadius, Vector3f color, float size) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 origin = player.position().add(0, player.getBbHeight() / 2, 0);
        int totalTicks = 5;
        int totalParticles = 60;
        int particlesPerTick = totalParticles / totalTicks;
        double radiusIncrement = maxRadius / totalTicks;

        for (int tick = 0; tick < totalTicks; tick++) {
            int finalTick = tick;
            TickScheduler.schedule(tick, () -> {
                double currentRadius = (finalTick + 1) * radiusIncrement;
                spawnCircle(serverLevel, origin, currentRadius, particlesPerTick, color, size);
            });
        }
    }

    private static void spawnCircle(ServerLevel level, Vec3 center, double radius, int particleCount, Vector3f color, float size) {
        DustParticleOptions particle = new DustParticleOptions(color, size);

        for (int i = 0; i < particleCount; i++) {
            double angle = 2 * Math.PI * i / particleCount;
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
            level.sendParticles(particle, center.x + xOffset, center.y, center.z + zOffset, 1, 0, 0, 0, 1.0);
        }
    }
}
