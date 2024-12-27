package net.iicosahedra.perplexity.client.particle;

import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import static net.iicosahedra.perplexity.client.particle.LineEffect.hexToVector3f;

public class SpinningEffect {

    public static void createSpinningParticles(Player player,  double maxRadius, int color, float size, int duration) {
        createSpinningParticles(player, maxRadius, hexToVector3f(color), size);
    }

    public static void createSpinningParticles(Player player, double radius, Vector3f color, float size) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 origin = player.position().add(0, player.getBbHeight() / 2, 0);
        int totalTicks = 5;
        int totalParticles = 60;
        int particlesPerTick = totalParticles / totalTicks;

        for (int tick = 0; tick < totalTicks; tick++) {
            int finalTick = tick;
            TickScheduler.schedule(tick,() -> {
                double angleIncrement = 2 * Math.PI / totalParticles;
                double baseAngle = (2 * Math.PI / totalTicks) * finalTick;
                for (int i = 0; i < particlesPerTick; i++) {
                    double angle = baseAngle + (i * angleIncrement);
                    double xOffset = radius * Math.cos(angle);
                    double zOffset = radius * Math.sin(angle);
                    spawnParticle(serverLevel, origin.add(xOffset, 0, zOffset), color, size);
                }
            });
        }
    }

    private static void spawnParticle(ServerLevel level, Vec3 position, Vector3f color, float size) {
        DustParticleOptions particle = new DustParticleOptions(color, size);
        level.sendParticles(particle, position.x, position.y, position.z, 1, 0, 0, 0, 1);
    }
}
