package com.diabin.latte.compiler;

import com.diabin.latte.annotations.PayEntryGenerator;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Copyright (C)
 *
 * @file: EntryVisitor
 * @author: 345
 * @Time: 2019/4/24 18:55
 * @description: ${DESCRIPTION}
 */
public final class EntryVisitor extends SimpleAnnotationValueVisitor7<Void,Void> {
    private Filer mFiler = null;
    private TypeMirror mTypeMirror = null;
    private String mPackageName = null;

    public void setFiler(Filer filer) {
        this.mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        return p;
    }

    @Override
    public Void visitType(TypeMirror t, Void p) {
        mTypeMirror = t;
        generateJavaCode();
        return p;
    }


    private void generateJavaCode() {
        //帮我们 生成所需要的类 ，微信规定入口文件的名字必须为 WXEntryActivity
        final TypeSpec targetActivity = TypeSpec.classBuilder("WXEntryActivity")
                //生成的类 为public
                .addModifiers(Modifier.PUBLIC)
                //被 final修饰
                .addModifiers(Modifier.FINAL)
                //继承于 那个类
                .superclass(TypeName.get(mTypeMirror))
                .build();

        //生成文件 ，传入packageName ， 微信规定包名必须加上 wxapi
        final JavaFile javaFile = JavaFile.builder(mPackageName+".wxapi",targetActivity)
                //注释
                .addFileComment("微信入口文件")
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
