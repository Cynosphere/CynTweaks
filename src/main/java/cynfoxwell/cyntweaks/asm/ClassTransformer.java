package cynfoxwell.cyntweaks.asm;

import cynfoxwell.cyntweaks.asm.patches.TabListPing;
import cynfoxwell.cyntweaks.glint.RenderEffectVisitor;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.function.BiConsumer;

public class ClassTransformer implements IClassTransformer {

    String pingClass = "net.minecraft.client.gui.GuiPlayerTabOverlay";
    String pingClassObf = "bjq";
    String pingDesc = "(IIILnet/minecraft/client/network/NetworkPlayerInfo;)V";
    String pingDescObf = "(IIILbsc;)V";
    String pingMethod = "drawPing";
    String pingMethodObf = "a";

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name.equals(pingClass) || name.equals(pingClassObf))
        {

            System.out.println("[NumericPing] Found " + pingClass + ", patching...");
            ClassNode classNode = readClassFromBytes(bytes);
            MethodNode method = findMethodNodeOfClass(classNode, CTASM.devEnv ? pingMethod : pingMethodObf, CTASM.devEnv
                    ? pingDesc : pingDescObf);

            method.localVariables = null;
            method.instructions.clear();
            method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            method.instructions.add(new VarInsnNode(Opcodes.ILOAD, 1));
            method.instructions.add(new VarInsnNode(Opcodes.ILOAD, 2));
            method.instructions.add(new VarInsnNode(Opcodes.ILOAD, 3));
            method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 4));
            method.instructions.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC, Type.getInternalName(TabListPing.class), pingMethod, CTASM.devEnv
                    ? pingDesc : pingDescObf,
                    false));
            method.instructions.add(new InsnNode(Opcodes.RETURN));
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(cw);
            bytes = cw.toByteArray();

            return bytes;
        }

        if (transformedName.equals("net.minecraft.client.Minecraft")) {
            System.out.println("[MemoryFix] Removing System.gc calls.");
            return transformMethods(bytes, this::transformMinecraft);
        }

        if (transformedName.equals("net.minecraft.client.renderer.RenderItem")) {
            System.out.println("[ColoredGlint] Attempting to patch RenderItem");
            ClassReader reader = new ClassReader(bytes);
            ClassWriter writer = new ClassWriter(reader, 2);
            ClassVisitor visitor = new ClassVisitor(262144, writer) {
                public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                    if (CTASM.devEnv ? name.equals("renderEffect") : name.equals("a") && CTASM.devEnv ? desc.equals("(Lnet/minecraft/client/renderer/block/model/IBakedModel;)V") : desc.equals("(Lcfy;)V")) {
                        System.out.println("[ColoredGlint] Found renderEffect, patching...");
                        return new RenderEffectVisitor(mv);
                    }
                    return mv;
                }
            };
            reader.accept(visitor, 0);
            return writer.toByteArray();
        }

        return bytes;
    }

    private ClassNode readClassFromBytes(byte[] bytes)
    {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        return classNode;
    }

    private MethodNode findMethodNodeOfClass(ClassNode classNode, String methodName, String methodDesc)
    {
        for (MethodNode method : classNode.methods)
        {
            if (method.name.equals(methodName) && method.desc.equals(methodDesc))
            {
                return method;
            }
        }
        return null;
    }

    public AbstractInsnNode findFirstInstruction(MethodNode method)
    {
        return getOrFindInstruction(method.instructions.getFirst());
    }

    public AbstractInsnNode getOrFindInstruction(AbstractInsnNode firstInsnToCheck)
    {
        return getOrFindInstruction(firstInsnToCheck, false);
    }

    public AbstractInsnNode getOrFindInstruction(AbstractInsnNode firstInsnToCheck, boolean reverseDirection)
    {
        for (AbstractInsnNode instruction = firstInsnToCheck; instruction != null; instruction = reverseDirection
                ? instruction.getPrevious() : instruction.getNext())
        {
            if (instruction.getType() != AbstractInsnNode.LABEL && instruction.getType() != AbstractInsnNode.LINE)
                return instruction;
        }
        return null;
    }

    private byte[] writeClassToBytes(ClassNode classNode)
    {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private byte[] transformMethods(byte[] bytes, BiConsumer<ClassNode, MethodNode> transformer) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        classNode.methods.forEach(m -> transformer.accept(classNode, m));

        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private void transformMinecraft(ClassNode clazz, MethodNode method) {
        Iterator<AbstractInsnNode> iter = method.instructions.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (insn.getOpcode() == Opcodes.INVOKESTATIC) {
                MethodInsnNode methodInsn = (MethodInsnNode) insn;
                if (methodInsn.owner.equals("java/lang/System") && methodInsn.name.equals("gc")) {
                    iter.remove();
                }
            }
        }
    }
}
