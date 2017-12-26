package com.sososeen09.multitypejsonparser.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by yunlong.su on 2017/6/9.
 */

public class MultiTypeJsonParser<T> {
    private HashMap<String, Class<? extends T>> typeClassMap = new HashMap<>();
    private String typeElementName;
    private String typeElementValue;
    private Class<T> targetClass;
    private Type targetUpperLevelClass;
    private Gson parseGson;
    private Gson targetParseGson; //只对目标类进行解析,不包含外层的类
    private Builder<T> mBuilder;

    private MultiTypeJsonParser() {
    }

    public <V> V fromJson(String json, Class<V> jsonFeedsClass) {
        return parseGson.fromJson(json, jsonFeedsClass);
    }

    public <V> V fromJson(String json, Type jsonFeedsClass) {
        return parseGson.fromJson(json, jsonFeedsClass);
    }

    private void setTypeElementValue(String typeElementValue) {
        this.typeElementValue = typeElementValue;
    }

    private String getTypeElementValue() {
        return typeElementValue;
    }

    private Builder<T> getBuilder() {
        return mBuilder;
    }

    /**
     * 获取注册过的类型个数
     * @return
     */
    public int getSupportTypeSize() {
        return typeClassMap.size();
    }

    /**
     * 获取只解析目标类这一层的Gson
     *
     * @return
     */
    public Gson getTargetParseGson() {
        if (targetParseGson == null) {
            targetParseGson = new GsonBuilder().registerTypeAdapter(targetClass, this.getBuilder().getTypeAdapter()).create();
        }
        return targetParseGson;
    }

    /**
     * 获取可以解析包含目标类外层的Gson
     *
     * @return
     */
    public Gson getParseGson() {
        return parseGson;
    }

    public static class Builder<T> {
        private MultiTypeJsonParser<T> multiTypeJsonParser;
        private TargetDeserializer typeAdapter;

        public Builder() {
            multiTypeJsonParser = new MultiTypeJsonParser<>();
            multiTypeJsonParser.mBuilder = this;
        }

        /**
         * 注册能够表示type类型的字段名
         *
         * @param typeElementName
         * @return
         */
        public Builder<T> registerTypeElementName(String typeElementName) {
            multiTypeJsonParser.typeElementName = typeElementName;
            return this;
        }

        /**
         * 注册type对应的value值以及整个value值对应的Class
         *
         * @param typeElementValue
         * @param classValue
         * @return
         */
        public Builder<T> registerTypeElementValueWithClassType(String typeElementValue, Class<? extends T> classValue) {
            multiTypeJsonParser.typeClassMap.put(typeElementValue, classValue);
            return this;
        }

        /**
         * 注册需要需要转换的类，通常这个类是一个父类
         *
         * @param adaptedClass
         * @return
         */
        public Builder<T> registerTargetClass(Class<T> adaptedClass) {
            multiTypeJsonParser.targetClass = adaptedClass;
            return this;
        }

        /**
         * 注册包裹在被转换的类的上一层的类
         *
         * @param adaptedUpperLevelClass
         * @return
         */
        public Builder<T> registerTargetUpperLevelClass(Type adaptedUpperLevelClass) {
            multiTypeJsonParser.targetUpperLevelClass = adaptedUpperLevelClass;
            return this;
        }

        public MultiTypeJsonParser<T> build() {
            typeAdapter = new TargetDeserializer(multiTypeJsonParser);
            GsonBuilder gsonBuilder = new GsonBuilder();

            if (multiTypeJsonParser.targetClass == null) {
                throw new IllegalStateException("targetClass can not be Null: ");
            }

            gsonBuilder.registerTypeAdapter(multiTypeJsonParser.targetClass, typeAdapter);

            if (multiTypeJsonParser.targetUpperLevelClass != null) {
                gsonBuilder.registerTypeAdapter(multiTypeJsonParser.targetUpperLevelClass, new TargetUpperLevelDeserializer(multiTypeJsonParser));
            }

            multiTypeJsonParser.parseGson = gsonBuilder.create();
            return multiTypeJsonParser;
        }

        private TargetDeserializer getTypeAdapter() {
            return typeAdapter;
        }

        private String getString(JsonElement jsonElement) {
            return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
        }

        private boolean checkHasRegistered(String typeValue) {
            return multiTypeJsonParser.typeClassMap.containsKey(typeValue);
        }

        private class TargetDeserializer implements JsonDeserializer<T> {

            private final MultiTypeJsonParser<T> mGeneralJsonParser;

            private TargetDeserializer(MultiTypeJsonParser<T> tGeneralJsonParser) {
                this.mGeneralJsonParser = tGeneralJsonParser;
            }

            @Override
            public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                Gson gson = mGeneralJsonParser.parseGson;
                JsonObject jsonObject = (JsonObject) json;
                JsonElement jsonElement = jsonObject.get(mGeneralJsonParser.typeElementName);
                T item;
                String contentType;
                if (jsonElement != null) {
                    //jsonObject已经包含了"type":""的形式
                    contentType = getString(jsonElement);
                } else {
                    //未包含type，那么就用上一层级的type对应的value
                    contentType = mGeneralJsonParser.getTypeElementValue();
                }

                // 未注册的类型直接返回null
                if (!checkHasRegistered(contentType)) {
                    return null;
                }

                item = gson.fromJson(json, mGeneralJsonParser.typeClassMap.get(contentType));
                onTargetItemDeserialize(item, contentType);
                return item;
            }

        }

        /**
         * 当目标类解析之后供子类调用
         *
         * @param item
         * @param typeElementValue
         */
        protected void onTargetItemDeserialize(T item, String typeElementValue) {

        }

        /**
         * 当目标类外层的类解析之后供子类调用
         *
         * @param item
         * @param typeElementValue
         */
        protected void onTargetUpperItemDeserialize(Object item, String typeElementValue) {

        }

        private class TargetUpperLevelDeserializer implements JsonDeserializer<Object> {

            private final MultiTypeJsonParser multiTypeJsonParser;

            private TargetUpperLevelDeserializer(MultiTypeJsonParser multiTypeJsonParser) {
                this.multiTypeJsonParser = multiTypeJsonParser;
            }

            @Override
            public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                //新建一个Gson,不再对AdaptedUpperLevelClass进行注册
                Gson gson = multiTypeJsonParser.getTargetParseGson();
                JsonObject jsonObject = (JsonObject) json;
                String typeValue = null;
                if (jsonObject.has(multiTypeJsonParser.typeElementName)) {
                    //如果包含type字段，就把对应的value传递给下一层级
                    typeValue = getString(jsonObject.get(multiTypeJsonParser.typeElementName));
                    multiTypeJsonParser.setTypeElementValue(typeValue);
                }

                // 未注册的类型直接返回null
                if (!checkHasRegistered(typeValue)) {
                    return null;
                }
                Object item;
                item = gson.fromJson(json, multiTypeJsonParser.targetUpperLevelClass);
                onTargetUpperItemDeserialize(item, typeValue);
                return item;
            }
        }

    }
}

