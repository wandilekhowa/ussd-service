package co.za.mamamoney.ussd.controller;

import co.za.mamamoney.ussd.dto.UssdRequest;
import co.za.mamamoney.ussd.dto.UssdResponse;
import co.za.mamamoney.ussd.service.UssdHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ussd")
public class UssdController {

    private final UssdHandlerService ussdHandlerService;

    public UssdController(UssdHandlerService ussdHandlerService) {
        this.ussdHandlerService = ussdHandlerService;
    }

    @PostMapping
    public ResponseEntity<UssdResponse> handleRequest(@RequestBody UssdRequest request) {
        if (request == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(ussdHandlerService.handleSession(request), HttpStatus.OK);
    }
}
