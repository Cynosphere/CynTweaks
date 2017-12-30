package cynfoxwell.cyntweaks.time;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

public class TimeChangerCommand extends CommandBase {
    public String getName() {
        return "localtime";
    }

    public String getUsage(ICommandSender sender) {
        return "/localtime <day, night, sunset, reset, fasttime>";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0)
        {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }else{
            if("day".equals(args[0])){
                CynTweaks.TIME_TYPE = TimeType.DAY;
                sender.sendMessage(new TextComponentString("Time set to day."));
            }else if("night".equals(args[0])){
                CynTweaks.TIME_TYPE = TimeType.NIGHT;
                sender.sendMessage(new TextComponentString("Time set to night."));
            }else if("sunset".equals(args[0])){
                CynTweaks.TIME_TYPE = TimeType.SUNSET;
                sender.sendMessage(new TextComponentString("Time set to sunset."));
            }else if("reset".equals(args[0])){
                CynTweaks.TIME_TYPE = TimeType.VANILLA;
                sender.sendMessage(new TextComponentString("Time reset to vanilla time."));
            }else if("fasttime".equals(args[0])){
                final double multiplier = NumberUtils.toDouble(args[1], -1.0);
                if (multiplier < 0.0) {
                    sender.sendMessage(new TextComponentString("Invalid multiplier!").setStyle(new Style().setColor(TextFormatting.RED)));
                    return;
                }
                CynTweaks.TIME_TYPE = TimeType.FAST;
                CynTweaks.fastTimeMultiplier = multiplier;
                sender.sendMessage(new TextComponentString("Time set to fast time with multiplier "+multiplier+"."));
            }else{
                throw new WrongUsageException(this.getUsage(sender), new Object[0]);
            }
        }
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }
}
