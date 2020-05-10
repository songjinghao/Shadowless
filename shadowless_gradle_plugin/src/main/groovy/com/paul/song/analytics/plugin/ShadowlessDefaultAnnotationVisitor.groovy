package com.paul.song.analytics.plugin;

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes;

class ShadowlessDefaultAnnotationVisitor extends AnnotationVisitor {

    ShadowlessDefaultAnnotationVisitor(AnnotationVisitor av) {
        super(Opcodes.ASM6, av)
    }

    @Override
    void visit(String name, Object value) {
        super.visit(name, value)
    }

    @Override
    void visitEnum(String name, String desc, String value) {
        super.visitEnum(name, desc, value)
    }

    @Override
    AnnotationVisitor visitAnnotation(String name, String desc) {
        return super.visitAnnotation(name, desc)
    }

    @Override
    AnnotationVisitor visitArray(String name) {
        return super.visitArray(name)
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }
}
