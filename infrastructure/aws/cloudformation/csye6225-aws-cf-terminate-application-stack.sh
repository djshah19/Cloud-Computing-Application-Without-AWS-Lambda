#!/bin/sh

stack_name=$1

echo deleting stack

aws cloudformation delete-stack --stack-name $stack_name
