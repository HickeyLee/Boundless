import os
from unittest import result
import pandas as pd
import random
import time
from kafka import KafkaProducer
import json
import traceback


relative_path = os.getcwd().replace("\\", "/")
product_info_path = "/genbuyinfo2kafka/conf/product_info.csv"
user_info_path = "/genbuyinfo2kafka/conf/user_info.csv"

product_df = pd.read_csv(relative_path + product_info_path, sep="," , encoding = "gbk")
user_df = pd.read_table(relative_path + user_info_path, sep=",")
# print(user_df.loc[1, "user_name"])

product_rg = product_df.shape[0]
user_rg = user_df.shape[0]

# producer = KafkaProducer(bootstrap_servers=["127.0.0.1:9092"])

df = []
for i in range(20):
    pd_inx = random.randint(0, product_rg - 1)
    user_idx = random.randint(0, user_rg - 1)
    
    try:
        forecast_data = {
            "log_id": i,
            "order_datetime": time.time(),
            
            "user_id": user_df.loc[user_idx, "user_id"],
            "user_name": user_df.loc[user_idx, "user_name"],
            "user_location": user_df.loc[user_idx, "user_location"],
            "sex": user_df.loc[user_idx, "sex"],
            "age": int(user_df.loc[user_idx, "age"]),
            
            "book_id": product_df.loc[pd_inx, "book_id"],
            "book_name": product_df.loc[pd_inx, "book_name"],
            "publish_dt": product_df.loc[pd_inx, "publish_dt"],
            "mk_price": float(product_df.loc[pd_inx, "mk_price"]),
            "category": product_df.loc[pd_inx, "category"],
            
            "order_amount": random.randint(1,4),
            "order_price":random.randint(1,4)*float(product_df.loc[pd_inx, "mk_price"])
        }
        # print("forecast_data", forecast_data)
        df.append(forecast_data)
        # df = pd.DataFrame.from_dict(forecast_data)

        t = json.dumps(forecast_data )
        b_info_str = t.encode()
        # producer.send("easy-quickstart-events-1", b_info_str)
        
    except Exception as e:
        traceback.print_exc()
        pass
    
    finally:
        time.sleep(random.randint(1,5))



result = pd.DataFrame.from_dict(df)
result.to_csv("result.csv" ,index=False)




