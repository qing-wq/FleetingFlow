import gradio as gr


from rs_backend import *

import random

sample_size=10
p_time = time.time()

train_data3, test_data3 = generate_fake_datas()
model, user_id_mapping, movie_id_mapping, movie_id_backup = lightfm_library(train_data3, test_data3)

def main(user_id, category_id):
    global p_time, train_data3, test_data3, model, user_id_mapping, movie_id_mapping, movie_id_backup
    try:

        if time.time() - p_time > 60 or category_id != -1:
            train_data3, test_data3 = generate_fake_datas(category_id)
            model, user_id_mapping, movie_id_mapping, movie_id_backup = lightfm_library(train_data3, test_data3)
            p_time = time.time()

        user_id_maped = user_id_mapping[user_id]

        all_movies = np.arange(len(movie_id_mapping))

        pred_rst = model.predict(user_id_maped, all_movies)

        pred_rst = movie_id_backup[np.argsort(-pred_rst)]

        rst_str = ''
        for i in range(sample_size):
            rst_str += str(pred_rst[i]) + ','

        return rst_str[:-1]

    except:
        rst_str = ''
        # 确保不会超出范围
        if not len(list(movie_id_backup)) > sample_size:
            sample_size = len(list(movie_id_backup))
        for rand in random.sample(list(movie_id_backup), sample_size):
            rst_str += str(rand) + ','
        
        return rst_str[:-1]


if __name__ == '__main__':
    # inputs = gradio.inputs.Number(default=-1, label="User ID")
    # outputs = gradio.outputs.Textbox()
    gr.Interface(
        fn=main, inputs=['number', 'number'], outputs='text'
        ).launch(server_name='0.0.0.0', share=True, server_port=7676)