# 给你一个整数数组 nums 。如果任一值在数组中出现至少两次 ，返回 true ；如果数组中每个元素互不相同，返回false 。
# 输入：nums = [1,2,3,1]
# 输出：true


# from telnetlib import PRAGMA_HEARTBEAT
# from tkinter import S


# a = [1,2,3,4,5,5,5]
# s = set(a)
# print(s)
# print(len())

import re



        

phone = {'2':['a','b','c'],
            '3':['d','e','f'],
            '4':['g','h','i'],
            '5':['j','k','l'],
            '6':['m','n','o'],
            '7':['p','q','r','s'],
            '8':['t','u','v'],
            '9':['w','x','y','z']}
        
def backtrack(conbination,nextdigit):
    if len(nextdigit) == 0:
        res.append(conbination)
    else:
        for letter in phone[nextdigit[0]]:
            backtrack(conbination + letter,nextdigit[1:])

res = []
digits='234'
backtrack('',digits)
print(res)


