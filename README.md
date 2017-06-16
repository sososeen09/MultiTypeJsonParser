# MultiTypeJsonParser

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

