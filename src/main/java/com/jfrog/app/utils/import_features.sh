#!/bin/bash
set -e
# Source generate_token.sh

BASE_URL=https://xray.cloud.getxray.app
projectKey=JFCFG

# Add code to extract JWT token
token=$(curl --location "$BASE_URL/api/v2/authenticate" --header 'Content-Type: application/json' --data @"src/main/java/com/jfrog/app/utils/jira_xray_secrets.json")

# Remove existing features zip file
rm -f features.zip || { echo "Failed to remove existing features zip file"; exit 1; }

# Zip features
zip -r features.zip src/test/java/com/jfrog/app/features/ -i \*.feature || { echo "Failed to zip features"; exit 1; }


# Import features to Jira
#sleep 10
import_response=$(curl --location 'https://xray.cloud.getxray.app/api/v1/import/feature?projectKey=JFCFG' --header 'Authorization: Bearer $token' --form 'file=@"features.zip"')
echo "$import_response"
keys=$(echo "$import_response" | jq -r '.updatedOrCreatedTests[].key' | paste -sd\; -) || { echo "Failed to extract keys from import response"; exit 1; }
echo $keys
# Export features from Jira
#rm -f exported_features.zip
#rm -f exported_features/*.feature
#curl --location "$BASE_URL/api/v2/export/cucumber?keys=$keys" \
#--header "Content-Type: application/json" \
#--header "Authorization: Bearer $token" \
#--output exported_features.zip || { echo "Failed to export features from Jira"; exit 1; }
#
#unzip -o exported_features.zip  -d exported_features || { echo "Failed to unzip exported features"; exit 1; }
#
## Run tests
#rm -f report.json
#mvn compile test -Dcucumber.plugin="json:report.json" -Dcucumber.features="exported_features/" || { echo "Failed to run tests"; exit 1; }
#
## Import the test results to Jira
#curl --location "$BASE_URL/api/v2/import/execution/cucumber" \
#--header "Content-Type: application/json" \
#--header "Authorization: Bearer $token" \
#--data-raw "$(cat report.json)" || { echo "Failed to import test results to Jira"; exit 1; }