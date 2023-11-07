# FleetingFlow
—— Web 短视频项目（后端）

队伍：`不想起名`

前端仓库：[https://github.com/LunaSekiii/qiniu-shortvideo](https://github.com/LunaSekiii/qiniu-shortvideo)

演示Demo：[https://github.com/qing-wq/FleetingFlow/blob/master/imgs/FleetingFlowDemo.mp4](https://github.com/qing-wq/FleetingFlow/blob/master/imgs/FleetingFlowDemo.mp4)

# 环境要求

> 环境要求：由于`FleetingFlow-backend`使用分布式架构、ElasticSearch搜索引擎等，并且部署了基于`Transformer`的深度学习模型`BERT`，所以内存空间占用较多。
  - 至少需要41G内存空间
  - 至少需要6.5G磁盘空间

![](https://camo.githubusercontent.com/be02fa9c441bee2a4b2e6f8c2553a2ccbd19cbdf2f792fd4dc313b6dd8383b0c/68747470733a2f2f73322e6c6f6c692e6e65742f323032332f31312f30372f586e57345631656c4459744a794c322e706e67)

![](https://camo.githubusercontent.com/a3c3f7a63b1f30c4c30809bf06066a25393b7c5df61cb13e9a40a55ffd7ade2d/68747470733a2f2f73322e6c6f6c692e6e65742f323032332f31312f30372f36545833507853516659574335756c2e706e67)

![](https://camo.githubusercontent.com/8b752fe0528dabb8e5cb05159953c83a7c4de34bcd63c5d6c81cbc6c593a43b0/68747470733a2f2f73322e6c6f6c692e6e65742f323032332f31312f30372f435047693544674666744a415242622e706e67)

# 项目部署

## Docker部署

`FleetingFlow-backend`使用docker封装，项目启动：

```Bash
# 使用Maven构建jar，并启动容器
bash run.sh

# 接下来导入ElasticSearch数据
bash es_init.sh

```

## 源代码部署

使用Maven读取pom文件即可，可分别运行各种微服务。

### 部署现有 SQL 数据

运行` ./backend/document/data.sql`，即可导入现有的数据，能够正常实现视频的播放等功能。

## AI应用部署

### Docker部署

```Bash
cd /path/to/FleetingFlow/machine-learning

# 通过 Dockerfile 构建镜像
docker build -t fleetingflow_ml:latest .

# 运行构建好的镜像，会自动使用 Supervisor 运行 3 个 AI 应用（标题分类、）
docker run -p 7670:7670 -p 7671:7671 -p 7672:7672 fleetingflow_ml

```

### 源代码部署

首先安装依赖：

```Bash
pip install pytorch_pretrained_bert jieba gensim gradio
cd ./recommender-system
pip install -r requirements.txt
cd .. 
wget http://s3anmft1h.hn-bkt.clouddn.com/machine-learning/bert.ckpt -O ./title-classification/THUCNews/saved_dict/bert.ckpt
wget http://s3anmft1h.hn-bkt.clouddn.com/machine-learning/pytorch_model.bin -O ./title-classification/bert_pretrain/pytorch_model.bin

```

之后运行即可：

```Bash
# 推荐系统
cd ./recommender-system
python rs_main.py

```

```Bash
# Tags 推荐
cd tags-recommender
python main.py
```

```Bash
# 标题分类
cd ./title-classification
python predict.py
```

# API文档

地址：[https://github.com/qing-wq/FleetingFlow/blob/master/api.md](https://github.com/qing-wq/FleetingFlow/blob/master/api.md)

# 测试

测试帐号：admin

测试密码：123456

---
