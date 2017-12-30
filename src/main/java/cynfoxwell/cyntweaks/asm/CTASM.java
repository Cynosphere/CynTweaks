package cynfoxwell.cyntweaks.asm;

import cynfoxwell.cyntweaks.CTModContainer;
import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name(value = "CynTweaks ASM")
@IFMLLoadingPlugin.MCVersion(value = "1.12.2")
public class CTASM implements IFMLLoadingPlugin {
    public static boolean devEnv = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                ClassTransformer.class.getName()
        };
    }

    @Override
    public String getModContainerClass() {
        return CTModContainer.class.getName();
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        devEnv = !((Boolean)data.get("runtimeDeobfuscationEnabled")).booleanValue();
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
