import jieba
from gensim import corpora,models,similarities
import pandas as pd
import gradio as gr
import pymysql
import pandas as pd
import warnings
warnings.filterwarnings('ignore')

mysql_settings = {
    'host': 'mysql',
    'port': 3306,
    'user': 'root',
    'passwd': 'password',
    'db': 'qiniu'  # 指定您的数据库名称
}

def read_table_to_pandas(table_name):
    # 建立与数据库的连接
    connection = pymysql.connect(**mysql_settings)

    try:
        # 创建一个光标对象
        cursor = connection.cursor()

        # 构造SQL查询语句
        query = f'SELECT * FROM {table_name}'

        # 执行查询
        cursor.execute(query)

        # 从查询结果创建Pandas DataFrame
        df = pd.read_sql(query, connection)

    finally:
        # 关闭光标和数据库连接
        cursor.close()
        connection.close()

    return df



def get_tag_list():
    df = read_table_to_pandas('tag')
    df = df[df.deleted == 0]
    tag_name = df['tag_name'].tolist()
    return tag_name
    

def recommand_tag(query_str):
    word_list = get_tag_list()
    
    doc_list = []

    query_list = [word for word in jieba.cut(query_str)]

    for i in word_list:
        doc_list.append([word for word in jieba.cut(i)])
        
    dictionary = corpora.Dictionary(doc_list)
    # print(dictionary.keys())

    corpus = [dictionary.doc2bow(doc) for doc in doc_list]
    # print(corpus)

    doc_test_vec = dictionary.doc2bow(query_list)

    tfidf = models.TfidfModel(corpus)
    index = similarities.SparseMatrixSimilarity(tfidf[corpus], num_features=len(dictionary.keys()))
    sim = index[tfidf[doc_test_vec]]
    result = sorted(enumerate(sim), key=lambda item: -item[1])
    
    rst_str = ""
    for tup_tag in result[:15]:
        rst_str += word_list[tup_tag[0]] + '%%'
        
        
       

    # print(f'The most similar sentence is: {rst_list}')
    return rst_str[:-2]
    
if __name__ == '__main__':
    print(recommand_tag('小狗狗'))
    gr.close_all()
    # inputs1 = gr.inputs.Number(default=-1, label="User ID")
    # inputs2 = gr.inputs.Number(default=-1, label="Category ID")
    # outputs = gr.outputs.Textbox()
    gr.Interface(
        fn=recommand_tag, inputs='text', outputs='text'
        ).launch(server_name='0.0.0.0', share=True, server_port=7672)
    