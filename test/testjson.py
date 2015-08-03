# -*- coding: utf-8 -*-
import time
import json

test_data = {
    'baihe': {
        'name': unicode('百合', 'utf-8'),
        'say': unicode('清新，淡雅，花香', 'utf-8'),
        'grow_time': 0.5,
        'fruit_time':  0.5,
        'super_time': 0.5,
        'total_time': 1,
        'buy':{'gold':2, } ,
        'harvest_fruit': 1,
        'harvest_super': 1,
        'sale': 1,
        'level_need': 0,
        'experience' : 2,
        'exp_fruit': 1,
        'exp_super': 1,
        'used': True,
    },
    '1':{
        'interval' : 0.3,
        'probability' : {
            '98': {'chips' : (5, 25), },
            '2' : {'gem' : (1,1), },
        },
    },
    '2':{
        'unlock' : {'chips':1000, 'FC':10,},
        'interval' : 12,
        'probability' : {
            '70': {'chips' : (120, 250), },
            '20': {'gem' : (1,1), },
            '10': {'gem' : (2,2), },
        },
    },
    'one':{
        '10,5' :{'id':'m01', 'Y':1, 'msg':u'在罐子里发现了一个银币！',},
        '3,7'  :{'id':'m02', 'Y':10,'msg':u'发现了十个银币！好大一笔钱！',},
        '15,5' :{'id':'m03', 'Y':2, 'msg':u'一只老鼠跑了过去',},
        '7,4'  :{'id':'m04', 'Y':4, 'msg':u'发现了四个生锈的银币……',},
        '2,12' :{'id':'m05', 'Y':6, 'msg':u'六个闪亮的银币！',},
    },

}

start_time = time.time()
print "start_time:", start_time

j = 1
while True:
    j += 1
    a = json.dumps(test_data)
    data_length = len(a)
    end_time = time.time()
    if end_time - start_time >= 1 :
        break
print "loop_num:", j
print "end_time:  ",end_time
print data_length ,a

s = json.loads('{"name":[111,2,3,4], "type":{"name":"seq", "parameter":["1", "2"]}}')
s['aaa']=[1333,2,3]
s['bbb']={'a':333}
print s
print s.keys()
print s["aaa"][0]
print s["bbb"]['a']
print s["type"]["name"]
print s["type"]["parameter"][1]