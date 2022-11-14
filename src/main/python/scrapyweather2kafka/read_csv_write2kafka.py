import re
from time import time
import json
from kafka import KafkaProducer
import time
import random

producer = KafkaProducer(bootstrap_servers=["1.15.120.226:9092"])

with open("E:/FlinkStudyTest/src/main/java/data/sensor_1.csv") as f:
    for line in f:
        res = line.split(',')
        # print(res)
        
        forecast_data = {
                "r_id": res[0],
                "user_id": res[1],
                "time_stamp": res[2],
                "temperature": res[3],
                "occur_dt": time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(time.time()-1000000 + random.randint(-15,15))),
                "send_dt": time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(time.time()))
            }
            
        t = json.dumps(forecast_data)
        print(t)
                
        b_info_str = t.encode()
        producer.send("quickstart-events-2", b_info_str)
        time.sleep(5)





