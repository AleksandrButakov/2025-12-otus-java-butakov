package ru.anbn.logging.asm;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class LogClassTransformer implements ClassFileTransformer {
    @Override
    @SuppressWarnings("java:S1168") // null is valid for ClassFileTransformer
    public byte[] transform(
            Module module,
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classFileBuffer) {

        // transform only application classes
        if (!className.startsWith("ru/anbn/logging/target")) {
            // returning null means "no transformation"
            return null;
        }

        ClassReader reader = new ClassReader(classFileBuffer);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        ClassVisitor visitor = new LogClassVisitor(writer);
        reader.accept(visitor, ClassReader.EXPAND_FRAMES);

        return writer.toByteArray();
    }
}
