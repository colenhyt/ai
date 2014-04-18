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
g_linkres[0]='<a href=\"http://(\s*)([\"\s]*)([^\"\')+?)([\"\s]+)(.*?)>'

URL_STATUS_DOWNLOAD = 1
URL_STATUS_FINISH = 2

def download(url):
	c = curl.Curl()
	c.set_timeout(8)
	c.get(url)
	return c.body()


def pushUrl(url):
	url2 = {}
	if not g_urls.has_key(url[2]):
		url2['data'] = url
		url2['status'] = URL_STATUS_DOWNLOAD
		g_urls[url[2]] = url2

def parse_url(base_url):
	ht_string = download(base_url)
	ht_doc = lxml.html.fromstring(ht_string, base_url)
#	elms = ht_doc.xpath("//li[@style='font-size:15px; line-height:29px;']/a")
#	for i in elms:
#		url = urlparse.urljoin(base_url, i.get('href'))
#		pushUrl(url)
	url = re.findall(g_linkres[0],ht_string,re.S|re.I) 
	for u in url:
		pushUrl(u)

parse_url('http://finance.ce.cn/stock/')
for i in g_urls:
	print g_urls[i]['data'][2]

