package dev.happysh.experimentalmenu;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class ExperimentalMenuScreen extends Screen {
    private ButtonWidget espButton, aimButton, puloButton, ponteButton;

    public ExperimentalMenuScreen() {
        super(Text.literal("Experimental Menu"));
    }

    @Override
    protected void init() {
        int w = Math.min(240, this.width - 40);
        int x = (this.width - w) / 2;
        int y = this.height / 2 - 57;
        espButton = addDrawableChild(ButtonWidget.builder(buttonText("ESP Box", ExperimentalMenuClient.espBoxEnabled), b -> {
            ExperimentalMenuClient.espBoxEnabled = !ExperimentalMenuClient.espBoxEnabled;
            updateLabels();
        }).dimensions(x, y, w, 20).build());
        aimButton = addDrawableChild(ButtonWidget.builder(buttonText("Aim Bot Arc", ExperimentalMenuClient.aimBotArcEnabled), b -> {
            ExperimentalMenuClient.aimBotArcEnabled = !ExperimentalMenuClient.aimBotArcEnabled;
            updateLabels();
        }).dimensions(x, y + 25, w, 20).build());
        puloButton = addDrawableChild(ButtonWidget.builder(buttonText("Pulo BLZ", ExperimentalMenuClient.puloBlzEnabled), b -> {
            ExperimentalMenuClient.puloBlzEnabled = !ExperimentalMenuClient.puloBlzEnabled;
            updateLabels();
        }).dimensions(x, y + 50, w, 20).build());
        ponteButton = addDrawableChild(ButtonWidget.builder(buttonText("Ponte Auto", ExperimentalMenuClient.ponteAutoEnabled), b -> {
            if (client != null && client.isInSingleplayer()) {
                ExperimentalMenuClient.ponteAutoEnabled = !ExperimentalMenuClient.ponteAutoEnabled;
            } else {
                ExperimentalMenuClient.ponteAutoEnabled = false;
            }
            updateLabels();
        }).dimensions(x, y + 75, w, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Fechar"), b -> close())
                .dimensions(x, y + 105, w, 20).build());
    }

    private static Text buttonText(String name, boolean on) {
        return Text.literal(name + "  [" + (on ? "ON" : "OFF") + "]");
    }

    private void updateLabels() {
        espButton.setMessage(buttonText("ESP Box", ExperimentalMenuClient.espBoxEnabled));
        aimButton.setMessage(buttonText("Aim Bot Arc", ExperimentalMenuClient.aimBotArcEnabled));
        puloButton.setMessage(buttonText("Pulo BLZ", ExperimentalMenuClient.puloBlzEnabled));
        ponteButton.setMessage(buttonText("Ponte Auto", ExperimentalMenuClient.ponteAutoEnabled));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xD9101018);
        int pw = Math.min(300, width - 20);
        int px = (width - pw) / 2;
        int py = height / 2 - 105;
        context.fill(px, py, px + pw, py + 151, 0xFF191923);
        context.fill(px, py, px + pw, py + 3, 0xFFFF3434);
        context.drawCenteredTextWithShadow(textRenderer, "EXPERIMENTAL MENU", width / 2, py + 8, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, "Minecraft Java 1.21.4 - Fabric", width / 2, py + 20, 0xFFB8B8C8);
        drawStatus(context, px + 8, py + 41, pw - 16, ExperimentalMenuClient.espBoxEnabled);
        drawStatus(context, px + 8, py + 66, pw - 16, ExperimentalMenuClient.aimBotArcEnabled);
        drawStatus(context, px + 8, py + 91, pw - 16, ExperimentalMenuClient.puloBlzEnabled);
        drawStatus(context, px + 8, py + 116, pw - 16, ExperimentalMenuClient.ponteAutoEnabled);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawStatus(DrawContext context, int x, int y, int w, boolean on) {
        context.fill(x, y, x + w, y + 19, on ? 0x6630D878 : 0x44383848);
        context.fill(x, y, x + 3, y + 19, on ? 0xFF30D878 : 0xFF686879);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
