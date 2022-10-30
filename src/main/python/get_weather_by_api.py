
from ast import And
import requests
import time
import pandas as pd
from kafka import KafkaConsumer
from kafka import KafkaProducer
from kafka.errors import KafkaError

url = "https://restapi.amap.com/v3/weather/weatherInfo"
key = 'e9de18477afe93323ffe06a8b4bcf910'

df = pd.read_table('E:/py_work/config/AMap_adcode_citycode.txt', sep=',')
city_list = list(df.iloc[:,1])
producer = KafkaProducer(bootstrap_servers=['1.15.120.226:9092'])
# producer = KafkaProducer(bootstrap_servers=['127.0.0.1:9092'])


# 24H中，6-23点执行，其余时间不请求信息；
while (int(time.strftime("%H"))>=6) & (int(time.strftime("%H"))<=22):
    
    for city in city_list:
        data = {'key': key, "city": city, "extensions": "all"}
        req = requests.post(url, data)
        info_dict = dict(req.json())
        b_info_str = str(info_dict).encode()
        print(b_info_str)
                
        producer.send('quickstart-events', b_info_str)
        time.sleep(8)




# consumer = KafkaConsumer('quickstart-events',bootstrap_servers=['1.15.120.226:9092'])
# for message in consumer:  # consumer是一个消息队列，当后台有消息时，这个消息队列就会自动增加．所以遍历也总是会有数据，当消息队列中没有数据时，就会堵塞等待消息带来
#     print(message)

# consumer = KafkaConsumer('quickstart-events',
#                              bootstrap_servers=['1.15.120.226:9092'],
#                              auto_offset_reset='earliest', # 设置偏移方式，当参数值为earliest时从头开始消费；当参数值为latest时从最新数据开始消费
#                              group_id='dev', consumer_timeout_ms=100000
#                              )# 该参数表示1000ms内如果没有新的数据产生则停止消费，否则会一直循环等待)
# for message in consumer:  # consumer是一个消息队列，当后台有消息时，这个消息队列就会自动增加．所以遍历也总是会有数据，当消息队列中没有数据时，就会堵塞等待消息带来
#     print(message)

# producer = KafkaProducer(bootstrap_servers=['1.15.120.226:90921.15.120.226:9092'])
# for _ in range(100):
#     producer.send('quickstart-events', b'some_message_bytes')




# producer = KafkaProducer(bootstrap_servers=['1.15.120.226:9092']) # 此处传入kafka的地址和端口
# msg = b"hello kafka" # 必须要编码为字节类型的数据，不可以用utf-8
# meg = msg.encode()
# 同步发送
# future = producer.send("quickstart-events", value=msg) # 此处传入kafka的topic

# producer = KafkaProducer(bootstrap_servers=['localhost:9092']) 
# future = producer.send("quickstart-events", b"hello kafka") 

# try:
#     future.get(timeout=10)
# except Exception as e:
#     print(e)
# producer.close()



# test passed
# consumer = KafkaConsumer('quickstart-events',
#                          group_id = 'test_group_1',
#                          bootstrap_servers=['1.15.120.226:9092'],
#                          auto_offset_reset='earliest')
# for msg in consumer:
#     print(msg)
#     print(msg.value)

# def send_to_kafka():
#     pass 

# def receive_kafka():
#     pass 
    
