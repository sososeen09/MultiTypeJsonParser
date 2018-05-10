package com.sososeen09.sample;

/**
 * Created by yunlong.su on 2017/6/15.
 */

public class TestJson {
    public static final String TEST_JSON_1 = "{\n" +
            "  \"total\": 2,\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"type\": \"address\",\n" +
            "      \"attributes\": {\n" +
            "        \"street\": \"NanJing Road\",\n" +
            "        \"city\": \"ShangHai\",\n" +
            "        \"country\": \"China\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"name\",\n" +
            "      \"attributes\": {\n" +
            "        \"first-name\": \"Su\",\n" +
            "        \"last-name\": \"Tu\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String TEST_JSON_2 = "{\n" +
            "  \"total\": 2,\n" +
            "  \"list\": [\n" +
            "    {\n" +
            "      \"type\": \"address\",\n" +
            "      \"street\": \"NanJing Road\",\n" +
            "      \"city\": \"ShangHai\",\n" +
            "      \"country\": \"China\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"type\": \"name\",\n" +
            "      \"first-name\": \"Su\",\n" +
            "      \"last-name\": \"Tu\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String TEST_JSON_WITH_UNKNOWN_TYPE1 = "{\n" +
            "    \"total\": 3,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"type\": \"parents\",\n" +
            "        \"attributes\": {\n" +
            "          \"mather\": \"mi lan\",\n" +
            "          \"father\": \"lin ken\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"address\",\n" +
            "        \"attributes\": {\n" +
            "          \"street\": \"NanJing Road\",\n" +
            "          \"city\": \"ShangHai\",\n" +
            "          \"country\": \"China\"\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"name\",\n" +
            "        \"attributes\": {\n" +
            "          \"first-name\": \"Su\",\n" +
            "          \"last-name\": \"Tu\"\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }";


    public static final String TEST_JSON_WITH_UNKNOWN_TYPE2 = "{\n" +
            "    \"total\": 3,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"type\": \"parents\",\n" +
            "        \"mather\": \"mi lan\",\n" +
            "        \"father\": \"lin ken\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"address\",\n" +
            "        \"street\": \"NanJing Road\",\n" +
            "        \"city\": \"ShangHai\",\n" +
            "        \"country\": \"China\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"name\",\n" +
            "        \"first-name\": \"Su\",\n" +
            "        \"last-name\": \"Tu\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }";
}
