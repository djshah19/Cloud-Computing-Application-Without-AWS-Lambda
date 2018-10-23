#!/bin/sh
#shell script to delete AWS stack
stack_name=$1

echo deleting the stack



aws cloudformation delete-stack --stack-name $stack_name 