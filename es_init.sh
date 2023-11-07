echo "Pulling taskrabbit/elasticsearch-dump for input..."

docker pull taskrabbit/elasticsearch-dump

docker run --rm -ti --network fleetingflow_fleetingflow -v ./.es-datas:/tmp taskrabbit/elasticsearch-dump --input=/tmp/qiniu_map.json --output=http://elasticsearch:9200/video --type=mapping

docker run --rm -ti --network fleetingflow_fleetingflow -v ./.es-datas:/tmp taskrabbit/elasticsearch-dump --input=/tmp/qiniu.json --output=http://elasticsearch:9200/video --type=data

docker run --rm -ti --network fleetingflow_fleetingflow -v ./.es-datas:/tmp taskrabbit/elasticsearch-dump --input=/tmp/analyzer.json --output=http://elasticsearch:9200/video --type=analyzer

echo "导入完毕！"