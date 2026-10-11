package dev.happysh.experimentalmenu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public final class ExperimentalMenuClient implements ClientModInitializer {
    public static boolean espBoxEnabled = false;
    public static boolean aimBotArcEnabled = false;
    public static boolean puloBlzEnabled = false;
    private static KeyBinding openMenuKey;
    private static boolean menuKeyWasDown = false;
    private static boolean addedSpeedEffect = false;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.experimentalmenu.open", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, "category.experimentalmenu"));
        ClientTickEvents.END_CLIENT_TICK.register(ExperimentalMenuClient::onClientTick);
        WorldRenderEvents.AFTER_ENTITIES.register(ExperimentalMenuClient::renderEntityBoxes);
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> renderAimPracticeHud(drawContext));
    }

    private static void onClientTick(MinecraftClient client) {
        boolean keyDown = openMenuKey.isPressed();
        if (keyDown && !menuKeyWasDown && client.currentScreen == null) {
            client.setScreen(new ExperimentalMenuScreen());
        }
        menuKeyWasDown = keyDown;

        if (client.player == null) {
            addedSpeedEffect = false;
            return;
        }

        if (!puloBlzEnabled) {
            removeOwnedSpeedEffect(client);
            return;
        }

        if (!client.player.isOnGround()) {
            var existing = client.player.getStatusEffect(StatusEffects.SPEED);
            if (existing == null) {
                client.player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SPEED, 8, 3, true, false, true));
                addedSpeedEffect = true;
            } else if (addedSpeedEffect && existing.getAmplifier() == 3 && existing.getDuration() <= 3) {
                client.player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SPEED, 8, 3, true, false, true));
            }
        } else {
            removeOwnedSpeedEffect(client);
        }
    }

    private static void removeOwnedSpeedEffect(MinecraftClient client) {
        if (!addedSpeedEffect) return;
        if (client.player != null) {
            var speed = client.player.getStatusEffect(StatusEffects.SPEED);
            if (speed != null && speed.getAmplifier() == 3) {
                client.player.removeStatusEffect(StatusEffects.SPEED);
            }
        }
        addedSpeedEffect = false;
    }

    private static void renderEntityBoxes(net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext context) {
        if (!espBoxEnabled) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null || context.matrixStack() == null || context.consumers() == null) return;

        var matrices = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();
        VertexConsumer lines = consumers.getBuffer(RenderLayer.getLines());
        Vec3d cameraPos = context.camera().getPos();
        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player || !entity.isAlive()) continue;
            if (client.player.squaredDistanceTo(entity) > 64.0 * 64.0) continue;
            Box box = entity.getBoundingBox().expand(0.035);
            float green = entity instanceof LivingEntity ? 0.18F : 0.55F;
            float blue = entity instanceof LivingEntity ? 0.18F : 0.9F;
            WorldRenderer.drawBox(matrices, lines, box, 1.0F, green, blue, 0.9F);
        }
        matrices.pop();
    }

    private static void renderAimPracticeHud(net.minecraft.client.gui.DrawContext drawContext) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!aimBotArcEnabled || client.player == null || client.currentScreen != null) return;
        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;
        int color = 0xFFFF5A5A;
        drawContext.drawHorizontalLine(centerX - 13, centerX - 5, centerY, color);
        drawContext.drawHorizontalLine(centerX + 5, centerX + 13, centerY, color);
        drawContext.drawVerticalLine(centerX, centerY - 13, centerY - 5, color);
        drawContext.drawVerticalLine(centerX, centerY + 5, centerY + 13, color);
        boolean holdingBow = client.player.getMainHandStack().isOf(net.minecraft.item.Items.BOW)
                || client.player.getOffHandStack().isOf(net.minecraft.item.Items.BOW);
        String label = holdingBow ? "AIM BOT ARC - TREINO DE ARCO" : "AIM BOT ARC - SEGURE UM ARCO";
        drawContext.drawText(client.textRenderer, Text.literal(label),
                centerX - client.textRenderer.getWidth(label) / 2, centerY + 18, color, true);
    }
}
