#!/bin/sh
#shell script to create AWS network infrastructures



aws cloudformation create-stack --stack-name testStack --template-body file://csye6225-cf-networking.json

