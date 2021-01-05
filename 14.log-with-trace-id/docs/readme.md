关键信息

- 大家都是用MDC来做的，并且MDC是基于threadlocal的
- 阿里巴巴的transmittable-thread-local使得threadlocal可以在父子线程中传输
- 虽然transmittable-thread-local可以在多线程中使用，但是在feigncliet等spring tools调用时，会丢失tracker-id，建议使用springbean来维护相关线程，并且在多线程模式下手动传输 tracer.withSpan(this.span);

|             |                                                              |
| ----------- | ------------------------------------------------------------ |
| logback配置 | 详见logback-spring.xml                                       |
| mdc判断     | boolean isAvailable = Class.forName("org.slf4j.MDC") != null;可以进行日志抓取 |
|             |                                                              |



参考文档：

https://github.com/alibaba/transmittable-thread-local

https://www.jianshu.com/p/cbf19812c166

https://www.jianshu.com/p/1706e0c54bb6

https://www.jianshu.com/p/0436d3a4c673