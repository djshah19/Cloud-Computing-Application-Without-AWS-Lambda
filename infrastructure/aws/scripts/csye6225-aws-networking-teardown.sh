#!/bin/bash
vpcid=$1

# Delete subnets
for i in `aws ec2 describe-subnets --filters Name=vpc-id,Values="${vpcid}" | grep subnet- | sed -E 's/^.*(subnet-[a-z0-9]+).*$/\1/'`; do aws ec2 delete-subnet --subnet-id=$i; done

# Detach internet gateways
for i in `aws ec2 describe-internet-gateways --filters Name=attachment.vpc-id,Values="${vpcid}" | grep igw- | sed -E 's/^.*(igw-[a-z0-9]+).*$/\1/'`; do aws ec2 detach-internet-gateway --internet-gateway-id=$i --vpc-id=$vpcid; done

# Delete internet gateways
aws ec2 delete-internet-gateway --internet-gateway-id=$i

# Delete route table
for i in `aws ec2 describe-route-tables --filters Name=vpc-id,Values="${vpcid}" | grep rtb- | sed -E 's/^.*(rtb-[a-z0-9]+).*$/\1/'`; do aws ec2 delete-route-table --route-table-id=$i; done

# Delete route 
#aws ec2 delete-route --route-table-id=$i --destination-cidr-block 0.0.0.0/0

# Delete the VPC
aws ec2 delete-vpc --vpc-id ${vpcid}