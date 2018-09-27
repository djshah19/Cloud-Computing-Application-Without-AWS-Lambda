#!/bin/sh
#shell script to create AWS network infrastructures

stack_name=$1


aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-networking.json
