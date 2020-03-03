#!/bin/bash
echo "start"

echo "fetch cwallet.sso ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['cwallet\.sso']}" | base64 -d | base64 -d > cwallet.sso
echo "fetch ewallet.p12 ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['ewallet\.p12']}" | base64 -d | base64 -d > ewallet.p12
echo "fetch keystore.jks ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['keystore\.jks']}" | base64 -d | base64 -d > keystore.jks
echo "fetch ojdbc.properties ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['ojdbc\.properties']}" | base64 -d | base64 -d > ojdbc.properties
echo "fetch sqlnet.ora ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['sqlnet\.ora']}" | base64 -d | base64 -d > sqlnet.ora
echo "fetch tnsnames.ora ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['tnsnames\.ora']}" | base64 -d | base64 -d > tnsnames.ora
echo "fetch truststore.jks ..."
kubectl get secret atp-demo-binding -o jsonpath="{['data']['truststore\.jks']}" | base64 -d | base64 -d > truststore.jks

echo "create Wallet_tfOKEATPDB.zip"
zip Wallet_tfOKEATPDB.zip cwallet.sso ewallet.p12 keystore.jks ojdbc.properties sqlnet.ora tnsnames.ora truststore.jks
rm cwallet.sso ewallet.p12 keystore.jks ojdbc.properties sqlnet.ora tnsnames.ora truststore.jks

echo "complete!"