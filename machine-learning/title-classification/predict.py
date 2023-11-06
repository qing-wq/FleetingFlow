import os

import torch
import torch.nn as nn
from pytorch_pretrained_bert import BertModel, BertTokenizer
import gradio

# 识别的类型
key_path = './THUCNews/data/class.txt'
key = {}

with open(key_path, mode='r') as f:
    for i, key_name in enumerate([x.strip() for x in f.readlines()]):
        key[i] = key_name

print(key)


class Config:
    """配置参数"""

    def __init__(self):
        cru = os.path.dirname(__file__)
        self.class_list = [str(i) for i in range(len(key))]  # 类别名单
        
        self.save_path = './THUCNews/saved_dict/bert.ckpt'
        self.device = torch.device('cpu')
        self.require_improvement = 1000  # 若超过1000batch效果还没提升，则提前结束训练
        self.num_classes = len(self.class_list)  # 类别数
        self.num_epochs = 3  # epoch数
        self.batch_size = 24  # mini-batch大小
        self.pad_size = 32  # 每句话处理成的长度(短填长切)
        self.learning_rate = 5e-5  # 学习率
        self.bert_path = './bert_pretrain'
        self.tokenizer = BertTokenizer.from_pretrained(self.bert_path)
        self.hidden_size = 768

    def build_dataset(self, text):
        lin = text.strip()
        pad_size = len(lin)
        token = self.tokenizer.tokenize(lin)
        token = ['[CLS]'] + token
        token_ids = self.tokenizer.convert_tokens_to_ids(token)
        mask=[]
        if len(token) < pad_size:
            mask = [1] * len(token_ids) + [0] * (pad_size - len(token))
            token_ids += ([0] * (pad_size - len(token)))
        else:
            mask = [1] * pad_size
            token_ids = token_ids[:pad_size]
        return torch.tensor([token_ids], dtype=torch.long), torch.tensor([mask])


class Model(nn.Module):

    def __init__(self, config):
        super(Model, self).__init__()
        self.bert = BertModel.from_pretrained(config.bert_path)
        for param in self.bert.parameters():
            param.requires_grad = True
        self.fc = nn.Linear(config.hidden_size, config.num_classes)

    def forward(self, x):
        context = x[0]
        mask = x[1]
        _, pooled = self.bert(context, attention_mask=mask, output_all_encoded_layers=False)
        out = self.fc(pooled)
        return out


config = Config()
model = Model(config).to(config.device)
model.load_state_dict(torch.load(config.save_path, map_location='cpu'))


def prediction_model(text):
    """输入一句问话预测"""
    data = config.build_dataset(text)
    with torch.no_grad():
        outputs = model(data)
        num = torch.argmax(outputs)
    return key[int(num)]


def pred_gradio(inp):
    return prediction_model(f'{inp}')

if __name__ == '__main__':
    gradio.Interface(
        fn=prediction_model, inputs=["text"], outputs=["text"]
        ).launch(server_name='0.0.0.0', share=True, server_port=7670)
    