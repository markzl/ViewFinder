package com.markzl.android.compiler.model;

import com.markzl.android.compiler.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * <pre>
 * @autor markzl
 * @date 2018/6/8
 * @desc
 * </pre>
 */

public class AnnotatedClass {

    public TypeElement mClassElement;

    public List<BindViewField> mFields;

    public List<OnClickMethod> mMethods;

    public Elements mElementUtils;

    public AnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mFields = new ArrayList<>();
        this.mMethods = new ArrayList<>();
        this.mElementUtils = elementUtils;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    public void addField(BindViewField field) {
        mFields.add(field);
    }

    public void addMethod(OnClickMethod method) {
        mMethods.add(method);
    }

    public JavaFile generateFinder() {

        //生成inject方法的参数
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)//访问权限
                .addAnnotation(Override.class)//注解
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(TypeUtil.PROVIDER, "provider");
        //在inject方法中，生成重复的findViewById(R.id.xxx)的语句
        for (BindViewField field : mFields) {
            methodBuilder.addStatement(
                    "host.$N = ($T)(provider.findView(source,$L))",
                    field.getFieldName(),
                    ClassName.get(field.getFieldType()),
                    field.getResId());
        }

        if (mMethods.size() > 0) {
            methodBuilder.addStatement("$T listener", TypeUtil.ANDROID_ON_CLICK_LISTENER);
        }
        for (OnClickMethod method : mMethods) {
            //declare OnClickListener anonymous class
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ANDROID_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("host.$N()", method.getMethodName())
                            .build())
                    .build();
            methodBuilder.addStatement("listener = $L ", listener);
            for (int id : method.ids) {
                //set listeners
                methodBuilder.addStatement("provider.findView(source,$L).setOnClickListener(listener)", id);
            }

        }
        //生成Host$$Finder类
        TypeSpec finderClass = TypeSpec
                .classBuilder(mClassElement.getSimpleName() + "$$Finder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.FINDER, TypeName.get(mClassElement.asType())))
                .addMethod(methodBuilder.build())
                .build();

        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
        return JavaFile.builder(packageName, finderClass).build();
    }


}
