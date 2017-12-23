# ABAP CI with Postman

Visit my blog about this repository --> https://medium.com/pacroy/continuous-integration-in-abap-3db48fc21028

## Components

| Name | Description |
| --- | --- |
| abap_unit_coverage.postman_collection.json | Postman collection to run ABAP Unit and check code coverage |
| abap_sci.postman_collection.json | Postman collection to run ABAP Static Code Inspector (SCI) via ATC |
| NPL.postman_environment.json | Environment variables |
| sap.groovy | Groovy script for integrating into Jenkinsfile (see [example](https://github.com/pacroy/abap-rest-api/blob/master/Jenkinsfile)) |
| SimpleRESTTest.postman_collection.json | Postman collection to run API tests of [abap-rest-api](https://github.com/pacroy/abap-rest-api)  |

## Running with Postman

1) Import all JSON files into Postman  
2) Bulk-edit imported environment variables *NPL* and add/update the following variables accordingly  

```
host:vhcalnplci.dummy.nodomain
username:DEVELOPER
password:password
package:$REST_SIMPLE
coverage_min:80
```

3) Test running the collection with environment *NPL*  

\* You can check the host and port via transaction `SICF` and select *Test Service*.  
\** You can also selectively run several packages, classes, or programs. Feel free to adjust the request XML. See object uri and type from *ABAP Communication Log* view in ADT.  

## Postman Collections

| Name | Description |
| --- | --- |
| abap_unit_coverage | ABAP Unit and Code Coverage |
| abap_sci | ABAP Static Code Inspector (via ATC) |
| SimpleRESTTest | API Tests of [abap-rest-api](https://github.com/pacroy/abap-rest-api) |

## Environment/Global Variables

| Name | Description |
| --- | --- |
| protocol | `http` or `https` |
| port | port of your SAP server\* |
| client | Your SAP golden client |
| client_test | Your SAP client to run test against |
| coverage_type | `statement`, `branch`, or `procedure` |
| coverage_maxlevel | Number of depth level to cumulate and display coverage result |
| coverage_chklevel | Number of depth level to check against minimum percentage. 0 means only top-level |
| exclusion | Object to excluded from coverage check in JSON array of `{"name": "", "type": "", "reason": ""}`. See coverage result for actual name and type to exclude. |
| atc_variant | SCI Variant to run the check |
| host | hostname of your SAP server\* |
| username | User ID for logon |
| password | Password for logon |
| package | The package you want to run against, including its subpackages\** |
| coverage_min | Minimum coverage percentage to 'pass' |
| x-csrf-token | *Automatically filled during run* |

\* You can check the host and port via transaction `SICF` and select *Test Service*.  
\** You can also selectively run several packages, classes, or programs. Feel free to adjust the request XML. See object uri and type from *ABAP Communication Log* view in ADT.   

## Running with Newman CLI

See example (Windows batch) commands in [Groovy script](https://github.com/pacroy/abap-ci-postman/blob/master/sap.groovy).
