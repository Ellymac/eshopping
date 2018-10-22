#!/bin/bash

cd eshopping/ejb-eshopping-client
java -classpath $CLASSPATH:../ejb-eshopping-bean/target/entity-bean.jar:../ejb-eshopping-api/target/ejb-eshopping-api-4.0.jar:target/ejb-eshopping-client-4.0.jar com/myshopping/app/MainClient
