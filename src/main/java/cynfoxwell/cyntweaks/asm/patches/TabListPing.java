package cynfoxwell.cyntweaks.asm.patches;

import cynfoxwell.cyntweaks.CynTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TabListPing {
    public static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    private static Minecraft mc;
    private static float zLevel = 0F;

    private static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public static void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn)
    {
        mc = Minecraft.getMinecraft();
        if(CynTweaks.tabPing == true) {
            int color = 0xFF0000;
            if (networkPlayerInfoIn.getResponseTime() < 0) {
                color = 0x00FF00;
            } else if (networkPlayerInfoIn.getResponseTime() < 150) {
                color = 0x88FF00;
            } else if (networkPlayerInfoIn.getResponseTime() < 300) {
                color = 0xFFFF00;
            } else if (networkPlayerInfoIn.getResponseTime() < 600) {
                color = 0xFF8800;
            } else if (networkPlayerInfoIn.getResponseTime() < 600) {
                color = 0xFF0000;
            }

            Minecraft.getMinecraft().fontRenderer.drawString(networkPlayerInfoIn.getResponseTime() + "ms", p_175245_2_ + p_175245_1_ - 11, p_175245_3_, color);
        }else{
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(ICONS);
            int index;

            if (networkPlayerInfoIn.getResponseTime() < 0)
            {
                index = 5;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 150)
            {
                index = 0;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 300)
            {
                index = 1;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 600)
            {
                index = 2;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 1000)
            {
                index = 3;
            }
            else
            {
                index = 4;
            }

            zLevel += 100.0F;
            drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0, 176 + index * 8, 10, 8);
            zLevel -= 100.0F;
        }
    }
}
