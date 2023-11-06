FROM bitnami/pytorch:1.13.1-debian-11-r29
USER 0
WORKDIR /app
COPY . /app
EXPOSE 7670
RUN     sed -i 's/deb.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list \
&&      sed -i 's/security.debian.org/mirrors.ustc.edu.cn\/debian-security/g' /etc/apt/sources.list \
&&      apt update && apt install wget -y \
&&      pip config set global.index-url https://mirrors.bfsu.edu.cn/pypi/web/simple \
&&      pip install pytorch_pretrained_bert gradio \
&&      wget http://s3anmft1h.hn-bkt.clouddn.com/machine-learning/bert.ckpt -O /app/title-classification/THUCNews/saved_dict/bert.ckpt \
&&      wget http://s3anmft1h.hn-bkt.clouddn.com/machine-learning/pytorch_model.bin -O /app/title-classification/bert_pretrain/pytorch_model.bin

WORKDIR /app/title-classification
ENTRYPOINT /opt/bitnami/python/bin/python /app/title-classification/predict.py