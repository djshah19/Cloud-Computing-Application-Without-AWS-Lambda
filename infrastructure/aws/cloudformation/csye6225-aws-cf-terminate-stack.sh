#!/bin/sh
#shell script to delete AWS stack

echo deleting the stack


aws cloudformation delete-stack --stack-name testStack 
