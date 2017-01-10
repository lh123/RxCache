package com.lh.cachelibrary.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 2017/1/10.
 * 帮助生产Type
 */

public class TypeBuilder {
    private Type selfType;
    private List<Type> paramTypes;

    public static TypeBuilder newBuilder(){
        return new TypeBuilder();
    }

    private TypeBuilder() {
        paramTypes = new ArrayList<>();
    }

    public TypeBuilder setSelfType(Type selfType) {
        this.selfType = selfType;
        return this;
    }

    public TypeBuilder addParamType(Type paramType) {
        this.paramTypes.add(paramType);
        return this;
    }

    public Type build(){
        return new ParameterizedTypeImpl(selfType,paramTypes.toArray(new Type[paramTypes.size()]));
    }

    private static class ParameterizedTypeImpl implements ParameterizedType{
        private Type[] paramsType;
        private Type selfType;

        private ParameterizedTypeImpl(Type selfType, Type[] paramsType) {
            this.paramsType = paramsType;
            this.selfType = selfType;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return paramsType;
        }

        @Override
        public Type getRawType() {
            return selfType;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
