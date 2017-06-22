package com.sososeen09.parsersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.sososeen09.multitypejsonparser.parse.ListItemFilter;
import com.sososeen09.multitypejsonparser.parse.MultiTypeJsonParser;
import com.sososeen09.parsersample.bean.AddressAttribute;
import com.sososeen09.parsersample.bean.Attribute;
import com.sososeen09.parsersample.bean.AttributeWithType;
import com.sososeen09.parsersample.bean.generic.BaseListInfo;
import com.sososeen09.parsersample.bean.generic.BaseUpperBean;
import com.sososeen09.parsersample.bean.ListInfoNoType;
import com.sososeen09.parsersample.bean.ListInfoWithType;
import com.sososeen09.parsersample.bean.NameAttribute;

import java.lang.reflect.Type;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void parseAttribute1(View view) {
        MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
                .registerTypeElementName("type")
                .registerTargetClass(Attribute.class)
                .registerTargetUpperLevelClass(AttributeWithType.class)
                .registerTypeElementValueWithClassType("address", AddressAttribute.class)
                .registerTypeElementValueWithClassType("name", NameAttribute.class)
                .build();

        ListInfoWithType listInfoWithType = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_1, ListInfoWithType.class);
        Log.d(TAG, "parseAttribute1: " + listInfoWithType);
    }

    public void parseAttribute2(View view) {
        MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
                .registerTypeElementName("type")
                .registerTargetClass(Attribute.class)
                .registerTypeElementValueWithClassType("address", AddressAttribute.class)
                .registerTypeElementValueWithClassType("name", NameAttribute.class)
                .build();

        ListInfoNoType listInfoNoType = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_2, ListInfoNoType.class);
        Log.d(TAG, "parseAttribute2: " + listInfoNoType);
    }

    /**
     * 包含未知类型
     *
     * @param view
     */
    public void parseWithUnknownType1(View view) {
        MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
                .registerTypeElementName("type")
                .registerTargetClass(Attribute.class)
                .registerTargetUpperLevelClass(AttributeWithType.class)
                .registerTypeElementValueWithClassType("address", AddressAttribute.class)
                .registerTypeElementValueWithClassType("name", NameAttribute.class)
                .build();

        ListInfoWithType listInfoWithType = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_WITH_UNKNOWN_TYPE1, ListInfoWithType.class);

        //如果含有未知的type类型，解析的集合中包含null，过滤一下
        listInfoWithType.setList(ListItemFilter.filterNullElement(listInfoWithType.getList()));
        Log.d(TAG, "parseWithUnknownType listSize: " + listInfoWithType.getList().size());
    }

    /**
     * 包含未知类型
     *
     * @param view
     */
    public void parseWithUnknownType2(View view) {
        MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
                .registerTypeElementName("type")
                .registerTargetClass(Attribute.class)
                .registerTypeElementValueWithClassType("address", AddressAttribute.class)
                .registerTypeElementValueWithClassType("name", NameAttribute.class)
                .build();

        ListInfoNoType listInfoNoType = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_WITH_UNKNOWN_TYPE2, ListInfoNoType.class);
        listInfoNoType.setList(ListItemFilter.filterNullElement(listInfoNoType.getList()));
        Log.d(TAG, "parseWithUnknownType2: " + listInfoNoType);
    }

    public void parseWithGeneric(View view) {
        Type upperClass = new TypeToken<BaseUpperBean<Attribute>>() {
        }.getType();
        MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
                .registerTypeElementName("type")
                .registerTargetClass(Attribute.class)
                .registerTargetUpperLevelClass(upperClass)
                .registerTypeElementValueWithClassType("address", AddressAttribute.class)
                .registerTypeElementValueWithClassType("name", NameAttribute.class)
                .build();

        Type listInfoType = new TypeToken<BaseListInfo<BaseUpperBean<Attribute>>>() {
        }.getType();
        BaseListInfo<BaseUpperBean<Attribute>> listInfo = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_1, listInfoType);
        Log.d(TAG, "parseWithGeneric: " + listInfo);
    }
}
