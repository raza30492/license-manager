1. Add Products

url: /api/products method: POST
body:
{
	"name": "Time And Action Calender",
	"productPrefix": "TNA",
	"flavours": "EXPRESS",
	"description": "It is an Application for Tracking various activities of an Organization."
}

2. Add Users

url: /api/users method: POST
body:
{
	"firstName": "Demo",
	"lastName": "User",
	"username": "demo_user",
	"email": "noreply@jaza-soft.com",
	"roles": "ROLE_CLIENT",
	"mobile": "8904360418",
	"company": {
		"name": "Jaza Software (OPC) Private Limited",
		"jobTitle": "Director",
		"address": {
			"line1": "#811, 10th 'A' Main",
			"line2": "Suit No - 311, 1st Floor, Indiranagar",
			"city": "Bangalore",
			"state": "Karnataka",
			"country": "India",
			"zipCode": "560038"
		}
	}
}

3. Allocate License

url: /api/licenses  method: POST
body:
{
	"productId": 1,
	"userId": 3,
	"licenseType": "TRIAL",
	"licenseFlavour": "EXPRESS",
	"entitlement": 50,
	"entitlementType": "USERS",
	"validity": 15
}
