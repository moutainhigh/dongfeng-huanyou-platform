set module_name=dongfeng-gateway-tcp
title %module_name%
@echo off
echo Begin  %module_name% Running ...
 java -jar -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC   %module_name%-1.0-SNAPSHOT-boot.jar
@pause