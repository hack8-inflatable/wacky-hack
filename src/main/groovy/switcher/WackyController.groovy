package switcher

import org.apache.camel.EndpointInject
import org.apache.camel.ProducerTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static org.springframework.http.HttpStatus.NON_AUTHORITATIVE_INFORMATION
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping("/wacky")
public class WackyController {

    @Autowired
    WaverStatus waverStatus;


    @EndpointInject(uri = "seda:activateWaver")
    ProducerTemplate activateWaver;

    @EndpointInject(uri = "seda:turnOnWaver")
    ProducerTemplate turnOnWaver;

    @EndpointInject(uri = "seda:turnOffWaver")
    ProducerTemplate turnOffWaver;


    @RequestMapping(value = "/switchOnFor", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void switchOnFor(@Valid @RequestParam long time) {
        activateWaver.sendBody(time)
    }

    @RequestMapping(value = "/switch", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void switchOnOrOff(@Valid @RequestParam boolean on) {

        if (on) {
            turnOnWaver.sendBody(true);
        } else {
            turnOffWaver.sendBody(true);
        }

    }

    @RequestMapping(value = "/switch", method = GET)
    public boolean switchStatus() {
        waverStatus.waverOn
    }


}
