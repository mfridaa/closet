#!/bin/sh
ps aux | grep closet-0.0.1-SNAPSHOT.jar | awk '{print $2}' | xargs kill -9
export BUILD_ID=dontKillMeJenkins
nohup java -jar target/closet-0.0.1-SNAPSHOT.jar &
