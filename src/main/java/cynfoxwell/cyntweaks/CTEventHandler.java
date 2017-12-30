package cynfoxwell.cyntweaks;

import com.google.common.collect.Ordering;
import cynfoxwell.cyntweaks.gui.GuiCynTweaks;
import cynfoxwell.cyntweaks.time.TimeChangerNetHandler;
import cynfoxwell.cyntweaks.time.TimeType;
import cynfoxwell.cyntweaks.weather.WeatherModRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Collection;

public class CTEventHandler {

    public static final CTEventHandler instance = new CTEventHandler();

    private static Minecraft mc;

    public CTEventHandler(){
        mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onPotionShiftEvent(final GuiScreenEvent.PotionShiftEvent event) {
        event.setCanceled(CynTweaks.potionShift);
    }

    @SubscribeEvent
    public void onOverlayEvent(final RenderGameOverlayEvent.Pre event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.BOSSHEALTH || event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO){
            event.setCanceled(!CynTweaks.hudBoss);
        }

        if(event.getType() == RenderGameOverlayEvent.ElementType.HELMET){
            event.setCanceled(!CynTweaks.hudHelmet);
        }

        if(event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE){
            event.setCanceled(!CynTweaks.hudVig);
        }

        if(event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS){
            event.setCanceled(!CynTweaks.hudPot);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        int sprintKey = this.mc.gameSettings.keyBindSprint.getKeyCode();
        int sneakKey = this.mc.gameSettings.keyBindSneak.getKeyCode();

        if (!CynTweaks.sprintToggled && sprintKey > 0) {
            KeyBinding.setKeyBindState(sprintKey, Keyboard.isKeyDown(sprintKey));
        }
        if (CynTweaks.sprintToggled) {
            KeyBinding.setKeyBindState(sprintKey, true);
        }

        if (!CynTweaks.sneakToggled && sneakKey > 0) {
            KeyBinding.setKeyBindState(sneakKey, Keyboard.isKeyDown(sneakKey));
        }
        if (CynTweaks.sneakToggled) {
            KeyBinding.setKeyBindState(sneakKey, true);
        }

        if(CynTweaks.cmdShowGui){
            mc.displayGuiScreen(new GuiCynTweaks(null));
            CynTweaks.cmdShowGui = false;
        }

        CynTweaks.finalGlintColor = CynTweaks.glintRainbow ? CynTweaks.RainbowEffect(CynTweaks.rainbowSpeed) : CynTweaks.HextoRGB(CynTweaks.glintColor);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (CynTweaks.keySprint.isPressed()) {
           CynTweaks.sprintToggled = !CynTweaks.sprintToggled;
        }
        if (CynTweaks.keySneak.isPressed()) {
            CynTweaks.sneakToggled = !CynTweaks.sneakToggled;
        }
    }

    @SubscribeEvent
    public void wmodWorldLoad(WorldEvent.Load event) {
        event.getWorld().provider.setWeatherRenderer(CynTweaks.wmodRenderer);
    }

    @SubscribeEvent
    public void wmodTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            WeatherModRenderer wmodRenderer = CynTweaks.wmodRenderer;
            ++wmodRenderer.rendererUpdateCount;
        }
    }

    @SubscribeEvent
    public void timeChangerTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.world != null) {
            final INetHandler parent = this.mc.player.connection.getNetworkManager().getNetHandler();
            if (!(parent instanceof TimeChangerNetHandler)) {
                this.mc.player.connection.getNetworkManager().setNetHandler((INetHandler)new TimeChangerNetHandler((NetHandlerPlayClient)parent));
            }
            if (CynTweaks.TIME_TYPE == TimeType.FAST) {
                this.mc.world.setWorldTime((long)(System.currentTimeMillis() * CynTweaks.fastTimeMultiplier % 24000.0));
            }
        }
    }

    @SubscribeEvent
    public void drawHUDText(RenderGameOverlayEvent event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT && mc.ingameGUI != null) {
            ScaledResolution res = new ScaledResolution(mc);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            if(CynTweaks.cynInfo){
                int index = 0;
                int color = CynTweaks.accentRainbow ? CynTweaks.RainbowEffect(CynTweaks.rainbowSpeed) : CynTweaks.HextoRGB(CynTweaks.accentColor);

                String fpsText = "FPS: \u00A7f" + mc.debug.substring(0, mc.debug.indexOf(' '));

                mc.fontRenderer.drawStringWithShadow(fpsText, 2, 2 + (mc.fontRenderer.FONT_HEIGHT*index), color);
                index++;

                if (CynTweaks.sprintToggled) {
                    mc.fontRenderer.drawStringWithShadow("Sprinting \u00A78(\u00A72Toggled\u00A78)", 2, 2 + (mc.fontRenderer.FONT_HEIGHT*index), color);
                    index++;
                }
                if (CynTweaks.sneakToggled) {
                    mc.fontRenderer.drawStringWithShadow("Sneaking \u00A78(\u00A72Toggled\u00A78)", 2, 2 + (mc.fontRenderer.FONT_HEIGHT*index), color);
                    index++;
                }
            }

            if(CynTweaks.cynPot){
                GuiIngame gui = mc.ingameGUI;
                int num = 0;
                Collection<PotionEffect> effects = mc.player.getActivePotionEffects();
                if(!effects.isEmpty()){
                    for(PotionEffect potioneffect : Ordering.natural().sortedCopy(effects)){
                        Potion potion = potioneffect.getPotion();

                        String name = I18n.format(potion.getName());
                        int color = potion.getLiquidColor();

                        if (potioneffect.getAmplifier() == 1)
                        {
                            name = name + " " + I18n.format("enchantment.level.2");
                        }
                        else if (potioneffect.getAmplifier() == 2)
                        {
                            name = name + " " + I18n.format("enchantment.level.3");
                        }
                        else if (potioneffect.getAmplifier() == 3)
                        {
                            name = name + " " + I18n.format("enchantment.level.4");
                        }

                        String st = name + " \u00A7f("+Potion.getPotionDurationString(potioneffect, 1.0F)+")";

                        mc.fontRenderer.drawStringWithShadow(st, width - mc.fontRenderer.getStringWidth(name+" ("+Potion.getPotionDurationString(potioneffect, 1.0F)+")")-2, height - 2 - mc.fontRenderer.FONT_HEIGHT - (mc.fontRenderer.FONT_HEIGHT*num), color);
                        num++;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(CynTweaks.MODID)) {
            CynTweaks.saveConfig();
        }
    }
}
