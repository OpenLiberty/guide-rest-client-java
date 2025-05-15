#!/bin/bash
set -euxo pipefail
./mvnw -version

##############################################################################
##
##  GH actions CI test script
##
##############################################################################

./mvnw -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -q clean package liberty:create liberty:install-feature liberty:deploy
./mvnw -ntp liberty:start
./mvnw -ntp failsafe:integration-test liberty:stop
./mvnw -ntp failsafe:verify
