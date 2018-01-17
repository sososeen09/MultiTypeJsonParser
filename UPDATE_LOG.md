### 版本 0.1.3 ——2017.12.28

添加方法forceUseUpperTypeValue，强制使用目标类上一层级的type。
解决因目标type和上一层级的type不同导致的解析错误

```
public Builder<T> forceUseUpperTypeValue(boolean force) {
    multiTypeJsonParser.forceUseUpperTypeValue = force;
    return this;
}

```

### 版本0.1.2——2017.12.26
添加方法用来获取注册的type个数

```
public int getSupportTypeSize() {
    return typeClassMap.size();
}
```

### 版本0.1.1——2017.12.26
添加方法用来获取Gson对象
```

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

```