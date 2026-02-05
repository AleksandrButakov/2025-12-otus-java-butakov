package ru.anbn.logging.asm;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;

public class LogMethodVisitor extends MethodVisitor {
    private final String className;
    private final String methodName;
    private boolean logAnnotationPresent = false;

    public LogMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(ASM9, methodVisitor);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if ("Lru/anbn/logging/common/Log;".equals(descriptor)) {
            logAnnotationPresent = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (!logAnnotationPresent) {
            return;
        }

        // Inject logging at method entry
        mv.visitLdcInsn(className.replace('/', '.'));
        mv.visitMethodInsn(
                INVOKESTATIC, "org/slf4j/LoggerFactory", "getLogger", "(Ljava/lang/String;)Lorg/slf4j/Logger;", false);

        mv.visitLdcInsn("executed method: " + methodName);
        mv.visitMethodInsn(INVOKEINTERFACE, "org/slf4j/Logger", "info", "(Ljava/lang/String;)V", true);
    }
}
