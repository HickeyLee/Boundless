import os
import pandas as pd
import random
import time

relative_path = os.getcwd().replace("\\", "/")
product_info_path = "/genbuyinfo2kafka/conf/product_info.csv"
user_info_path = "/genbuyinfo2kafka/conf/user_info.csv"

product_df = pd.read_csv(relative_path + product_info_path, sep="," , encoding = "gbk")
user_df = pd.read_table(relative_path + user_info_path, sep=",")
print(user_df.loc[1, "user_name"])

product_rg = product_df.shape[0]
user_rg = user_df.shape[0]

for i in range(2000):
    pd_inx = random.randint(1, product_rg)
    user_idx = random.randint(1, user_rg)
    
    try:
        forecast_data = {
            "log_id": i,
            "order_datetime": time.time(),
            
            "user_id": user_df.loc[user_idx, "user_id"],
            "user_name": user_df.loc[user_idx, "user_name"],
            "user_location": user_df.loc[user_idx, "user_location"],
            "sex": user_df.loc[user_idx, "sex"],
            "age": user_df.loc[user_idx, "age"],
            
            "book_id": product_df.loc[pd_inx, "book_id"],
            "book_name": product_df.loc[pd_inx, "book_name"],
            "publish_dt": product_df.loc[pd_inx, "publish_dt"],
            "mk_price": product_df.loc[pd_inx, "mk_price"],
            "category": product_df.loc[pd_inx, "category"],
            
            "order_amount": random.randint(1,4),
            "order_price":random.randint(1,4)*int(product_df.loc[pd_inx, "mk_price"])
        }
        
        print(forecast_data)
    except Exception as e:
        pass

