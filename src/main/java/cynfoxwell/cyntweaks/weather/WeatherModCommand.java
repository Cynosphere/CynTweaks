package cynfoxwell.cyntweaks.weather;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class WeatherModCommand extends CommandBase {
    public String getName() {
        return "wmod";
    }

    public String getUsage(ICommandSender sender) {
        return "/wmod <clear, rain, snow>";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0)
        {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }else{
            if("clear".equals(args[0])){
                CynTweaks.wmodType = 0;
                sender.sendMessage(new TextComponentString("Weather set to clear."));
            }else if("rain".equals(args[0])){
                CynTweaks.wmodType = 1;
                sender.sendMessage(new TextComponentString("Weather set to rain."));
            }else if("snow".equals(args[0])){
                CynTweaks.wmodType = 2;
                sender.sendMessage(new TextComponentString("Weather set to snow."));
            }else{
                throw new WrongUsageException(this.getUsage(sender), new Object[0]);
            }
        }
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }
}
