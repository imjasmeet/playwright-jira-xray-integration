/*
package com.jfrog.app.utils;

public class HelperUtils {
    public static String getKeysFromImportFeatureResponse(String response) {
        return response.split("keys")[1].split("]")[0].split(":")[1].trim();
    }
}

#!/bin/bash

        # JSON string
        json_string='{
        "errors": [],
        "updatedOrCreatedTests": [
        {
        "id": "673280",
        "key": "JFCFG-285",
        "self": "https://jfrog-int.atlassian.net/rest/api/2/issue/673280"
        },
        {
        "id": "673281",
        "key": "JFCFG-286",
        "self": "https://jfrog-int.atlassian.net/rest/api/2/issue/673281"
        },
        {
        "id": "673282",
        "key": "JFCFG-287",
        "self": "https://jfrog-int.atlassian.net/rest/api/2/issue/673282"
        }
        ],
        "updatedOrCreatedPreconditions": []
        }'

        # Use jq to extract the keys and paste to replace newlines with semicolons
        keys=$(echo "$json_string" | jq -r '.updatedOrCreatedTests[].key' | paste -sd\; -)

        # Print the keys
        echo "$keys"*/
