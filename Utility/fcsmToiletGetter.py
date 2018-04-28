# -*- coding: UTF8 -*-
import sys
import urllib
import re

with urllib.urlopen('http://www.fcsm.hu/szolgaltatasok/nyilvanos_illemhelyek_uzemeltetese/terkep_a_fovarosi_illemhelyekrol') as response:
    for line in response:
        line = line.decode('utf-8')
        print(line)

f = urllib.urlopen("http://www.fcsm.hu/szolgaltatasok/nyilvanos_illemhelyek_uzemeltetese/terkep_a_fovarosi_illemhelyekrol/")

content = f.read()
res = re.findall('bubbleContent.*', content)

for x in range(0, res.size(), 15):
    print "Új"
    print res[0+i]
    print res[1+i]
    print res[2+i]
    print res[3+i]
    print res[4+i]
    print res[5+i]
    print res[6+i]
    print res[7+i]
    print res[8+i]
    print res[9+i]
    print res[10+i]
    print res[11+i]
    print res[12+i]
    print res[13+i]
    print res[14+i]
