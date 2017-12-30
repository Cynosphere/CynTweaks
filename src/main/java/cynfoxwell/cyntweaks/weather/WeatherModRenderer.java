package cynfoxwell.cyntweaks.weather;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.IRenderHandler;

import java.util.Random;

public class WeatherModRenderer extends IRenderHandler {
    private final Random random;
    private final ResourceLocation locationSnowPng;
    private final ResourceLocation locationRainPng;
    public int rendererUpdateCount;
    private float[] rainXCoords;
    private float[] rainYCoords;

    public WeatherModRenderer() {
        this.random = new Random();
        this.locationSnowPng = new ResourceLocation("textures/environment/snow.png");
        this.locationRainPng = new ResourceLocation("textures/environment/rain.png");
        this.rainXCoords = new float[1024];
        this.rainYCoords = new float[1024];
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                final float f = j - 16;
                final float f2 = i - 16;
                final float f3 = MathHelper.sqrt(f * f + f2 * f2);
                this.rainXCoords[i << 5 | j] = -f2 / f3;
                this.rainYCoords[i << 5 | j] = f / f3;
            }
        }
    }

    public void render(final float partialTicks, final WorldClient world, final Minecraft mc) {
        if(CynTweaks.wmodType == 0){
            return;
        }
        final EntityRenderer renderer = mc.entityRenderer;
        renderer.enableLightmap();
        final Entity entity = mc.getRenderViewEntity();
        final int i = MathHelper.floor(entity.posX);
        final int j = MathHelper.floor(entity.posY);
        final int k = MathHelper.floor(entity.posZ);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder worldrenderer = tessellator.getBuffer();
        GlStateManager.disableCull();
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1f);
        final double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        final double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        final double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        final int l = MathHelper.floor(d2);
        int i2 = 5;
        if (mc.gameSettings.fancyGraphics) {
            i2 = 10;
        }
        int j2 = -1;
        final float f1 = this.rendererUpdateCount + partialTicks;
        worldrenderer.setTranslation(-d0, -d2, -d3);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for (int k2 = k - i2; k2 <= k + i2; ++k2) {
            for (int l2 = i - i2; l2 <= i + i2; ++l2) {
                final int i3 = (k2 - k + 16) * 32 + l2 - i + 16;
                final double d4 = this.rainXCoords[i3] * 0.5;
                final double d5 = this.rainYCoords[i3] * 0.5;
                blockpos$mutableblockpos.setPos(l2, 0, k2);
                final int j3 = world.getPrecipitationHeight((BlockPos)blockpos$mutableblockpos).getY();
                int k3 = j - i2;
                int l3 = j + i2;
                if (k3 < j3) {
                    k3 = j3;
                }
                if (l3 < j3) {
                    l3 = j3;
                }
                int i4;
                if ((i4 = j3) < l) {
                    i4 = l;
                }
                if (k3 != l3) {
                    this.random.setSeed(l2 * l2 * 3121 + l2 * 45238971 ^ k2 * k2 * 418711 + k2 * 13761);
                    blockpos$mutableblockpos.setPos(l2, k3, k2);

                    if(CynTweaks.wmodType == 1) {
                        if (j2 != 0) {
                            j2 = 0;
                            mc.getTextureManager().bindTexture(this.locationRainPng);
                            worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }

                        double d6 = -((double)(this.rendererUpdateCount + l2 * l2 * 3121 + l2 * 45238971 + k2 * k2 * 418711 + k2 * 13761 & 31) + (double)partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
                        double d7 = (double)((float)l2 + 0.5F) - entity.posX;
                        double d8 = (double)((float)k2 + 0.5F) - entity.posZ;
                        float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float)i2;
                        float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * 1.0f;
                        blockpos$mutableblockpos.setPos(l2, i3, k2);
                        int j4 = world.getCombinedLight(blockpos$mutableblockpos, 0);
                        int k4 = j4 >> 16 & 65535;
                        int l4 = j4 & 65535;
                        worldrenderer.pos((double)l2 - d4 + 0.5D, (double)l3, (double)k2 - d5 + 0.5D).tex(0.0D, (double)k3 * 0.25D + d6 + d8).color(1.0F, 1.0F, 1.0F, f4).lightmap(k4, l4).endVertex();
                        worldrenderer.pos((double)l2 + d4 + 0.5D, (double)l3, (double)k2 + d5 + 0.5D).tex(1.0D, (double)k3 * 0.25D + d6 + d8).color(1.0F, 1.0F, 1.0F, f4).lightmap(k4, l4).endVertex();
                        worldrenderer.pos((double)l2 + d4 + 0.5D, (double)k3, (double)k2 + d5 + 0.5D).tex(1.0D, (double)l3 * 0.25D + d6 + d8).color(1.0F, 1.0F, 1.0F, f4).lightmap(k4, l4).endVertex();
                        worldrenderer.pos((double)l2 - d4 + 0.5D, (double)k3, (double)k2 - d5 + 0.5D).tex(0.0D, (double)l3 * 0.25D + d6 + d8).color(1.0F, 1.0F, 1.0F, f4).lightmap(k4, l4).endVertex();
                    }

                    if(CynTweaks.wmodType == 2) {
                        if (j2 != 1) {
                            j2 = 1;
                            mc.getTextureManager().bindTexture(this.locationSnowPng);
                            worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }
                        final double d6 = ((this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0f;
                        final double d7 = this.random.nextDouble() + f1 * 0.01 * (float) this.random.nextGaussian();
                        final double d8 = this.random.nextDouble() + f1 * (float) this.random.nextGaussian() * 0.001;
                        final double d9 = l2 + 0.5f - entity.posX;
                        final double d10 = k2 + 0.5f - entity.posZ;
                        final float f2 = MathHelper.sqrt(d9 * d9 + d10 * d10) / i2;
                        final float f3 = ((1.0f - f2 * f2) * 0.3f + 0.5f) * 1.0f;
                        blockpos$mutableblockpos.setPos(l2, i4, k2);
                        final int i5 = (world.getCombinedLight(blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
                        final int j4 = i5 >> 16 & 0xFFFF;
                        final int k4 = i5 & 0xFFFF;
                        worldrenderer.pos(l2 - d4 + 0.5, (double) k3, k2 - d5 + 0.5).tex(0.0 + d7, k3 * 0.25 + d6 + d8).color(1.0f, 1.0f, 1.0f, f3).lightmap(j4, k4).endVertex();
                        worldrenderer.pos(l2 + d4 + 0.5, (double) k3, k2 + d5 + 0.5).tex(1.0 + d7, k3 * 0.25 + d6 + d8).color(1.0f, 1.0f, 1.0f, f3).lightmap(j4, k4).endVertex();
                        worldrenderer.pos(l2 + d4 + 0.5, (double) l3, k2 + d5 + 0.5).tex(1.0 + d7, l3 * 0.25 + d6 + d8).color(1.0f, 1.0f, 1.0f, f3).lightmap(j4, k4).endVertex();
                        worldrenderer.pos(l2 - d4 + 0.5, (double) l3, k2 - d5 + 0.5).tex(0.0 + d7, l3 * 0.25 + d6 + d8).color(1.0f, 1.0f, 1.0f, f3).lightmap(j4, k4).endVertex();
                    }
                }
            }
        }
        if (j2 >= 0) {
            tessellator.draw();
        }
        worldrenderer.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1f);
        renderer.disableLightmap();
    }
}
