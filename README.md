# How To Test
Run the docker files to set-up the environment

Swagger link: http://localhost:8080/swagger-ui/index.html#/

Endpoints description should be self-explanatory to test the APIs

# What could have been better?
- The test cases are not solid. Testcontainers (https://www.testcontainers.org/) library could be used for testing the kafka/db related operations. It is really good,I have used it before. But there was simply no time to implement.

- https://reflectoring.io/spring-scheduler/ could be useful to integrate the ShedLock
once the plan is to run multiple instances. since now, the jobs cannot handle scheduler synchronization over multiple instances
- How to handle a failed job? Cannot took this into consideration since the given time was very limited, but here is an interesting article
that explains how to handle a failed job -> https://medium.com/@roperluo.me/talk-about-how-spring-scheduled-tasks-are-used-on-a-large-scale-at-an-enterprise-level-59345c40ae1a
. I could also use Quartz since it seems simpler. https://stackoverflow.com/questions/4408858/quartz-retry-when-failure
- The consumer class in the kafka package should be separated in an another microservice. It does not really belong here, but given the time. this was the quickest solution I had came up with.