package com.thecodinginterface.kafkaprocessingsemantics.controllers;

import com.thecodinginterface.kafkaprocessingsemantics.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
class PersonController {

    static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Value("${kafka.topics.people.name}")
    private String personTopic;

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @PostMapping("/")
    public void publishPeople(@RequestBody Person person) {
        logger.info("Posted to publishPeople person " + person);
        ListenableFuture<SendResult<String, Person>> future = kafkaTemplate.send(
                personTopic,
                person.getProfession(),
                person
        );
        future.addCallback(new ListenableFutureCallback<SendResult<String, Person>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failed to produce person " + person, ex);
            }

            @Override
            public void onSuccess(SendResult<String, Person> result) {
                logger.info("published person " + result);
            }
        });
    }

}