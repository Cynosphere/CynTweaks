package cynfoxwell.cyntweaks.gui;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiCynTweaks extends GuiScreen {
    private GuiScreen parent;

    public GuiCynTweaks(GuiScreen parentScreen){
        this.parent = parentScreen;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        int left = width/2-155, top = height/6;

        buttonList.add(new GuiButton(199, width/2-100, top+24, 200, 20, "General Settings"));
        buttonList.add(new GuiButton(198, width/2-100, top+48, 200, 20, "HUD Settings"));

        buttonList.add(new GuiButton(200, width/2-100, top+144, 200, 20, I18n.format("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        switch (btn.id) {
            case 199:
                mc.displayGuiScreen(new GuiConfig(this, new ConfigElement(CynTweaks.config.getCategory("cyn_general")).getChildElements(), CynTweaks.MODID, false, false, "\u00A75Cynthia's Tweaks\u00A7r: General Settings"));

                break;
            case 198:
                mc.displayGuiScreen(new GuiConfig(this, new ConfigElement(CynTweaks.config.getCategory("cyn_hud")).getChildElements(), CynTweaks.MODID, false, false, "\u00A75Cynthia's Tweaks\u00A7r: HUD Settings"));

                break;
            case 200:
                if (parent == null) mc.setIngameFocus();
                else mc.displayGuiScreen(parent);

                break;
        }
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTickTime) {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "\u00A75Cynthia's Tweaks", width / 2, 20, 16777215);
        /*for(int a = 0, top = height/6; a < buttonList.size(); a++){
            GuiButton btn = buttonList.get(a);

            if (mouseX >= btn.x && mouseX < btn.x+btn.width && mouseY >= btn.y && mouseY < btn.y+20){
                String info = a < buttonTitles.length ? buttonTitles[a] : "";
                String[] spl = I18n.format(info).split("#");

                for(int line = 0; line < spl.length; line++){
                    drawCenteredString(fontRenderer, spl[line], width/2, top+148+10*line-(fontRenderer.FONT_HEIGHT*spl.length/2), -1);
                }

                break;
            }
        }*/

        super.drawScreen(mouseX, mouseY, partialTickTime);

    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
