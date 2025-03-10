package com.leo.labs.springaidemo.mains;

import org.junit.jupiter.api.BeforeEach;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseLabTest {
    protected Map<String, String> yamlData;
    protected String api_key;
    protected String baseUrl;
    protected String completionsPath;


    @BeforeEach
    public void init() throws Exception {
        this.yamlData = loadYaml();
        this.api_key = yamlData.get("labs.openai.api-key");
        this.baseUrl = yamlData.get("labs.openai.baseUrl");
        this.completionsPath = yamlData.get("labs.openai.completionsPath");

    }

    private Map<String, String> loadYaml() {
        // 创建 Yaml 对象
        Yaml yaml = new Yaml();

        // 读取 YAML 文件
        InputStream inputStream = BaseLabTest.class.getResourceAsStream("/application.yaml");
        LinkedHashMap<String, Object> testCases = yaml.load(inputStream);
        // 递归遍历并扁平化输出
        return flattenMap(testCases);
    }

    private Map<String, String> flattenMap(LinkedHashMap<String, Object> map) {
        return this.flattenMap(map, "");
    }


    private Map<String, String> flattenMap(LinkedHashMap<String, Object> map, String parentKey) {
        // 用于存储扁平化结果的 Map
        Map<String, String> resultMap = new HashMap<>();

        map.forEach((key, value) -> {
            String fullKey = parentKey.isEmpty() ? key : parentKey + "." + key;
            if (value instanceof LinkedHashMap) {
                // 如果值是嵌套的 LinkedHashMap，递归处理并将结果合并到当前 Map
                resultMap.putAll(flattenMap((LinkedHashMap<String, Object>) value, fullKey));
            } else {
                // 将扁平化的键值对存储到 Map 中
                resultMap.put(fullKey, value.toString());
            }
        });
        // 返回存储了扁平化结果的 Map
        return resultMap;
    }


    @Override
    public String toString() {
        return "BaseLabTest{" +
                "yamlData=" + yamlData +
                ", api_key='" + api_key + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", completionsPath='" + completionsPath + '\'' +
                '}';
    }
}
