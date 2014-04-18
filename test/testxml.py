import urllib2
import lxml.etree as etree
from bs4 import BeautifulSoup

url = r'http://www.google.com.hk'

req = urllib2.Request(url)

html = urllib2.urlopen(req).read()
#html = '<html><title>aaaa</title><body id="1">abc<div>123</div>def<div>456</div>ghi</body></html>'

#etree.tostring(dom,encoding='utf-8')


soup = BeautifulSoup(html)

print soup.originalEncoding

import os
import sys
import urlparse

import lxml.html
import lxml.etree
import curl

def download(url):
	c = curl.Curl()
	c.set_timeout(8)
	c.get(url)
	return c.body()


def parse_url(base_url):
   d = 3
   a = 1
   ht_string = download(base_url)
   ht_doc = lxml.html.fromstring(ht_string, base_url)
   elms = ht_doc.xpath("//li[@style='font-size:15px; line-height:29px;']/a")
   for i in elms:
     print 'ddd'
     print urlparse.urljoin(base_url, i.get('href'))
     print lxml.etree.tostring(i, encoding='utf-8')

parse_url('http://finance.ce.cn/stock/')

