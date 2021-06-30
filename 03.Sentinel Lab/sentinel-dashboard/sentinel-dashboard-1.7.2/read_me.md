官网

https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0



如何启动

```java
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.2.jar
```



注意：

```properties
控制台=http://127.0.0.1:8080
用户名/密码=sentinel/sentinel
默认api-port=csp.sentinel.api.port=8719
```



注意：

启动 1min，控制台才会收集并展示数据
