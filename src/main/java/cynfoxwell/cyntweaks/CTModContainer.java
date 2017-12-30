package cynfoxwell.cyntweaks;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

import java.util.Arrays;

public class CTModContainer extends DummyModContainer {
    public CTModContainer() {
        super(new ModMetadata());
        final ModMetadata meta = this.getMetadata();
        meta.modId = "cyntweaks-asm";
        meta.name = "CynTweaks ASM";
        meta.description = "*hacker voice* I'm in.\n\nThis is all stuff you don't need to worry about and most is configurable from main mod.";
        meta.version = "GRADLE:VERSION";
        meta.authorList = Arrays.asList("BoxOfFlex");
    }
}
