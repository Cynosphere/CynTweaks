package cynfoxwell.cyntweaks.glint;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RenderEffectVisitor extends MethodVisitor
{
    public RenderEffectVisitor(final MethodVisitor mv) {
        super(262144, mv);
    }

    public void visitLdcInsn(final Object c) {
        if (c.equals(-8372020)) {
            super.visitFieldInsn(Opcodes.GETSTATIC, "cynfoxwell/cyntweaks/CynTweaks", "finalGlintColor", "I");
        }
        else {
            super.visitLdcInsn(c);
        }
    }
}
