
# def divide(a, b):
#     try:
#         return a / b
#     except ZeroDivisionError as e:
#         raise ValueError('Invaild inputs') from e
    

# x, y = 5, 2
# result = divide(x, y)
# print( result)
# print('result', result)


# def divide(a, b):
#     if a == 2:
#         return a/b
#     else:
#         raise RaiseE("a 必须是数字")
#         # raise ValueError("a != 2")

    
# x, y = 3, 8
# result = divide(x, y)
# print('result', result)


import requests
import os
print(os.path.abspath('..'))


# 发送API请求
url = "https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=e9de18477afe93323ffe06a8b4bcf910"
r = requests.get(url)


