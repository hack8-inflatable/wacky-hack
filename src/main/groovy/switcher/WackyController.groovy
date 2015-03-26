package switcher

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static org.springframework.http.HttpStatus.NON_AUTHORITATIVE_INFORMATION
import static org.springframework.web.bind.annotation.RequestMethod.POST
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping("/wacky")
public class WackyController {


    @RequestMapping(value="/switchOnFor", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void switchOnFor( @Valid @RequestParam long time) {

        println "POST::switchFor?time=$time"
    }

    @RequestMapping(value="/switch", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void switchOnOrOff( @Valid @RequestParam boolean on) {

        println "POST::switchOnorOff?on=$on"
    }

    @RequestMapping(value="/switch", method = GET)
    public boolean switchStatus( ) {

        println "GET::switchStatus"

        false
    }




}
