package switcher

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static org.springframework.http.HttpStatus.NON_AUTHORITATIVE_INFORMATION
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping("/wacky")
public class WackyController {


    @RequestMapping(method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void witch( @Valid @RequestParam long time) {

        println "POST::wacky?time=$time"
    }
}
