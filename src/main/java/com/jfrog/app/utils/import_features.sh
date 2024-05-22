#!/bin/bash
#Add code to extarct JWT token from curl --location 'https://xray.cloud.getxray.app/api/v2/authenticate' \
                                #--header 'Content-Type: application/json' \
# Import Feature files to Jira-xray                                #--data '{ "client_id": "22705543735747FFA82E18BBECA2D7BE","client_secret": "53786b5f1441fc6de206477140bbff8261882885fa39f73687fa74322d1e0411" }'
echo "Removing existing features zip file"
rm -f features.zip
echo "Zipping features"
zip -r features.zip src/test/java/com/jfrog/app/features/ -i \*.feature
echo "Importing features to Jira "
import_response=$(curl --location 'https://xray.cloud.getxray.app/api/v2/import/feature?projectKey=JFCFG' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiJmZTFkOGQ1NS1mZjc0LTM0NTgtODQyMS05NDIxMDFhNjk3N2MiLCJhY2NvdW50SWQiOiI2MzZiNjg0NmMzODNhZDg0MjE0Njc2ZTAiLCJpc1hlYSI6ZmFsc2UsImlhdCI6MTcxNjIyMTc5NiwiZXhwIjoxNzE2MzA4MTk2LCJhdWQiOiIyMjcwNTU0MzczNTc0N0ZGQTgyRTE4QkJFQ0EyRDdCRSIsImlzcyI6ImNvbS54cGFuZGl0LnBsdWdpbnMueHJheSIsInN1YiI6IjIyNzA1NTQzNzM1NzQ3RkZBODJFMThCQkVDQTJEN0JFIn0.xkAIoqL1sRf8IGwihCP5yFJOV8gcFek07F7t8Yz1ZT4' \
--form 'file=@"features.zip"')
echo "Imported features successfully"
keys=$(echo "$import_response" | jq -r '.updatedOrCreatedTests[].key' | paste -sd\; -)
echo "$keys"

# Export Feature files from Jira-xray
rm -f exported_features.zip
rm -f exported_features/*.feature
curl --location 'https://xray.cloud.getxray.app/api/v2/export/cucumber?keys=JFCFG-285' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiJmZTFkOGQ1NS1mZjc0LTM0NTgtODQyMS05NDIxMDFhNjk3N2MiLCJhY2NvdW50SWQiOiI2MzZiNjg0NmMzODNhZDg0MjE0Njc2ZTAiLCJpc1hlYSI6ZmFsc2UsImlhdCI6MTcxNjM4NDQxNiwiZXhwIjoxNzE2NDcwODE2LCJhdWQiOiIyMjcwNTU0MzczNTc0N0ZGQTgyRTE4QkJFQ0EyRDdCRSIsImlzcyI6ImNvbS54cGFuZGl0LnBsdWdpbnMueHJheSIsInN1YiI6IjIyNzA1NTQzNzM1NzQ3RkZBODJFMThCQkVDQTJEN0JFIn0.Qx7RxG_R_OETVvsJtcPLeSJWxqX1WBWyLnb3m-pOFy0' \
--output exported_features.zip
unzip -o exported_features.zip  -d exported_features

#Run tests

mvn compile test -Dcucumber.plugin="json:report.json" -Dcucumber.features="exported_features/"
