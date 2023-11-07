# 安装maven
if ! which mvn > /dev/null 2>&1; then
    sudo apt update
    sudo apt install maven
fi

echo "正在打包成 JAR...，请耐心等待"

bash -c "cd backend && mvn package"

# ------------------------------------

echo "正在启动 FleetingFlow Docker，因微服务架构，镜像较多，请耐心等待..."
docker-compose up -d

echo "第一次使用，请运行 es_init.sh，导入 ElasticSearch 的数据！"