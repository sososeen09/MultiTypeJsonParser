[![](https://jitpack.io/v/sososeen09/MultiTypeJsonParser.svg)](https://jitpack.io/#sososeen09/MultiTypeJsonParser)
### 如何使用


1.在项目root目录下的 build.gradle 添加:

```
allprojects {
    repositories {
    	...
    	maven { url 'https://jitpack.io' }
    }
}
```

2. 添加依赖

```
dependencies {
    implementation 'com.github.sososeen09:MultiTypeJsonParser:0.1.3'
}
```

#### 功能

不管是这样，

```
{
    "total": 2,
    "list": [
        {
            "type": "address",
            "attributes": {
                "street": "NanJing Road",
                "city": "ShangHai",
                "country": "China"
            }
        },
        {
            "type": "name",
            "attributes": {
                "first-name": "Su",
                "last-name": "Tu"
            }
        }
    ]
}

```

还是这样，

```
{
    "total": 2,
    "list": [
        {
            "type": "address",
            "street": "NanJing Road",
            "city": "ShangHai",
            "country": "China"
        },
        {
            "type": "name",
            "first-name": "Su",
            "last-name": "Tu"
        }
    ]
}
```

只需几行代码，就可以解析，方便快捷。

```
MultiTypeJsonParser<Attribute> multiTypeJsonParser = new MultiTypeJsonParser.Builder<Attribute>()
        .registerTypeElementName("type")
        .registerTargetClass(Attribute.class)
        .registerTargetUpperLevelClass(AttributeWithType.class)
        .registerTypeElementValueWithClassType("address", Address.class)
        .registerTypeElementValueWithClassType("name", Name.class)
        .build();

ListInfoWithType listInfoWithType = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_1, ListInfoWithType.class);
```

> 示例项目中添加了新的示例，使用Retrofit进行网络请求，通过自定义Convert来使用MultiTypeJsonParser进行复杂Json的解析。
复杂列表推荐使用一位大神写的开源框架，[MultiType](https://github.com/drake.et/MultiType)https://github.com/drakeet/MultiType
MultiTypeJsonParser结合MultiType，绝对会使得你的开发效率大大提升！


[更新日志](/UPDATE_LOG.md)

#### 说明文档
[采用Gson解析含有多种JsonObject的复杂json](http://www.jianshu.com/p/185e1ee9f05b)

