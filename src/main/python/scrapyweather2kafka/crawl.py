import json
import requests
import time
import math
tp = str(math.floor(time.time()*1000))[:13]

print(tp)

# 爬取程序
# step1：获取沪深300、中证500等ETF成分股
# step2:获取成分信息
# step3:获取货币基金的价格
# step4：套利策略
# step5:对沪深300所有成分股进行监控，如果股价短时波动超过2%，发出报警信息




post_url = f"http://query.sse.com.cn/security/stock/queryExpandName.do?jsonCallBack=jsonpCallback77216007&secCodes=510010%2C510020%2C510030%2C510050%2C510060%2C510090%2C510100%2C510110%2C510120%2C510130%2C510150%2C510160%2C510170%2C510180%2C510190%2C510200%2C510210%2C510220%2C510230%2C510270%2C510290%2C510300%2C510310%2C510330%2C510350&_={tp}"
print(post_url)
# post_url = "http://query.sse.com.cn/security/stock/queryExpandName.do?jsonCallBack=jsonpCallback31489344&secCodes=510010%2C510020%2C510030%2C510050%2C510060%2C510090%2C510100%2C510110%2C510120%2C510130%2C510150%2C510160%2C510170%2C510180%2C510190%2C510200%2C510210%2C510220%2C510230%2C510270%2C510290%2C510300%2C510310%2C510330%2C510350&_=1656850321044"


response=requests.get(post_url,headers={'Referer': 'http://www.sse.com.cn/',
                                        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.53 Safari/537.36 Edg/103.0.1264.37'})

print(response)
# json_str=response.text[:]
# print(json_str)


http://www.neeq.com.cn/newShareController/infoResult.do?callback=jQuery211_1592489332270

http://www.neeq.com.cn/newShareController/infoResult.do?callback=jQuery211_" + str(timestamp)[:13]

    # callback = 'jQuery18306789193760800711_' + str(timestamp)
    # start_url = f'http://fundtest.eastmoney.com/dataapi1015/ztjj//GetBKListByBKType?callback={callback}&_={timestamp}'
    # response = requests.get(start_url, headers=headers)
    
    
    
    