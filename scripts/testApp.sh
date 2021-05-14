#!/bin/bash
set -euxo pipefail

##############################################################################
##
##  GH actions CI test script
##
##############################################################################

mvn -Dhttp.keepAlive=false 
    -Dmaven.wagon.http.pool=false 
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 
    -q clean package liberty:create liberty:install-feature liberty:deploy
mvn liberty:start
mvn failsafe:integration-test liberty:stop
mvn failsafe:verify
