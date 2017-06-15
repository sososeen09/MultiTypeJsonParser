package com.sososeen09.multitypejsonparser;

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
    private String dataElementName;
    private Class<?> adaptedClass;
    private Class<?> adaptedUpperLevelClass;
    private Gson gson;
    private Gson pureGson;
    private Builder<T> mBuilder;

    public Gson getPureGson() {
        if (pureGson == null) {
            pureGson = new Gson();
        }
        return pureGson;
    }

    private MultiTypeJsonParser() {
    }

    public <V> V fromJson(String json, Class<V> jsonFeedsClass) {
        return gson.fromJson(json, jsonFeedsClass);
    }

    public <V> V fromJson(String json, Type jsonFeedsClass) {
        return gson.fromJson(json, jsonFeedsClass);
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

    private Gson getAdaptedTargetGson() {
        return new GsonBuilder().registerTypeAdapter(adaptedClass, this.getBuilder().getTypeAdapter()).create();
    }


    public static class Builder<T> {
        private MultiTypeJsonParser<T> mMultiTypeJsonParser;
        private TargetDeserializer mTypeAdapter;

        public Builder() {
            mMultiTypeJsonParser = new MultiTypeJsonParser<>();
            mMultiTypeJsonParser.mBuilder = this;
        }

        /**
         * 注册能够表示type类型的字段名
         *
         * @param typeElementName
         * @return
         */
        public Builder<T> registerTypeElementName(String typeElementName) {
            mMultiTypeJsonParser.typeElementName = typeElementName;
            return this;
        }

        /**
         * 注册能够表示需要解析的JsonObject的字段名
         *
         * @param dataElementName
         * @return
         */
        public Builder<T> registerDataElementName(String dataElementName) {
            mMultiTypeJsonParser.dataElementName = dataElementName;
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
            mMultiTypeJsonParser.typeClassMap.put(typeElementValue, classValue);
            return this;
        }

        /**
         * 注册需要需要转换的类，通常这个类是一个父类
         *
         * @param adaptedClass
         * @return
         */
        public Builder<T> registerTargetClass(Class<T> adaptedClass) {
            mMultiTypeJsonParser.adaptedClass = adaptedClass;
            return this;
        }

        /**
         * 注册包裹在被转换的类的上一层的类
         *
         * @param adaptedUpperLevelClass
         * @return
         */
        public Builder<T> registerTargetUpperLevelClass(Class<?> adaptedUpperLevelClass) {
            mMultiTypeJsonParser.adaptedUpperLevelClass = adaptedUpperLevelClass;
            return this;
        }

        public MultiTypeJsonParser<T> build() {
            mTypeAdapter = new TargetDeserializer(mMultiTypeJsonParser);
            GsonBuilder gsonBuilder = new GsonBuilder();

            if (mMultiTypeJsonParser.adaptedClass == null) {
                throw new IllegalStateException("adaptedClass can not be Null: ");
            }

            gsonBuilder.registerTypeAdapter(mMultiTypeJsonParser.adaptedClass, mTypeAdapter);

            if (mMultiTypeJsonParser.adaptedUpperLevelClass != null) {
                gsonBuilder.registerTypeAdapter(mMultiTypeJsonParser.adaptedUpperLevelClass, new TargetUpperLevelDeserializer(mMultiTypeJsonParser));
            }

            mMultiTypeJsonParser.gson = gsonBuilder.create();
            return mMultiTypeJsonParser;
        }

        private TargetDeserializer getTypeAdapter() {
            return mTypeAdapter;
        }

        private String getString(JsonElement jsonElement) {
            return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
        }

        private class TargetDeserializer implements JsonDeserializer<T> {

            private final MultiTypeJsonParser<T> mGeneralJsonParser;

            private TargetDeserializer(MultiTypeJsonParser<T> tGeneralJsonParser) {
                this.mGeneralJsonParser = tGeneralJsonParser;
            }

            @Override
            public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                Gson gson = mGeneralJsonParser.gson;
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
                item = gson.fromJson(json, mGeneralJsonParser.typeClassMap.get(contentType));
                onTargetItemDeserialize(item, contentType);
                return item;
            }

        }

        protected void onTargetItemDeserialize(T item, String typeElementValue) {

        }

        protected void onTargetUpperItemDeserialize(Object item, String typeElementValue) {

        }

        private class TargetUpperLevelDeserializer implements JsonDeserializer<Object> {

            private final MultiTypeJsonParser mMultiTypeJsonParser;

            private TargetUpperLevelDeserializer(MultiTypeJsonParser multiTypeJsonParser) {
                this.mMultiTypeJsonParser = multiTypeJsonParser;
            }

            @Override
            public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                //新建一个Gson,不再对AdaptedUpperLevelClass进行注册
                Gson gson = mMultiTypeJsonParser.getAdaptedTargetGson();
                JsonObject jsonObject = (JsonObject) json;
                String typeValue = null;
                if (jsonObject.has(mMultiTypeJsonParser.typeElementName)) {
                    //如果包含type字段，就把对应的value传递给下一层级
                    typeValue = getString(jsonObject.get(mMultiTypeJsonParser.typeElementName));
                    mMultiTypeJsonParser.setTypeElementValue(typeValue);
                }
                Object item;
                item = gson.fromJson(json, mMultiTypeJsonParser.adaptedUpperLevelClass);
                onTargetUpperItemDeserialize(item, typeValue);
                return item;
            }


        }

    }
}

