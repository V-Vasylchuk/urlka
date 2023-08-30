# URLKA
Welcome to the URL Shortener Service Prototype!

`URLKA` is a reference to what Ukrainians call a URL link. 
But if we are talking about the global market for this product, it is "URL kind again"
## Features
* Shorten long URLs to convenient short URLs.
* Retrieve the original URL using the shortened URL.
* Utilize MongoDB for persistent storage of original and shortened URLs.

# [Short video presentation](https://komododecks.com/recordings/v2jnMJ46MyUg4Hw2TLyf?onlyRecording=1)

## Getting Started
To get started with the test task solution, follow the instructions below:

### Local Run

* Clone the repository.
* Set your credentials for tests in application.properties.
* Build the project: `mvn clean package`.
* Run the application.
* Also, I prepared some `Postman` request `collection` for your convenience: [LINK](https://www.postman.com/vasylchuk/workspace/colections-for-testing-by-bbc/collection/3329996-c04b4e22-841d-41c1-8690-a6c57026eed6?action=share&creator=3329996)

### Run with docker
* Clone the repository from GitHub.
* run with command docker-compose up
* Use postman [collection](https://www.postman.com/vasylchuk/workspace/colections-for-testing-by-bbc/collection/3329996-c04b4e22-841d-41c1-8690-a6c57026eed6?action=share&creator=3329996)

## 🔨 Technologies Used
* Java 17
* Apache Maven
* Spring Boot
* Spring Data 
* Spring MVC
* MongoDB
* Redis
* Docker
* Lombok
* Unit Test
# What if
* _What if this service needed to scale to 10,000 URL generation
requests per second? How about 100,000 URL resolve requests per second? Describe how
you'd actually architect a system like this. Specifically, how would the URL generation work at
scale (and what generation method you'd use) and how the URL resolving would work at scale.
How would you store the data? How long would you keep it around?_


The first I thought about "Master-Slave relations", but it's not enough for such amounts of requests.
So, to handle lots of URL generation and resolution requests, I'd use many small parts of the service working together. For making short URLs, I'd spread the work across multiple machines so that no single machine gets overwhelmed. Each machine would use a mix of numbers and letters (some hash encoding) to make the short URLs. For finding the original URL from a short one, I'd use a quick storage system like Redis, which keeps popular mappings in memory to speed things up. The actual URLs and their short versions would be saved in a database like MongoDB. We'd keep the data in the database for a while, but not forever, maybe a few months.

This way, we can handle a lot of requests without slowing down and still keep things organized and working smoothly.
## Author
_[Vasyl Vasylchuk](https://www.linkedin.com/in/vasyl-vasylchuk-632303273/)_

