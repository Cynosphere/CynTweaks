package cynfoxwell.cyntweaks.time;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.ITextComponent;

import java.lang.reflect.Field;

public class TimeChangerNetHandler extends NetHandlerPlayClient {
    private NetHandlerPlayClient parent;

    public TimeChangerNetHandler(final NetHandlerPlayClient parent) {
        super(Minecraft.getMinecraft(), getGuiScreen(parent), parent.getNetworkManager(), parent.getGameProfile());
        this.parent = parent;
    }

    private static GuiScreen getGuiScreen(final NetHandlerPlayClient parent) {
        for (Field field : parent.getClass().getDeclaredFields()) {
            if (field.getType().equals(GuiScreen.class)) {
                field.setAccessible(true);
                try {
                    return (GuiScreen)field.get(parent);
                }
                catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    public void handleJoinGame(SPacketJoinGame packetIn)
    {
        this.parent.handleJoinGame(packetIn);
    }

    public void handleSpawnObject(SPacketSpawnObject packetIn)
    {
        this.parent.handleSpawnObject(packetIn);
    }

    public void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb packetIn)
    {
        this.parent.handleSpawnExperienceOrb(packetIn);
    }

    public void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity packetIn)
    {
        this.parent.handleSpawnGlobalEntity(packetIn);
    }

    public void handleSpawnPainting(SPacketSpawnPainting packetIn)
    {
        this.parent.handleSpawnPainting(packetIn);
    }

    public void handleEntityVelocity(SPacketEntityVelocity packetIn)
    {
        this.parent.handleEntityVelocity(packetIn);
    }

    public void handleEntityMetadata(SPacketEntityMetadata packetIn)
    {
        this.parent.handleEntityMetadata(packetIn);
    }

    public void handleSpawnPlayer(SPacketSpawnPlayer packetIn)
    {
        this.parent.handleSpawnPlayer(packetIn);
    }

    public void handleEntityTeleport(SPacketEntityTeleport packetIn)
    {
        this.parent.handleEntityTeleport(packetIn);
    }

    public void handleHeldItemChange(SPacketHeldItemChange packetIn)
    {
        this.parent.handleHeldItemChange(packetIn);
    }

    public void handleEntityMovement(SPacketEntity packetIn)
    {
        this.parent.handleEntityMovement(packetIn);
    }

    public void handleEntityHeadLook(SPacketEntityHeadLook packetIn)
    {
        this.parent.handleEntityHeadLook(packetIn);
    }

    public void handleDestroyEntities(SPacketDestroyEntities packetIn)
    {
        this.parent.handleDestroyEntities(packetIn);
    }

    public void handlePlayerPosLook(SPacketPlayerPosLook packetIn)
    {
        this.parent.handlePlayerPosLook(packetIn);
    }

    public void handleMultiBlockChange(SPacketMultiBlockChange packetIn)
    {
        this.parent.handleMultiBlockChange(packetIn);
    }

    public void handleChunkData(SPacketChunkData packetIn)
    {
        this.parent.handleChunkData(packetIn);
    }

    public void processChunkUnload(SPacketUnloadChunk packetIn)
    {
        this.parent.processChunkUnload(packetIn);
    }

    public void handleBlockChange(SPacketBlockChange packetIn)
    {
        this.parent.handleBlockChange(packetIn);
    }

    public void handleDisconnect(SPacketDisconnect packetIn)
    {
        this.parent.handleDisconnect(packetIn);
    }

    public void onDisconnect(ITextComponent reason)
    {
        this.parent.onDisconnect(reason);
    }

    public void sendPacket(Packet<?> packetIn)
    {
        this.parent.sendPacket(packetIn);
    }

    public void handleCollectItem(SPacketCollectItem packetIn)
    {
        this.parent.handleCollectItem(packetIn);
    }

    public void handleChat(SPacketChat packetIn)
    {
        this.parent.handleChat(packetIn);
    }

    public void handleAnimation(SPacketAnimation packetIn)
    {
        this.parent.handleAnimation(packetIn);
    }

    public void handleUseBed(SPacketUseBed packetIn)
    {
        this.parent.handleUseBed(packetIn);
    }


    public void handleSpawnMob(SPacketSpawnMob packetIn)
    {
        this.parent.handleSpawnMob(packetIn);
    }

    public void handleTimeUpdate(SPacketTimeUpdate packet) {
        switch (CynTweaks.TIME_TYPE) {
            case DAY: {
                this.parent.handleTimeUpdate(new SPacketTimeUpdate(packet.getWorldTime(), -6000L, true));
            }
            case SUNSET: {
                this.parent.handleTimeUpdate(new SPacketTimeUpdate(packet.getWorldTime(), -22880L, true));
            }
            case NIGHT: {
                this.parent.handleTimeUpdate(new SPacketTimeUpdate(packet.getWorldTime(), -18000L, true));
            }
            case VANILLA: {
                this.parent.handleTimeUpdate(packet);
                break;
            }
        }
    }

    public void handleSpawnPosition(SPacketSpawnPosition packetIn)
    {
        this.parent.handleSpawnPosition(packetIn);
    }

    public void handleSetPassengers(SPacketSetPassengers packetIn)
    {
        this.parent.handleSetPassengers(packetIn);
    }

    public void handleEntityAttach(SPacketEntityAttach packetIn)
    {
        this.parent.handleEntityAttach(packetIn);
    }

    public void handleEntityStatus(SPacketEntityStatus packetIn)
    {
        this.parent.handleEntityStatus(packetIn);
    }

    public void handleUpdateHealth(SPacketUpdateHealth packetIn)
    {
        this.parent.handleUpdateHealth(packetIn);
    }

    public void handleSetExperience(SPacketSetExperience packetIn)
    {
        this.parent.handleSetExperience(packetIn);
    }

    public void handleRespawn(SPacketRespawn packetIn)
    {
        this.parent.handleRespawn(packetIn);
    }

    public void handleExplosion(SPacketExplosion packetIn)
    {
        this.parent.handleExplosion(packetIn);
    }

    public void handleOpenWindow(SPacketOpenWindow packetIn)
    {
        this.parent.handleOpenWindow(packetIn);
    }

    public void handleSetSlot(SPacketSetSlot packetIn)
    {
        this.parent.handleSetSlot(packetIn);
    }

    public void handleConfirmTransaction(SPacketConfirmTransaction packetIn)
    {
        this.parent.handleConfirmTransaction(packetIn);
    }

    public void handleWindowItems(SPacketWindowItems packetIn)
    {
        this.parent.handleWindowItems(packetIn);
    }

    public void handleSignEditorOpen(SPacketSignEditorOpen packetIn)
    {
        this.parent.handleSignEditorOpen(packetIn);
    }

    public void handleUpdateTileEntity(SPacketUpdateTileEntity packetIn)
    {
        this.parent.handleUpdateTileEntity(packetIn);
    }

    public void handleWindowProperty(SPacketWindowProperty packetIn)
    {
        this.parent.handleWindowProperty(packetIn);
    }

    public void handleEntityEquipment(SPacketEntityEquipment packetIn)
    {
        this.parent.handleEntityEquipment(packetIn);
    }

    public void handleCloseWindow(SPacketCloseWindow packetIn)
    {
        this.parent.handleCloseWindow(packetIn);
    }

    public void handleBlockAction(SPacketBlockAction packetIn)
    {
        this.parent.handleBlockAction(packetIn);
    }

    public void handleBlockBreakAnim(SPacketBlockBreakAnim packetIn)
    {
        this.parent.handleBlockBreakAnim(packetIn);
    }

    public void handleChangeGameState(SPacketChangeGameState packetIn)
    {
        this.parent.handleChangeGameState(packetIn);
    }

    public void handleMaps(SPacketMaps packetIn)
    {
        this.parent.handleMaps(packetIn);
    }

    public void handleEffect(SPacketEffect packetIn)
    {
        this.parent.handleEffect(packetIn);
    }

    public void handleAdvancementInfo(SPacketAdvancementInfo packetIn)
    {
        this.parent.handleAdvancementInfo(packetIn);
    }

    public void handleSelectAdvancementsTab(SPacketSelectAdvancementsTab packetIn)
    {
        this.parent.handleSelectAdvancementsTab(packetIn);
    }

    public void handleStatistics(SPacketStatistics packetIn)
    {
        this.parent.handleStatistics(packetIn);
    }

    public void handleRecipeBook(SPacketRecipeBook packetIn)
    {
        this.parent.handleRecipeBook(packetIn);
    }

    public void handleEntityEffect(SPacketEntityEffect packetIn)
    {
        this.parent.handleEntityEffect(packetIn);
    }

    public void handleCombatEvent(SPacketCombatEvent packetIn)
    {
        this.parent.handleCombatEvent(packetIn);
    }

    public void handleServerDifficulty(SPacketServerDifficulty packetIn)
    {
        this.parent.handleServerDifficulty(packetIn);
    }

    public void handleCamera(SPacketCamera packetIn)
    {
        this.parent.handleCamera(packetIn);
    }

    public void handleWorldBorder(SPacketWorldBorder packetIn)
    {
        this.parent.handleWorldBorder(packetIn);
    }

    public void handleTitle(SPacketTitle packetIn)
    {
        this.parent.handleTitle(packetIn);
    }

    public void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter packetIn)
    {
        this.parent.handlePlayerListHeaderFooter(packetIn);
    }

