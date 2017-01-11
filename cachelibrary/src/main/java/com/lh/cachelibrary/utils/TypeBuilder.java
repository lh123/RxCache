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

    private TypeBuilder parent;
    private Type selfType;
    private List<Type> paramTypes;

    public static TypeBuilder newBuilder(Type type) {
        return new TypeBuilder(null, type);
    }

    private TypeBuilder(TypeBuilder parent, Type selfType) {
        this.parent = parent;
        this.selfType = selfType;
        this.paramTypes = new ArrayList<>();
    }

    public TypeBuilder addParamType(Type paramType) {
        this.paramTypes.add(paramType);
        return this;
    }

    public TypeBuilder beginNestedType(Type type) {
        return new TypeBuilder(this, type);
    }

    public TypeBuilder endNestedType() {
        if (parent == null) {
            throw new IllegalStateException("you should call beginNestedType first");
        }
        parent.addParamType(buidType());
        return parent;
    }

    private Type buidType() {
        if (paramTypes.size() == 0) {
            return selfType;
        }
        return new ParameterizedTypeImpl(selfType, paramTypes.toArray(new Type[paramTypes.size()]));
    }

    public Type build() {
        if (parent != null) {
            throw new IllegalStateException("you should call endNestedType first");
        }
        return buidType();
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
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
