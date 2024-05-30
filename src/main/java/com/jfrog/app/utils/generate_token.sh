#!/bin/bash
set -e

BASE_URL=https://xray.cloud.getxray.app
token=$(curl --location "$BASE_URL/api/v2/authenticate" --header 'Content-Type: application/json' --data @"src/main/java/com/jfrog/app/utils/jira_xray_secrets.json")
export token