def abap_unit_coverage(HOST,CREDENTIAL,PACKAGE,COVERAGE) {	
	println "CREDENTIAL=" + CREDENTIAL

	withCredentials([usernamePassword(credentialsId: CREDENTIAL, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		stage('ABAP Unit and Code Coverage') {
			dir('sap-pipeline') {
				bat "newman run abap_unit_coverage.postman_collection.json --insecure --bail " +
				"--environment NPL.postman_environment.json " +
				"--timeout-request 120000 " +
				"--global-var host=$HOST " +
				"--global-var username=$USERNAME " +
				"--global-var password=$PASSWORD " +
				"--global-var package=$PACKAGE " +
				"--global-var coverage_min=$COVERAGE "
			}
		}
	}
}

def abap_sci(HOST,CREDENTIAL,PACKAGE) {	
	println "CREDENTIAL=" + CREDENTIAL
	
	withCredentials([usernamePassword(credentialsId: CREDENTIAL, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {	
		stage('ABAP Code Inspector') {
			dir('sap-pipeline') {
					bat "newman run abap_sci.postman_collection.json --insecure --bail " +
					"--environment NPL.postman_environment.json " +
					"--timeout-request 120000 " +
					"--global-var host=$HOST " +
					"--global-var username=$USERNAME " +
					"--global-var password=$PASSWORD " +
					"--global-var package=$PACKAGE " 
			}
		}
	}
}

def sap_api_test(HOST,CREDENTIAL) {	
	println "CREDENTIAL=" + CREDENTIAL
	
	withCredentials([usernamePassword(credentialsId: CREDENTIAL, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		stage('SAP API Tests') {
			dir('sap-pipeline') {
				try {
					bat "newman run SimpleRESTTest.postman_collection.json --insecure --bail " + 
					"--environment NPL.postman_environment.json " + 
					"--reporters junit " +
					"--timeout-request 10000 " +
					"--global-var host=$HOST " +
					"--global-var username=$USERNAME " + 
					"--global-var password=$PASSWORD "
				} catch(e) {
					skip_pipeline = true
					currentBuild.result = 'FAILURE'
				}
				junit 'newman/*.xml'
			}
		}
	}
}

return this