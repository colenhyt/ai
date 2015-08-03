import urllib2
import lxml.etree as etree
from bs4 import BeautifulSoup
import json

import os
import sys
import urlparse

import lxml.html
import lxml.etree
import curl
import re  

f = open('./file/sougou.html')
# read all file content
ht_string = f.read()
print ht_string

