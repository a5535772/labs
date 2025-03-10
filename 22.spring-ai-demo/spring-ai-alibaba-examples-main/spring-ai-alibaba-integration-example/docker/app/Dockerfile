# 使用指定的基础镜像
FROM registry.cn-hangzhou.aliyuncs.com/yuluo-yx/java-base:V20240116

# 设置工作目录
WORKDIR /app

# 将当前目录下的 backend.jar 复制到镜像中的 /app 目录
COPY ./docker/app/app.jar /app/app.jar

# 设置容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=docker"]
