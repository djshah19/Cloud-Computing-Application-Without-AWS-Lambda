#!/bin/sh

stack_name=$1

echo deleting stack

aws cloudformation create-stack --stack-name $stack_name
