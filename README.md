FleetingFlow
============

<div align="center">
    <img src="https://github.com/user-attachments/assets/815942d3-e513-4b73-9ebd-ad38e3add1e6" width="40%" />

![GitHub Last Commit]
[![Apache License][Apache License Badge]][Apache License URL]

</div>

—— Web 短视频项目（后端）

队伍：`不想起名`

前端仓库：[https://github.com/LunaSekiii/qiniu-shortvideo](https://github.com/LunaSekiii/qiniu-shortvideo)

演示Demo：[https://github.com/qing-wq/FleetingFlow/blob/master/imgs/FleetingFlowDemo.mp4](https://github.com/qing-wq/FleetingFlow/blob/master/imgs/FleetingFlowDemo.mp4)

备用链接: https://pan.baidu.com/s/1-WBUI5S3ctbFOYWPo2WuVg?pwd=w4h3 提取码: w4h3

项目文档：https://github.com/qing-wq/FleetingFlow/blob/master/doc.pdf

环境要求
-------

> 环境要求：由于`FleetingFlow-backend`使用分布式架构、ElasticSearch搜索引擎等，并且部署了基于`Transformer`的深度学习模型`BERT`，所以内存空间占用较多。
  - 至少需要41G内存空间
  - 至少需要6.5G磁盘空间

项目架构图
-------

<img width="1497" alt="59783EEC088A18CA7530C7AC68426316" src="https://github.com/user-attachments/assets/ec91add0-87c4-47b8-b24b-5125490c88db">

## 页面展示

![Qiniu Shortvideo Frontend](https://github.com/user-attachments/assets/c76d2e6a-23ac-4ba4-813e-5a53beea6659)

![Qiniu Shortvideo Frontend (1)](https://github.com/user-attachments/assets/cb873f3f-89ab-4554-b121-b967b7c1f28b)

![Qiniu Shortvideo Frontend (2)](https://github.com/user-attachments/assets/ff634135-f129-408b-8220-6ac8d9175fab)


项目部署
-------

### Docker部署

`FleetingFlow-backend`使用docker封装，项目启动：

```Bash
# 使用Maven构建jar，并启动容器
bash run.sh

# 接下来导入ElasticSearch数据
bash es_init.sh

```

### 源代码部署

使用Maven读取pom文件即可，可分别运行各种微服务。

#### 部署现有 SQL 数据

运行` ./backend/document/data.sql`，即可导入现有的数据，能够正常实现视频的播放等功能。

AI应用部署
---------

#### Docker部署

```Bash
cd /path/to/FleetingFlow/machine-learning

# 通过 Dockerfile 构建镜像
docker build -t fleetingflow_ml:latest .

# 运行构建好的镜像，会自动使用 Supervisor 运行 3 个 AI 应用（标题分类、）
docker run -p 7670:7670 -p 7671:7671 -p 7672:7672 fleetingflow_ml

```

#### 源代码部署

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

API文档
------

地址：[https://github.com/qing-wq/FleetingFlow/blob/master/api.md](https://github.com/qing-wq/FleetingFlow/blob/master/api.md)

测试
---

测试帐号：admin

测试密码：123456


[GitHub Last Commit]: https://img.shields.io/github/last-commit/qing-wq/FleetingFlow/master?logo=github&style=for-the-badge
[Apache License Badge]: https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white
[Apache License URL]: https://www.apache.org/licenses/LICENSE-2.0
