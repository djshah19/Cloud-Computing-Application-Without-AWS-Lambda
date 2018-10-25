#!/bin/sh

stack_name=$1

bucket_name=$2

bucketName="csye6225-fall2018-$bucket_name.csye6225.com" 

echo deleting stack

aws s3 rb s3://$bucketName --force

aws cloudformation delete-stack --stack-name $stack_name