    public void handleRemoveEntityEffect(SPacketRemoveEntityEffect packetIn)
    {
        this.parent.handleRemoveEntityEffect(packetIn);
    }

    public void handlePlayerListItem(SPacketPlayerListItem packetIn)
    {
        this.parent.handlePlayerListItem(packetIn);
    }

    public void handleKeepAlive(SPacketKeepAlive packetIn)
    {
        this.parent.handleKeepAlive(packetIn);
    }

    public void handlePlayerAbilities(SPacketPlayerAbilities packetIn)
    {
        this.parent.handlePlayerAbilities(packetIn);
    }

    public void handleTabComplete(SPacketTabComplete packetIn)
    {
        this.parent.handleTabComplete(packetIn);
    }

    public void handleSoundEffect(SPacketSoundEffect packetIn)
    {
        this.parent.handleSoundEffect(packetIn);
    }

    public void handleCustomSound(SPacketCustomSound packetIn)
    {
        this.parent.handleCustomSound(packetIn);
    }

    public void handleResourcePack(SPacketResourcePackSend packetIn)
    {
        this.parent.handleResourcePack(packetIn);
    }

    public void handleUpdateBossInfo(SPacketUpdateBossInfo packetIn)
    {
        this.parent.handleUpdateBossInfo(packetIn);
    }

    public void handleCooldown(SPacketCooldown packetIn)
    {
        this.parent.handleCooldown(packetIn);
    }

    public void handleMoveVehicle(SPacketMoveVehicle packetIn)
    {
        this.parent.handleMoveVehicle(packetIn);
    }

    public void handleCustomPayload(SPacketCustomPayload packetIn)
    {
        this.parent.handleCustomPayload(packetIn);
    }

    public void handleScoreboardObjective(SPacketScoreboardObjective packetIn)
    {
        this.parent.handleScoreboardObjective(packetIn);
    }

    public void handleUpdateScore(SPacketUpdateScore packetIn)
    {
        this.parent.handleUpdateScore(packetIn);
    }

    public void handleDisplayObjective(SPacketDisplayObjective packetIn)
    {
        this.parent.handleDisplayObjective(packetIn);
    }

    public void handleTeams(SPacketTeams packetIn)
    {
        this.parent.handleTeams(packetIn);
    }

    public void handleParticles(SPacketParticles packetIn)
    {
        this.parent.handleParticles(packetIn);
    }

    public void handleEntityProperties(SPacketEntityProperties packetIn)
    {
        this.parent.handleEntityProperties(packetIn);
    }
}
