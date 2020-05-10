package com.paul.song.analytics.plugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Created by songjinghao on 2019/10/18.
 */
class ShadowlessClassVisitor extends ClassVisitor {

    private final static String SDK_API_CLASS = "com/paul/song/analytics/sdk/ShadowlessTrackHelper"

    private String className
    private String superName
    private String[] mInterfaces
    private ClassVisitor classVisitor

    private HashMap<String, ShadowlessMethodCell> mLambdaMethodCells = new HashMap<>()

    ShadowlessClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor)
        this.classVisitor = classVisitor
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
        this.superName = superName
        mInterfaces = interfaces
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)

        String nameDesc = name + desc

        methodVisitor = new ShadowlessDefaultMethodVisitor(methodVisitor, access, name, desc) {
            boolean isShadowlessTrackViewOnClickAnnotation = false
            boolean isShadowlessTrackVoiceAnnotation = false
            boolean isShadowlessTrackIntentAnnotation = false
            String extraNameForIntent
            ArrayList<Integer> parameterIndexs = new ArrayList()

            @Override
            AnnotationVisitor visitAnnotation(String s, boolean b) {
                if (s == 'Lcom/paul/song/analytics/sdk/ShadowlessTrackViewOnClick;') {
                    isShadowlessTrackViewOnClickAnnotation = true
                } else if (s == 'Lcom/paul/song/analytics/sdk/ShadowlessTrackVoice;') {
                    isShadowlessTrackVoiceAnnotation = true
                } else if (s == 'Lcom/paul/song/analytics/sdk/ShadowlessTrackIntent;') {
                    isShadowlessTrackIntentAnnotation = true
                }

//                return super.visitAnnotation(s, b)
                AnnotationVisitor av = super.visitAnnotation(s, b)
                av = new ShadowlessDefaultAnnotationVisitor(av) {
                    @Override
                    void visit(String name1, Object value) {
                        super.visit(name1, value)
                        if (name1 == ShadowlessConstants.ANNOTATION_VALUE_EXTRANAME) {
                            extraNameForIntent = value.toString()
                        }
                    }

                    @Override
                    AnnotationVisitor visitAnnotation(String name1, String desc1) {
                        return super.visitAnnotation(name1, desc1)
                    }

                    @Override
                    AnnotationVisitor visitArray(String name1) {
                        return super.visitArray(name1)
                    }
                }
                return av
            }

            @Override
            void visitParameter(String name1, int access1) {
                super.visitParameter(name1, access1)
            }

            @Override
            void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
                super.visitLocalVariable(s, s1, s2, label, label1, i)
            }

            @Override
            AnnotationVisitor visitParameterAnnotation(int parameter, String desc1, boolean visible) {
                if (desc1 == 'Lcom/paul/song/analytics/sdk/ShadowlessTrackParameter;') {
                    parameterIndexs.add(parameter)
                }
                return super.visitParameterAnnotation(parameter, desc1, visible)
            }

            @Override
            void visitInvokeDynamicInsn(String name1, String desc1, Handle bsm, Object... bsmArgs) {
                super.visitInvokeDynamicInsn(name1, desc1, bsm, bsmArgs)

                try {
                    String desc2 = (String) bsmArgs[0]
                    ShadowlessMethodCell methodCell = ShadowlessHookConfig.sInterfaceMethods.get(Type.getReturnType(desc1).getDescriptor() + name1 + desc2)
                    if (methodCell) {
                        Handle handle = (Handle) bsmArgs[1]
                        mLambdaMethodCells.put(handle.name + handle.desc, methodCell)
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }

            /** 在方法最前面插入 */
            @Override
            protected void onMethodEnter() {
                super.onMethodEnter()

                ShadowlessMethodCell lambdaMethodCell = mLambdaMethodCells.get(nameDesc)
                if (lambdaMethodCell) {
                    Type[] types = Type.getArgumentTypes(lambdaMethodCell.desc)
                    int length = types.length
                    Type[] lambdaTypes = Type.getArgumentTypes(desc)
                    int paramStart = lambdaTypes.length - length
                    if (paramStart < 0) {
                        return
                    } else {
                        for (int i = 0; i < length; i++) {
                            if (lambdaTypes[paramStart + i].descriptor != types[i].descriptor) {
                                return
                            }
                        }
                    }
                    boolean isStaticMethod = ShadowlessUtils.isStatic(access)
                    if (!isStaticMethod) {
                        if (lambdaMethodCell.desc == '(Landroid/view/MenuItem;)Z') {
                            methodVisitor.visitVarInsn(ALOAD, 0)
                            methodVisitor.visitVarInsn(ALOAD, getVisitPosition(lambdaTypes, paramStart, isStaticMethod))
                            methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, lambdaMethodCell.agentName, '(Ljava/lang/Object;Landroid/view/MenuItem;)V', false)
                            return
                        }
                    }

                    for (int i = paramStart; i < paramStart + lambdaMethodCell.paramsCount; i++) {
                        methodVisitor.visitVarInsn(lambdaMethodCell.opcodes.get(i - paramStart), getVisitPosition(lambdaTypes, i, isStaticMethod))
                    }
                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, lambdaMethodCell.agentName, lambdaMethodCell.agentDesc, false)
                    return
                }

                if (isShadowlessTrackIntentAnnotation) {
                    if (nameDesc == 'onStartCommand(Landroid/content/Intent;II)I'
                            || nameDesc == 'onHandleIntent(Landroid/content/Intent;)V') {
                        methodVisitor.visitVarInsn(ALOAD, 0)
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitLdcInsn(extraNameForIntent)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackIntentFromService", "(Ljava/lang/Object;Landroid/content/Intent;Ljava/lang/String;)V", false)
                        return
                    }
                }

                if (isShadowlessTrackViewOnClickAnnotation) {
                    if (desc == '(Landroid/view/View;)V') {
                        methodVisitor.visitVarInsn(ALOAD, 1)
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackViewOnClick", "(Landroid/view/View;)V", false)
                        return
                    }
                }

                if (isShadowlessTrackVoiceAnnotation) {
                    int paramCount = parameterIndexs.size()
                    if (paramCount > 0) {
//                        methodVisitor.visitVarInsn(ALOAD, 0)
                        if (paramCount <=  5) {
                            methodVisitor.visitInsn(ICONST_0 + paramCount)
                        } else {
                            methodVisitor.visitVarInsn(BIPUSH, paramCount)
                        }
                        mv.visitTypeInsn(ANEWARRAY, "java/lang/Object")
                        int index = 0
                        for (int parameter : parameterIndexs) {
                            methodVisitor.visitInsn(DUP)
                            methodVisitor.visitInsn(ICONST_0 + index)
                            methodVisitor.visitVarInsn(ALOAD, parameter + 1)
                            methodVisitor.visitInsn(AASTORE)
                            index++
                        }
                    }

                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackVoice", "([Ljava/lang/Object;)V", false)
                    return
                }

                Iterator<ShadowlessMethodCell> lMIterator = ShadowlessHookConfig.sLifecycleMethods.iterator()
                while (lMIterator.hasNext()) {
                    ShadowlessMethodCell cell = lMIterator.next()
                    if (name == cell.name && desc == cell.desc && (isActivity() || (isFragment() && !isReportFragment()))) {
                        int paramStart = cell.paramsStart
                        for (int i = paramStart; i < cell.paramsStart + cell.paramsCount; i++) {
                            methodVisitor.visitVarInsn(cell.opcodes[i - paramStart], i)
                        }
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, cell.agentName, cell.agentDesc, false)
                        break
                    }
                }

                Iterator<ShadowlessMethodCell> oMIterator = ShadowlessHookConfig.sOnlyMethods.iterator()
                while (oMIterator.hasNext()) {
                    ShadowlessMethodCell cell = oMIterator.next()
                    if (name == cell.name && desc == cell.desc) {
                        int paramStart = cell.paramsStart
                        for (int i = paramStart; i < cell.paramsStart + cell.paramsCount; i++) {
                            methodVisitor.visitVarInsn(cell.opcodes[i - paramStart], i)
                        }
                        methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, cell.agentName, cell.agentDesc, false)
                        break
                    }
                }

                if ((mInterfaces && mInterfaces.length > 0)) {
                    Iterator<ShadowlessMethodCell> iMIterator = ShadowlessHookConfig.sInterfaceMethods.values().iterator()
                    while (iMIterator.hasNext()) {
                        ShadowlessMethodCell cell = iMIterator.next()
                        String parentName = cell.parent.substring(1, cell.parent.length() - 1)
                        if (mInterfaces.contains(parentName) && name == cell.name && desc == cell.desc) {
                            int paramStart = cell.paramsStart
                            for (int i = paramStart; i < cell.paramsStart + cell.paramsCount; i++) {
                                methodVisitor.visitVarInsn(cell.opcodes[i - paramStart], i)
                            }
                            methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, cell.agentName, cell.agentDesc, false)
                            break
                        }
                    }
                }

                if (className && !className.isEmpty()) {
                    Iterator<ShadowlessMethodCell> cMIterator = ShadowlessHookConfig.sClassMethods.values().iterator()
                    while (cMIterator.hasNext()) {
                        ShadowlessMethodCell cell = cMIterator.next()
                        if (cell.parent.isEmpty()) {
                            continue
                        }
                        String parentName = cell.parent.substring(1, cell.parent.length() - 1)
                        if (className.contains(parentName) && name == cell.name && desc == cell.desc) {
                            int paramStart = cell.paramsStart
                            for (int i = paramStart; i < cell.paramsStart + cell.paramsCount; i++) {
                                methodVisitor.visitVarInsn(cell.opcodes[i - paramStart], i)
                            }
                            methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, cell.agentName, cell.agentDesc, false)
                            break
                        }
                    }
                }

                /*if (className == 'com/paul/song/medialibrary/player/AudioPlayer'
                        && nameDesc == 'play(Lcom/paul/song/medialibrary/entity/TrackEntity;)V') {
                    println("Here!!! AudioPlayer.paly()")
                    methodVisitor.visitVarInsn(ALOAD, 1)
                    methodVisitor.visitMethodInsn(INVOKESTATIC, SDK_API_CLASS, "trackPlay", "(Lcom/paul/song/medialibrary/entity/TrackEntity;)V", false)
                }*/
            }

            @Override
            void visitEnd() {
                super.visitEnd()

                if (mLambdaMethodCells.containsKey(nameDesc)) {
                    mLambdaMethodCells.remove(nameDesc)
                }
            }

        }
        return methodVisitor
    }

    /**
     * 获取方法参数下标为 index 的对应 ASM index
     * @param types 方法参数类型数组
     * @param index 方法中参数下标，从 0 开始
     * @param isStaticMethod 该方法是否为静态方法
     * @return 访问该方法的 index 位参数的 ASM index
     */
    int getVisitPosition(Type[] types, int index, boolean isStaticMethod) {
        if (types == null || index < 0 || index >= types.length) {
            throw new Error("getVisitPosition error")
        }
        if (index == 0) {
            return isStaticMethod ? 0 : 1
        } else {
            return getVisitPosition(types, index - 1, isStaticMethod) + types[index - 1].getSize()
        }
    }

    boolean isActivity() {
        if (className == null || className.isEmpty()) {
            return false
        }
        if (className == ShadowlessConstants.v4_FRAGMENT_ACTIVITY_NAME
                || className == ShadowlessConstants.ANDROIDX_FRAGMENT_ACTIVITY_NAME
                || superName == ShadowlessConstants.ACTIVITY_NAME
                || superName == ShadowlessConstants.V7_COMPATACTIVITY_NAME
                || superName == ShadowlessConstants.ANDROIDX_COMPATACTIVITY_NAME) {
            return true
        }
        return false
    }

    boolean isCustomService() {
        if (className == null || className.isEmpty()) {
            return false
        }
        if ((superName == ShadowlessConstants.SERVICE_NAME || superName == ShadowlessConstants.INTENTSERVICE_NAME) && !className.startsWith("android")) {
            return true
        }
        return false
    }

    boolean isFragment() {
        if (className == null || className.isEmpty()) {
            return false
        }
        if (className == ShadowlessConstants.V4_FRAGMENT_NAME
                || className == ShadowlessConstants.ANDROIDX_FRAGMENT_NAME
                || superName == ShadowlessConstants.FRAGMENT_NAME
                || superName == ShadowlessConstants.V4_FRAGMENT_NAME
                || superName == ShadowlessConstants.ANDROIDX_FRAGMENT_NAME) {
            return true
        }
        return false
    }

    boolean isReportFragment() {
        if (className == null || className.isEmpty()) {
            return false
        }
        if (className.contains(ShadowlessConstants.ANDROID_LIFECYCLE_PREFIX)
                || className.contains(ShadowlessConstants.ANDROIDX_LIFECYCLE_PREFIX)) {
            return true
        }
        return false
    }
}
