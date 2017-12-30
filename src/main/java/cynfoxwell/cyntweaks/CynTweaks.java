package cynfoxwell.cyntweaks;

import cynfoxwell.cyntweaks.gui.GuiConfigCommand;
import cynfoxwell.cyntweaks.time.TimeChangerCommand;
import cynfoxwell.cyntweaks.time.TimeType;
import cynfoxwell.cyntweaks.weather.WeatherModCommand;
import cynfoxwell.cyntweaks.weather.WeatherModRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@Mod(modid = CynTweaks.MODID, name = CynTweaks.NAME, version = CynTweaks.VERSION, useMetadata = true, clientSideOnly = true, guiFactory = "cynfoxwell.cyntweaks.CTGuiFactory")
public class CynTweaks extends DummyModContainer {
    public static final String MODID = "cyntweaks";
    public static final String NAME = "Cynthia's Tweaks";
    public static final String VERSION = "GRADLE:VERSION";
    public static Logger logger;
    public static Configuration config;
    public static Minecraft mc;

    public static String accentColor = "54267f";
    public static boolean accentRainbow = false;
    public static boolean cynInfo = true;
    public static boolean cynPot = true;
    public static boolean tabPing = true;
    public static String glintColor = "FFFFFF";
    public static boolean glintRainbow = false;
    public static int finalGlintColor = 0xFFFFFF;
    public static int rainbowSpeed = 2;

    public static boolean hudBoss = true;
    public static boolean hudHelmet = false;
    public static boolean hudVig = false;
    public static boolean hudPot = true;
    public static boolean potionShift = true;

    public static KeyBinding keySprint;
    public static KeyBinding keySneak;
    public static boolean sprintToggled = false;
    public static boolean sneakToggled = false;

    public static int wmodType = 0;
    public static WeatherModRenderer wmodRenderer;

    public static TimeType TIME_TYPE;
    public static double fastTimeMultiplier;

    public static boolean cmdShowGui = false;

    public CynTweaks() {
        this.mc = Minecraft.getMinecraft();
        this.wmodRenderer = new WeatherModRenderer();
        this.TIME_TYPE = TimeType.VANILLA;
        this.fastTimeMultiplier = 1.0;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        config = new Configuration(event.getSuggestedConfigurationFile());
        saveConfig();
    }

    public static void saveConfig() {
        accentColor = config.getString("accentColor", "cyn_general", "54267f", "Accent color for HUD elements.");
        accentRainbow = config.getBoolean("accentRainbow", "cyn_general", false, "Rainbow accent color.");
        cynInfo = config.getBoolean("cynInfo", "cyn_general", true, "Enable or disable the info HUD.");
        cynPot = config.getBoolean("cynPot", "cyn_general", true, "Enable or disable the custom status effect HUD.");
        tabPing = config.getBoolean("tabPing", "cyn_general", true, "Switch between ping icons and numerical ping. (true = numeric, false = icon)");
        glintColor = config.getString("glintColor", "cyn_general", "FFFFFF", "Custom enchant glint color.");
        glintRainbow = config.getBoolean("glintRainbow", "cyn_general", false, "Rainbow enchant glint color.");
        rainbowSpeed = config.getInt("rainbowSpeed", "cyn_general", 2, 1, 10, "Rainbow speed for anything rainbow.");

        // HUD disabling
        hudBoss = config.getBoolean("hudBoss", "cyn_hud", true, "Enable or disable the boss bar.");
        hudHelmet = config.getBoolean("hudHelmet", "cyn_hud", false, "Enable or disable the overlay for pumpkins.");
        hudVig = config.getBoolean("hudVig", "cyn_hud", false, "Enable or disable the vignette.");
        hudPot = config.getBoolean("hudPot", "cyn_hud", true, "Enable or disable the potion icons in the corner.");
        potionShift = config.getBoolean("potionShift", "cyn_hud", true, "Enable or disable potion shift fix.");

        finalGlintColor = glintRainbow ? RainbowEffect(rainbowSpeed) : HextoRGB(glintColor);

        config.save();
    }

    public static int HextoRGB(String str) {
        return new Color(
                Integer.valueOf( str.substring( 0, 2 ), 16 ),
                Integer.valueOf( str.substring( 2, 4 ), 16 ),
                Integer.valueOf( str.substring( 4, 6 ), 16 ) ).getRGB();
    }

    public static int RainbowEffect(int speed){
        return Color.HSBtoRGB((System.nanoTime()*speed)/4.0E9F % 1F,1F, 1F);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        ClientRegistry.registerKeyBinding(keySprint = new KeyBinding("Toggle Sprint", 34, "CynTweaks"));
        ClientRegistry.registerKeyBinding(keySneak = new KeyBinding("Toggle Sneak", 21, "CynTweaks"));

        ClientCommandHandler.instance.registerCommand(new WeatherModCommand());
        ClientCommandHandler.instance.registerCommand(new TimeChangerCommand());
        ClientCommandHandler.instance.registerCommand(new GuiConfigCommand());


        MinecraftForge.EVENT_BUS.register(CTEventHandler.instance);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
