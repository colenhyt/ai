# -*- coding: utf-8 -*-

import os
import sys
import urlparse
from lxml.html import fromstring
from lxml import etree
from io import StringIO, BytesIO
from collections import OrderedDict

import lxml.html
import lxml.etree
import curl


def sort_by_valuelen(d):
    items=d.items()
    backitems=[[len(v[1]),v[0]] for v in items]
    backitems.sort(reverse=True)
    sortkeys = []
    for k in backitems:
        sortkeys.append([k[1],k[0]])
    return sortkeys

def isChild(parentTag,childTag):
    return 1

#两个tag相似度分析得分:
def tagSameScore(tag1,tag2):
    # tag count:
    es1 = tag1.xpath("*")
    if (len(es1)>20):return 0        #tag 数量过大，拦截
    es2 = tag2.xpath("*")
    if (len(es1)!=len(es2)):return 0      #数量不一致，拦截
    tagnames1 = ""
    tagkeys1 = ""
    for e in es1:
        tagnames1 += "_"+e.tag
        tagkeys1 += e.tag+ ":".join(e.keys());
    tagnames2 = ""
    tagkeys2 = ""
    for e in es2:
        tagnames2 += "_"+e.tag
        tagkeys2 += e.tag+":".join(e.keys())
    if (tagkeys1==tagkeys2):                        #子节点一致，子节点属性一致
        return 1
    elif (tagnames1==tagnames2) :              #子节点名一致
        return 0.8
    return 0

class HtmlPage():
    def __init__(self,itemCount=5,itemSame=1):
        self.type=0
        self.eleMaps={}
        self.sortKeys = []
        self.itemMinCount = itemCount     #item数量阀值
        self.itemSame = itemSame        #item相似度阀值

    def parse(self,htmlStr):
        self.doc = lxml.html.fromstring(htmlStr, base_url)
        tagNames = ["div","li"]
        chooseKey = ""
        for tagname in tagNames:
            chooseKey = self.parse_findItems(tagname)
            if (len(chooseKey)>0):break
        print chooseKey

    def parse_findItems(self,tagName):
        tagkeys = self._findTags(tagName)
        if (len(tagkeys)<=0):
            tagkeys = self._findTags(tagName,False)
        chooseKey = ""
        if (len(tagkeys)==1):
            chooseKey = tagkeys[0]
        # elif (len(tagkeys)>1):            #多个,仲裁取哪个:
        #     for k in tagkeys:
        #         item1 = self.eleMaps[k][0]
        return chooseKey

    def _findTags(self,tagname,hasUrl=True):
        elms = self.doc.xpath("//"+tagname)
        tagelems = {}
        for ele in elms:
            items = ele.items()
            allkey = ele.tag+"["
            inilen = len(allkey)
            for v in items:
                if (len(allkey)>inilen):
                    allkey += " and "
                allkey += "@"+v[0]+"='"+v[1]+"'"
            allkey += "]"
            if (len(allkey)>50): continue           #属性名过长, 判断为无效invalid tag
            # print allkey,len(ele.xpath("*"))
            if (hasUrl==True):
                urles = ele.xpath("a")
                if (len(urles)<=0):continue
            tag = []
            if (tagelems.has_key(allkey)==True):
                tag = tagelems[allkey]
            tag.append(ele)
            tagelems[allkey] =tag
        #判断节点是否为相似节点:
        sameelems = {}
        for k in tagelems:
            if (len(tagelems[k])<self.itemMinCount):continue
            issame = tagSameScore(tagelems[k][0],tagelems[k][1])
            if (issame>=self.itemSame):
                sameelems[k]=tagelems[k]
        sortkeys = sort_by_valuelen(sameelems)
        self.eleMaps = sameelems
        return sortkeys

    def isCat(self):
        return 0

    def judgeCat(self,base_url):
        # div,li等内容块判断:找出数量大于某个阀值(5)内容块,
        ht_doc = lxml.html.fromstring(self.content, base_url)
        elms = ht_doc.xpath("//div")
        return 11

    def isItem(self):
        return 1
    
base_url="http://weixin.sougou.com"
f = open('./file/sougou.html')
# read all file content
ht_string = f.read()
page = HtmlPage()
page.parse(ht_string)

dic={'a':[1,4,5,6,1],'b':[1,2,5,5],'c':[1,3,4,3,2,3]}
dic2 = sort_by_valuelen(dic)
# print dic2