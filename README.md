# 如何使用


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
             compile 'com.github.sososeen09:MultiTypeJsonParser:v0.0.1-SNAPSHOT'
}
```

# 功能

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
        .registerTypeElementValueWithClassType("address", AddressAttribute.class)
        .registerTypeElementValueWithClassType("name", NameAttribute.class)
        .build();

ListInfoWithType listInfoWithType = multiTypeJsonParser.fromJson(TestJson.TEST_JSON_1, ListInfoWithType.class);

```

# 说明文档
[采用Gson解析含有多种JsonObject的复杂json](http://www.jianshu.com/p/185e1ee9f05b)

