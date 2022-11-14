
from ast import And
from distutils.log import ERROR, error
import requests
import time
import pandas as pd
from kafka import KafkaConsumer
from kafka import KafkaProducer
from kafka.errors import KafkaError
import json

url = "https://restapi.amap.com/v3/weather/weatherInfo"
key = "e9de18477afe93323ffe06a8b4bcf910"

# df = pd.read_table("E:/py_work/config/AMap_adcode_citycode.txt", sep=",")
df = pd.read_table("/root/bigdata/project/config/AMap_adcode_citycode.txt", sep=",")


city_list = list(df.iloc[:,1])
# producer = KafkaProducer(bootstrap_servers=["1.15.120.226:9092"])
producer = KafkaProducer(bootstrap_servers=["127.0.0.1:9092"])


# 24H中，6-23点执行，其余时间不请求信息；
while (int(time.strftime("%H"))>=6) & (int(time.strftime("%H"))<=24):
# while 1==1:

    for city in city_list:
        data = {"key": key, "city": city, "extensions": "all"}
        req = requests.post(url, data)
        info_dict = dict(req.json())
        # print("info_dict", info_dict)
                
        try:
            forecast_data = {
                "city": info_dict["forecasts"][0]["city"],
                "reporttime": info_dict["forecasts"][0]["reporttime"],
                "date": info_dict["forecasts"][0]["casts"][0]["date"],
                "daytemp": info_dict["forecasts"][0]["casts"][0]["daytemp"],
                "nighttemp": info_dict["forecasts"][0]["casts"][0]["nighttemp"],
                "daywind": info_dict["forecasts"][0]["casts"][0]["daywind"],
                "nightwind": info_dict["forecasts"][0]["casts"][0]["nightwind"]
            }
            
            t = json.dumps(forecast_data)
            b_info_str = t.encode()
            # print(b_info_str)

            producer.send("easy-quickstart-events-1", b_info_str)
            print(b_info_str)
        except Exception as e:
            print(e)
            pass

        time.sleep(10)




