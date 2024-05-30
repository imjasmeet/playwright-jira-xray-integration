#!/bin/bash
set -e

BASE_URL=https://xray.cloud.getxray.app
projectKey=JFCFG

# Add code to extract Auth token
token=$(curl --location "$BASE_URL/api/v2/authenticate" \
        --header 'Content-Type: application/json' \
        --data '{ "client_id": "22705543735747FFA82E18BBECA2D7BE","client_secret": "53786b5f1441fc6de206477140bbff8261882885fa39f73687fa74322d1e0411" }' | tr -d '"') || { echo "Failed to authenticate"; exit 1; }
echo $token
# Remove existing features zip file
rm -f features.zip || { echo "Failed to remove existing features zip file"; exit 1; }

# Zip features
zip -r features.zip src/test/java/com/jfrog/app/features/ -i \*.feature || { echo "Failed to zip features"; exit 1; }


# Import features to Jira
import_response=$(curl -vvv --location "$BASE_URL/api/v2/import/feature?projectKey=$projectKey" \
--header "Authorization: Bearer $token" \
--form "file=@"features.zip"") || { echo "Failed to import features to Jira"; exit 1; }
keys=$(echo "$import_response" | jq -r '.updatedOrCreatedTests[].key' | paste -sd\; -) || { echo "Failed to extract keys from import response"; exit 1; }
echo "Jasmeet: " $keys
# Export features from Jira
rm -f exported_features.zip
rm -f exported_features/*.feature
curl --location "$BASE_URL/api/v2/export/cucumber?keys=$keys" \
--header "Content-Type: application/json" \
--header "Authorization: Bearer $token" \
--output exported_features.zip || { echo "Failed to export features from Jira"; exit 1; }

unzip -o exported_features.zip  -d exported_features || { echo "Failed to unzip exported features"; exit 1; }

# Run tests
rm -f report.json
mvn compile test -Dcucumber.plugin="json:report.json" -Dcucumber.features="exported_features/" || { echo "Failed to run tests"; exit 1; }

# Import the test results to Jira
curl --location "$BASE_URL/api/v2/import/execution/cucumber" \
--header "Content-Type: application/json" \
--header "Authorization: Bearer $token" \
--data-raw "$(cat report.json)" || { echo "Failed to import test results to Jira"; exit 1; }