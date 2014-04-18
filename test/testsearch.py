import urllib2
import lxml.etree as etree
from bs4 import BeautifulSoup

import os
import sys
import urlparse

import lxml.html
import lxml.etree
import curl
import re  

g_urls = {}
g_linkres = [5]
g_linkres[0]='<a(\s*)(.*?)(\s*)href=(\s*)([\"\s]*)([^\"\')+?)([\"\s]+)(.*?)>'
#g_linkres[0]='<a href=(\s*)([\"\s]*)([^\"\')+?)([\"\s]+)(.*?)>'

URL_STATUS_DOWNLOAD = 1
URL_STATUS_FINISH = 2

def downloadPage(url):
	c = curl.Curl()
	c.set_timeout(8)
	c.get(url)
	return c.body()


def pushGlobalUrl(url):
	url2 = {}
	if not g_urls.has_key(url[2]):
		url2['data'] = url
		url2['status'] = URL_STATUS_DOWNLOAD
		g_urls[url[2]] = url2

def findPageURLs(base_url,ht_string):
#	ht_doc = lxml.html.fromstring(ht_string, base_url)
#	elms = ht_doc.xpath("//li[@style='font-size:15px; line-height:29px;']/a")
#	for i in elms:
#		url = urlparse.urljoin(base_url, i.get('href'))
#		pushUrl(url)
	urls = []
	url = re.findall(g_linkres[0],ht_string,re.S|re.I) 
	for u in url:
#		u[2]= urlparse.urljoin(base_url,url[2])
		urls.append(u)
	return urls

def testSearch(url):
	ht_string = downloadPage(base_url)

base_url = 'http://finance.ce.cn/stock/'
ht_string = downloadPage(base_url)
urls3 = findPageURLs(base_url,ht_string)

import testbasic

base_url = "http://taobao.com"
ht_string = testbasic.getStringFile('../files/babyshops.htm')
urls3 = findPageURLs(base_url,ht_string)
for i in urls3:
	print i[5]
#	print g_urls[i]['data'][2]

