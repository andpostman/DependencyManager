package com.andpostman.dependencymanager.controller;

import com.andpostman.dependencymanager.dto.EventDto;
import com.andpostman.dependencymanager.dto.SubscriberDto;
import com.andpostman.dependencymanager.service.DependencyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DependencyController {

    private final DependencyServiceImpl dependencyService;

    @PostMapping(value = "/checkAndSubscribe")
    public boolean checkAndSubscribe(@RequestBody SubscriberDto body,
                                                     @RequestHeader @Nullable HttpHeaders headers){

            return dependencyService.isEventToSubscribeExist(body);


    }

    @PostMapping(value = "/report")
    public String report(@RequestBody EventDto body,
                                       @RequestHeader @Nullable HttpHeaders headers){

            return dependencyService.createEvent(body);

    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({NullPointerException.class})
//    public String emptyFieldsHandler(NullPointerException exception){
//        log.error(exception.getMessage());
//        return exception.getMessage();
//    }

}
