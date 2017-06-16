package com.sososeen09.multitypejsonparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sososeen09.multitypejsonparser.bean.AddressAttribute;
import com.sososeen09.multitypejsonparser.bean.Attribute;
import com.sososeen09.multitypejsonparser.bean.AttributeWithType;
import com.sososeen09.multitypejsonparser.bean.ListInfoNoType;
import com.sososeen09.multitypejsonparser.bean.ListInfoWithType;
import com.sososeen09.multitypejsonparser.bean.NameAttribute;

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
}
