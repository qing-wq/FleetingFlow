# To store the data
import random
import pandas as pd

# To do linear algebra
import numpy as np

# To create plots
import matplotlib.pyplot as plt

# # To create interactive plots
# from plotly.offline import init_notebook_mode, plot, iplot, download_plotlyjs
# import plotly as py
# import plotly.graph_objs as go
# # init_notebook_mode(connected=True)
# To operator files
import os
# To shift lists
from collections import deque

# To compute similarities between vectors
from sklearn.metrics import mean_squared_error
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder

# To create sparse matrices
from scipy.sparse import coo_matrix

# To light fm
from lightfm import LightFM
from lightfm.evaluation import precision_at_k

# To stack sparse matrices
from scipy.sparse import vstack


import pymysql
import pandas as pd
from pandasql import sqldf
import time

import warnings
warnings.filterwarnings('ignore')

mysql_settings = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'passwd': 'passwd',
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



def get_train_test(filtered_user_item, test_size=0.2):
    X_train, X_test, _, _ = train_test_split(filtered_user_item.reset_index(), filtered_user_item['movieId'].values, test_size=test_size, random_state=2020, stratify=filtered_user_item['movieId'].values)
    return X_train, X_test


def lightfm_library(train, test):
    # Create user- & movie-id mapping
    user_id_mapping = {id:i for i, id in enumerate(train['userId'].unique())}
    movie_id_mapping = {id:i for i, id in enumerate(train['movieId'].unique())}
    movie_id_backup = train['movieId'].unique()
    
    # Create correctly mapped train- & testset
    train_user_data = train['userId'].map(user_id_mapping)
    train_movie_data = train['movieId'].map(movie_id_mapping)

    test_user_data = test['userId'].map(user_id_mapping)
    test_movie_data = test['movieId'].map(movie_id_mapping)

    # Create sparse matrix from ratings
    shape = (len(user_id_mapping), len(movie_id_mapping))
    train_matrix = coo_matrix((train['rating'].values, (train_user_data.astype(int), train_movie_data.astype(int))), shape=shape)
    test_matrix = coo_matrix((test['rating'].values, (test_user_data.astype(int), test_movie_data.astype(int))), shape=shape)


    # Instantiate and train the model
    model = LightFM(loss='warp', no_components=20)
    model.fit(train_matrix, epochs=10, num_threads=4)
    
    return model, user_id_mapping, movie_id_mapping, movie_id_backup


def generate_fake_datas():
    
    video_df = read_table_to_pandas('video')
    print(video_df.columns)
    user_df = read_table_to_pandas('user')
    print(user_df.columns)
    score_df = read_table_to_pandas('score')

    video_ids = video_df['id'].unique()
    user_ids = user_df['id'].unique()
    
    fake_times = []

    # generate pandas timestamp
    for i in range(3000):
        fake_times.append(pd.Timestamp(np.random.randint(2021, 2023), np.random.randint(1, 12), np.random.randint(1, 28)))

    fake_rows = []

    # make fake data
    for i in range(2000):
        id = i + 1
        user_id = random.choice(user_ids)
        video_id = random.choice(video_ids)
        score = random.random() * 20 - 10
        
        fake_time = random.choice(fake_times)
        
        fake_rows.append([id, user_id, video_id, score, fake_time])
        
        user_id = random.choice(user_ids)
        score = random.random() * 20 - 10
        
        fake_time = random.choice(fake_times)
        
        fake_rows.append([id, user_id, video_id, score, fake_time])
        
    fake_df = pd.DataFrame(fake_rows, columns=['id', 'user_id', 'video_id', 'score', 'update_time'])

    # drop id column
    fake_df = fake_df.drop(columns=['id'])

    fake_df.columns = ['userId', 'movieId', 'rating', 'Date']
    
    train_data3, test_data3 = get_train_test(fake_df)
    
    return train_data3, test_data3