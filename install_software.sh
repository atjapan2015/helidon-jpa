#!/bin/bash

echo "Install Start"
echo ""

# set terraform version, by default is 0.12.21 (latest version on 2020-03-05)
if [ "$1" != "" ]
then
    TERRAFORM_VERSION="$1"
else
    TERRAFORM_VERSION=0.12.21
fi

echo "default Terraform Version is v${TERRAFORM_VERSION}"
echo ""

# install git
echo "Installing git ..."
sudo yum install -y git > /dev/null 2>&1

echo "Installed $(git --version)"
echo ""

# install oci
#usage:
# curl -LSs https://raw.githubusercontent.com/oracle-japan/weblogic-operator-handson/scripts/master/bin/install-oci.sh | bash
echo "Installing oci cli ..."
sudo yum install python3 -y > /dev/null 2>&1
rm -rf ~/lib/oracle-cli > /dev/null 2>&1
rm -f ~/oci_install.sh > /dev/null 2>&1
curl -L https://raw.githubusercontent.com/oracle/oci-cli/master/scripts/install/install.sh --output ~/oci_install.sh > /dev/null 2>&1
chmod 755 ~/oci_install.sh > /dev/null 2>&1
~/oci_install.sh --accept-all-defaults > /dev/null 2>&1
rm -f ~/oci_install.sh > /dev/null 2>&1
mkdir -p ~/.oci > /dev/null 2>&1

echo "Installed oci cli version $(oci --version)"
echo ""

# install kubectl
echo "Installing kubectl ..."
curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl > /dev/null 2>&1
chmod +x ./kubectl > /dev/null 2>&1
sudo mv ./kubectl /usr/local/bin/kubectl > /dev/null 2>&1

echo "Installed kubectl $(kubectl version --client --short)"
echo ""

# install terraform
echo "Installing terraform ..."
wget https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip > /dev/null 2>&1
unzip terraform_${TERRAFORM_VERSION}_linux_amd64.zip > /dev/null 2>&1
sudo mv ./terraform /usr/local/bin/terraform > /dev/null 2>&1

echo "Installed $(terraform version)"
echo ""

echo "Install Complete"