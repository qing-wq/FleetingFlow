FROM bitnami/pytorch:1.13.1-debian-11-r29
USER 0
WORKDIR /app
COPY . /app
EXPOSE 7670
RUN     sed -i 's/deb.debian.org/mirrors.bfsu.edu.cn/g' /etc/apt/sources.list \
&&      sed -i 's/security.debian.org/mirrors.bfsu.edu.cn\/debian-security/g' /etc/apt/sources.list \
&&      apt update && apt install wget gcc supervisor -y \
&&      pip config set global.index-url https://mirrors.bfsu.edu.cn/pypi/web/simple \
&&      pip install pytorch_pretrained_bert jieba gensim gradio \
&&      cd /app/recommender-system && pip install -r requirements.txt \
&&      rm -rf /var/lib/apt/lists/* \
&&      wget http://s3anmft1h.hn-bkt.clouddn.com/machine-learning/bert.ckpt -O /app/title-classification/THUCNews/saved_dict/bert.ckpt \
&&      wget http://s3anmft1h.hn-bkt.clouddn.com/machine-learning/pytorch_model.bin -O /app/title-classification/bert_pretrain/pytorch_model.bin

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

CMD ["/usr/bin/supervisord"]

# WORKDIR /app/title-classification
# ENTRYPOINT /opt/bitnami/python/bin/python /app/title-classification/predict.py

# WORKDIR /app/recommender-system
# ENTRYPOINT /opt/bitnami/python/bin/python /app/recommender-system/rs_main.py