# Basic Assumptions

- Only Create Job and Get job by id operations are exposed to the customer
- Label job input is a string and the labeler just counts the letters in this string
- If the input string contains exactly 9 letters the job fails - for testing and visualization
- 4 job statuses handled - AWAITING_SCHEDULING -> EXECUTING -> SUCCEEDED -> DISCARDED

# Tech Stack

- Spring Boot (Gradle)
- Spring Cloud Stream
- RabbitMQ
- Spring Actuator/Micrometer/Prometheus/Grafana
- MongoDB (NoSQL)
- PostMan for simulating requests

# How to run the project

- Run 'docker-compose up -d' from the root of the project


