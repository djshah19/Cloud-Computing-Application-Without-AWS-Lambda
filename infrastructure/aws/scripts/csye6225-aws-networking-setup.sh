#!/bin/bash
# create-aws-vpc

#variables used in script:
availabilityZone1="us-east-1a"
availabilityZone2="us-east-1b"
availabilityZone3="us-east-1c"
vpcName="VPC"
Name="foo"
puSubnetName1="PublicSubnet1"
puSubnetName2="PublicSubnet2"
puSubnetName3="PublicSubnet3"
prSubnetName1="PrivateSubnet1"
prSubnetName2="PrivateSubnet2"
prSubnetName3="PrivateSubnet3"
gatewayName="InternetGateway"
routeTableName="PublicRouteTable"
routeName="PublicRoute"
vpcCidrBlock="10.0.0.0/16"
subNetCidrBlock1="10.0.0.0/24"
subNetCidrBlock2="10.0.1.0/24"
subNetCidrBlock3="10.0.2.0/24"
subNetCidrBlock4="10.0.10.0/24"
subNetCidrBlock5="10.0.11.0/24"
subNetCidrBlock6="10.0.12.0/24"
destinationCidrBlock="0.0.0.0/0"

aws_response=$(aws ec2 create-vpc --cidr-block "$vpcCidrBlock" --instance-tenancy dedicated --output json)
vpcId=$(echo -e "$aws_response" | /usr/bin/jq '.Vpc.VpcId' | tr -d '"')
#name the vpc
aws ec2 create-tags --resources "$vpcId" --tags Key=Name,Value="$vpcName"

subnet_response1=$(aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock1" --availability-zone "$availabilityZone1")
subnetId_1=$(echo -e "$subnet_response1" |  /usr/bin/jq '.Subnet.SubnetId' | tr -d '"')

#name the subnet
aws ec2 create-tags --resources "$subnetId_1" --tags Key=Name,Value="$puSubnetName1"

subnet_response2=$(aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock2" --availability-zone "$availabilityZone2")
subnetId_2=$(echo -e "$subnet_response2" |  /usr/bin/jq '.Subnet.SubnetId' | tr -d '"')

#name the subnet
aws ec2 create-tags --resources "$subnetId_2" --tags Key=Name,Value="$puSubnetName2"


subnet_response3=$(aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock3" --availability-zone "$availabilityZone3")
subnetId_3=$(echo -e "$subnet_response3" |  /usr/bin/jq '.Subnet.SubnetId' | tr -d '"')

#name the subnet
aws ec2 create-tags --resources "$subnetId_3" --tags Key=Name,Value="$puSubnetName3"


subnet_response4=$(aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock4" --availability-zone "$availabilityZone1")
subnetId_4=$(echo -e "$subnet_response4" |  /usr/bin/jq '.Subnet.SubnetId' | tr -d '"')

#name the subnet
aws ec2 create-tags --resources "$subnetId_4" --tags Key=Name,Value="$prSubnetName1"


subnet_response5=$(aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock5" --availability-zone "$availabilityZone2")
subnetId_5=$(echo -e "$subnet_response5" |  /usr/bin/jq '.Subnet.SubnetId' | tr -d '"')

#name the subnet
aws ec2 create-tags --resources "$subnetId_5" --tags Key=Name,Value="$prSubnetName2"


subnet_response6=$(aws ec2 create-subnet --vpc-id "$vpcId" --cidr-block "$subNetCidrBlock6" --availability-zone "$availabilityZone3")
subnetId_6=$(echo -e "$subnet_response6" |  /usr/bin/jq '.Subnet.SubnetId' | tr -d '"')

#name the subnet
aws ec2 create-tags --resources "$subnetId_6" --tags Key=Name,Value="$prSubnetName3"


#create internet gateway
gateway_response=$(aws ec2 create-internet-gateway --output json)
gatewayId=$(echo -e "$gateway_response" |  /usr/bin/jq '.InternetGateway.InternetGatewayId' | tr -d '"')

#name the internet gateway
aws ec2 create-tags --resources "$gatewayId" --tags Key=Name,Value="$gatewayName"

#attach gateway to vpc
attach_response=$(aws ec2 attach-internet-gateway --internet-gateway-id "$gatewayId" --vpc-id "$vpcId")


#create route table for vpc
route_table_response=$(aws ec2 create-route-table --vpc-id "$vpcId" --output json)
routeTableId=$(echo -e "$route_table_response" |  /usr/bin/jq '.RouteTable.RouteTableId' | tr -d '"')

#name the route table
aws ec2 create-tags --resources "$routeTableId" --tags Key=Name,Value="$routeTableName"

#attach subnets to route table
associate_response=$(aws ec2 associate-route-table --subnet-id "$subnetId_1" --route-table-id "$routeTableId")

associate_response=$(aws ec2 associate-route-table --subnet-id "$subnetId_2" --route-table-id "$routeTableId")

associate_response=$(aws ec2 associate-route-table --subnet-id "$subnetId_3" --route-table-id "$routeTableId")

associate_response=$(aws ec2 associate-route-table --subnet-id "$subnetId_4" --route-table-id "$routeTableId")

associate_response=$(aws ec2 associate-route-table --subnet-id "$subnetId_5" --route-table-id "$routeTableId")

associate_response=$(aws ec2 associate-route-table --subnet-id "$subnetId_6" --route-table-id "$routeTableId")

#add route for the internet gateway
route_response=$(aws ec2 create-route --route-table-id "$routeTableId" --destination-cidr-block "$destinationCidrBlock" --gateway-id "$gatewayId")