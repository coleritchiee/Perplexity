package net.iicosahedra.perplexity.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.iicosahedra.perplexity.network.packet.AnvilRollPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemAnvilScreen extends Screen {
    private static final int STRIP_Y = 90;
    private static final int CELL_W = 20;
    private static final int CELL_H = 20;
    private static final int POINTER_W = 1;

    private final List<ItemStack> entries;
    private final int revealIndex;
    private final int durationTicks;
    private long startTime;

    public ItemAnvilScreen(AnvilRollPacket packet) {
        super(Component.literal("Item Anvil"));
        this.entries = toStacks(packet.displayList());
        this.revealIndex = packet.revealIndex();
        this.durationTicks = packet.durationTicks();
    }

    private List<ItemStack> toStacks(List<ResourceLocation> ids) {
        List<ItemStack> list = new ArrayList<>(ids.size());
        for (ResourceLocation id : ids) {
            Item item = BuiltInRegistries.ITEM.get(id);
            list.add(new ItemStack(item));
        }
        return list;
    }

    @Override
    protected void init() {
        super.init();
        startTime = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        gfx.fill(0, 0, this.width, this.height, 0xAA000000);
        int centerX = this.width / 2;
        int stripY = STRIP_Y + (this.height - 200) / 2;

        long now = Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 0L;
        float elapsed = (now - startTime) + partialTick;
        float total = durationTicks;
        float t = Mth.clamp(elapsed / total, 0f, 1f);
        float eased = (float) (1 - Math.pow(1 - t, 3));

        int totalCells = entries.size();
        int targetOffsetPx = revealIndex * CELL_W;
        float currentOffsetPx = eased * targetOffsetPx;
        int stripStartX = centerX - (CELL_W / 2);

        super.render(gfx, mouseX, mouseY, partialTick);

        int panelTop = stripY - 10;
        int panelBot = stripY + CELL_H + 10;
        gfx.fill(0, panelTop, this.width, panelBot, 0xFF0A0A0A);
        int visibleCells = (this.width / CELL_W) + 6;
        int startK = -visibleCells;
        int endK = totalCells * 4 + visibleCells;
        for (int k = startK; k < endK; k++) {
            int idx = Mth.positiveModulo(k, totalCells);
            int x = stripStartX + (k * CELL_W) - (int) currentOffsetPx;
            if (x < -CELL_W || x > this.width + CELL_W) continue;
            gfx.fill(x - 1, stripY - 1, x + CELL_W + 1, stripY + CELL_H + 1, 0xFF1E1E1E);
            gfx.renderItem(entries.get(idx), x + 2, stripY + 2);
        }

        gfx.pose().pushPose();
        gfx.pose().translate(0, 0, 400);
        gfx.fill(centerX - POINTER_W, stripY - 10, centerX + POINTER_W, stripY + CELL_H + 10, 0xFFFFFFFF);
        gfx.pose().popPose();


        if (t >= 1f) {
            this.onClose();
        }
    }
}


