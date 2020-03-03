#!/bin/bash

echo "Install Start"

# set terraform version, by default is 0.12.16 (latest version on 2019-11-20)
if [ "$1" != "" ]
then
    TERRAFORM_VERSION="$1"
else
    TERRAFORM_VERSION=0.12.16
fi

echo "--------------------------------------------------------------------------"
echo "echo TERRAFORM_VERSION"
echo ${TERRAFORM_VERSION}
echo "--------------------------------------------------------------------------"

# install git
sudo yum install -y git

echo "--------------------------------------------------------------------------"
echo "git --version"
git --version
echo "--------------------------------------------------------------------------"

# install kubectl
curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
source <(kubectl completion bash)

echo "--------------------------------------------------------------------------"
echo "kubectl version"
kubectl version
echo "--------------------------------------------------------------------------"

# install terraform
wget https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip
unzip terraform_${TERRAFORM_VERSION}_linux_amd64.zip
sudo mv ./terraform /usr/local/bin/terraform

echo "--------------------------------------------------------------------------"
echo "terraform version"
terraform version
echo "--------------------------------------------------------------------------"

echo "Install Complete"
