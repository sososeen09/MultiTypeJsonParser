package com.sososeen09.sample.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sososeen09.multitypejsonparser.parse.MultiTypeJsonParser;
import com.sososeen09.sample.bean.Address;
import com.sososeen09.sample.bean.Attribute;
import com.sososeen09.sample.bean.AttributeWithType;
import com.sososeen09.sample.bean.ListInfoWithType;
import com.sososeen09.sample.bean.Name;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created on 2018/5/10.
 *
 * @author sososeen09
 */

class AttributeListConverterFactory extends Converter.Factory {
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == ListInfoWithType.class) {
            return new MultiTypeJsonResponseBodyConverter();
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    static Converter.Factory create() {
        return new AttributeListConverterFactory();
    }


    class MultiTypeJsonResponseBodyConverter implements Converter<ResponseBody, ListInfoWithType> {
        Gson gson = createGson();

        private Gson createGson() {
            MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
                    .registerTypeElementName("type")
                    .registerTargetClass(Attribute.class)
                    .registerTargetUpperLevelClass(AttributeWithType.class)
                    .registerTypeElementValueWithClassType("address", Address.class)
                    .registerTypeElementValueWithClassType("name", Name.class)
                    .build();

            return multiTypeJsonParser.getParseGson();
        }

        TypeAdapter<ListInfoWithType> adapter = gson.getAdapter(TypeToken.get(ListInfoWithType.class));

        @Override
        public ListInfoWithType convert(@NonNull ResponseBody value) throws IOException {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());

            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }

        }
    }

}
