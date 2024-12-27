package net.iicosahedra.perplexity.client.particle;

import net.iicosahedra.perplexity.util.TickScheduler;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class LineEffect {

    public static void createLineParticles(Player player, LivingEntity target, int color, float size, int duration) {
        createLineParticles(player, target, hexToVector3f(color), size, duration);
    }

    public static void createLineParticles(Player player, LivingEntity target, Vector3f color, float size, int duration) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 start = player.position().add(0, player.getBbHeight() / 2, 0);
        Vec3 end = target.position().add(0, target.getBbHeight() / 2, 0);
        Vec3 direction = end.subtract(start).normalize();
        double distance = start.distanceTo(end);

        int totalParticles = 30;
        double particleSpacing = distance / totalParticles;

        for (int i = 0; i < totalParticles; i++) {
            int finalI = i;
            TickScheduler.schedule((i * duration) / totalParticles, () -> {
                Vec3 position = start.add(direction.scale(finalI * particleSpacing));
                spawnParticle(serverLevel, position, color, size);
            });
        }
    }


    private static void spawnParticle(ServerLevel level, Vec3 position, Vector3f color, float size) {
        DustParticleOptions particle = new DustParticleOptions(color, size);

        double motionX = (Math.random() - 0.5) * 0.1;
        double motionY = (Math.random() - 0.5) * 0.1;
        double motionZ = (Math.random() - 0.5) * 0.1;

        level.sendParticles(particle, position.x, position.y, position.z, 1, motionX, motionY, motionZ, 1.0);
    }

    public static Vector3f hexToVector3f(int hexColor) {
        float red = ((hexColor >> 16) & 0xFF) / 255.0F;
        float green = ((hexColor >> 8) & 0xFF) / 255.0F;
        float blue = (hexColor & 0xFF) / 255.0F;
        return new Vector3f(red, green, blue);
    }
}

