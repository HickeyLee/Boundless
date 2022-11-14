
from ast import And
import requests
import time
import pandas as pd
from kafka import KafkaConsumer
from kafka import KafkaProducer
from kafka.errors import KafkaError



# consumer = KafkaConsumer('quickstart-events',
#                              bootstrap_servers=['1.15.120.226:9092'],
#                              auto_offset_reset='latest',
#                              group_id='dev', 
#                              consumer_timeout_ms=100000 )

consumer = KafkaConsumer('easy-quickstart-events-1',
                             bootstrap_servers=['1.15.120.226:9092'],
                             auto_offset_reset='earliest',
                             group_id='dev', 
                             consumer_timeout_ms=100000)
for message in consumer:
    print(message)
    



