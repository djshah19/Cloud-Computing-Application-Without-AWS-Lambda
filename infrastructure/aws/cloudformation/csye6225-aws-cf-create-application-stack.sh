#!/bin/sh

stack_name = $1

aws cloudformation create-stack --stack-name $stack_name --template-body file://csye6225-cf-application.json
