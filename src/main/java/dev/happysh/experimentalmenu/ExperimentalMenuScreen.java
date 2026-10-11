package dev.happysh.experimentalmenu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class ExperimentalMenuScreen extends Screen {
    private ButtonWidget espButton;
    private ButtonWidget aimButton;
    private ButtonWidget puloButton;

    public ExperimentalMenuScreen() {
        super(Text.literal("Experimental Menu"));
    }

    @Override
    protected void init() {
        int buttonWidth = Math.min(240, this.width - 40);
        int left = (this.width - buttonWidth) / 2;
        int top = this.height / 2 - 44;
        espButton = addDrawableChild(ButtonWidget.builder(buttonText("ESP Box", ExperimentalMenuClient.espBoxEnabled), button -> {
            ExperimentalMenuClient.espBoxEnabled = !ExperimentalMenuClient.espBoxEnabled;
            updateButtonLabels();
        }).dimensions(left, top, buttonWidth, 20).build());
        aimButton = addDrawableChild(ButtonWidget.builder(buttonText("Aim Bot Arc", ExperimentalMenuClient.aimBotArcEnabled), button -> {
            ExperimentalMenuClient.aimBotArcEnabled = !ExperimentalMenuClient.aimBotArcEnabled;
            updateButtonLabels();
        }).dimensions(left, top + 27, buttonWidth, 20).build());
        puloButton = addDrawableChild(ButtonWidget.builder(buttonText("Pulo BLZ", ExperimentalMenuClient.puloBlzEnabled), button -> {
            ExperimentalMenuClient.puloBlzEnabled = !ExperimentalMenuClient.puloBlzEnabled;
            updateButtonLabels();
        }).dimensions(left, top + 54, buttonWidth, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Fechar"), button -> close())
                .dimensions(left, top + 88, buttonWidth, 20).build());
    }

    private static Text buttonText(String name, boolean enabled) {
        return Text.literal(name + "  [" + (enabled ? "ON" : "OFF") + "]");
    }

    private void updateButtonLabels() {
        espButton.setMessage(buttonText("ESP Box", ExperimentalMenuClient.espBoxEnabled));
        aimButton.setMessage(buttonText("Aim Bot Arc", ExperimentalMenuClient.aimBotArcEnabled));
        puloButton.setMessage(buttonText("Pulo BLZ", ExperimentalMenuClient.puloBlzEnabled));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xD9101018);
        int panelWidth = Math.min(300, this.width - 20);
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = this.height / 2 - 91;
        context.fill(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 124, 0xFF191923);
        context.fill(panelLeft, panelTop, panelLeft + panelWidth, panelTop + 3, 0xFFFF3434);
        context.drawCenteredTextWithShadow(this.textRenderer, "EXPERIMENTAL MENU", this.width / 2, panelTop + 10, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Minecraft Java 1.21.4 - Fabric", this.width / 2, panelTop + 23, 0xFFB8B8C8);
        drawStatus(context, panelLeft + 8, panelTop + 45, panelWidth - 16, ExperimentalMenuClient.espBoxEnabled);
        drawStatus(context, panelLeft + 8, panelTop + 72, panelWidth - 16, ExperimentalMenuClient.aimBotArcEnabled);
        drawStatus(context, panelLeft + 8, panelTop + 99, panelWidth - 16, ExperimentalMenuClient.puloBlzEnabled);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawStatus(DrawContext context, int x, int y, int width, boolean enabled) {
        context.fill(x, y, x + width, y + 19, enabled ? 0x6630D878 : 0x44383848);
        context.fill(x, y, x + 3, y + 19, enabled ? 0xFF30D878 : 0xFF686879);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
