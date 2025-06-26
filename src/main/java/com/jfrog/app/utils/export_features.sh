rm -f exported_features.zip
rm -f exported_features/*.feature
curl --location 'https://xray.cloud.getxray.app/api/v2/export/cucumber?keys=XYZ-285' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer ass.sds.ds-pOFy0' \
--output exported_features.zip
unzip -o exported_features.zip  -d exported_features
