package cynfoxwell.cyntweaks.gui;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class GuiConfigCommand extends CommandBase {
    public String getName() {
        return "ctconfig";
    }

    public String getUsage(ICommandSender sender) {
        return "/ctconfig";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CynTweaks.cmdShowGui = true;
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }
}
