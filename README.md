# RecommendationSystem

### Prerequisite:-
1. Docker

### What will be not included due to time constraints [don't mind please I am working 9 hours a day and I am family man :) ]:-
1. Test Cases
2. Validation at certain levels
3. Spring and Codding best practices

### How I have tried to solve this problem.
- Get information about the Pattern Mining and find out two algorithm Apriori and FPGrowth
- I have used FPGrowth here to solved this problem.
- All implementation is using JAVA , Spring and Mongodb.
- Containerized the application using docker and docker compose to make it quick run for you.

### What more I can to with this problem using python. However, I am not hardcore python programmer.
- We can develop Content Based Recommendation system
- We can develop recommendation engine using cosine similarity using Elastic search and Google Burt Modal.
- I have experience using Pandas, nltk kind of libraries which helps in NLP.

### Setup Steps:-

1. Go to Project Root Directory using cmd
2. Run below two Command respectively
     1. docker-compose build 
     2. docker-compose up

### User Below APIs to create Order and get Recommendations.
##### 1. http://localhost:8080/orderengine/placeOrder
 Request Body : 
{
	"id": "",
	"products" : [
		{
			"id" : "id_1205",
			"customerIP" : "12.227.260.77",
			"productName" : "Decleor Dry Skin Bundle"
		}
	]
}

##### 2. http://localhost:8080/orderengine/getRecommendedProducts/Dermalogica Skin Smoothing Cream 200ml --> GET Request

 This API will give top 5 product Recommendations
##### Expected Responce:-
[
    "Elemis Pro-Collagen Marine Mask 50ml",
    "The Ordinary Niacinamide 20% + Zinc 2% 10ml",
    "Nioxin System 2 Cleanser 100ml",
    "Ultrasun Sports High SPF50 250ml",
    "The Ordinary Buffet 10ml"
]
