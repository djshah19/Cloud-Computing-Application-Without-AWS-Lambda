#!/bin/bash
stack_name=$1
aws cloudformation create-stack --stack-name $stack_name --template-body file://script.json --capabilities CAPABILITY_NAMED_IAM