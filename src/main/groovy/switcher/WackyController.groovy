package switcher

import groovy.util.logging.Slf4j
import org.apache.camel.EndpointInject
import org.apache.camel.ProducerTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

import static org.springframework.http.HttpStatus.*
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping("/wacky")
@Slf4j
public class WackyController {

    @Autowired
    WaverStatus waverStatus;


    private AtomicBoolean waverAvailabitityStatus = new AtomicBoolean(true);

    @EndpointInject(uri = "seda:activateWaver")
    ProducerTemplate activateWaverForDuration;

    @EndpointInject(uri = "seda:sendHardwareMessage")
    ProducerTemplate sendHardwareMessage;


    @RequestMapping(value = "/availabilty", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void availabeOnOff(@Valid @RequestParam boolean on) {
        log.info "waver new availabilty:$on"
        waverAvailabitityStatus.set(on)

        if (!on) {
            sendHardwareMessage.sendBody(false);
        }
    }


    @RequestMapping(value = "/availabilty", method = GET)
    public boolean availabilyStatus() {
        waverAvailabitityStatus.get()
    }

    @RequestMapping(value = "/switchOnFor", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void switchOnFor(@Valid @RequestParam long time) {
        if (!waverAvailabitityStatus.get()) {
            log.warn "waver switchOnFor rejected because not available"
            throw new WackyUnavailableException("wacky is not available")
        }
        if (!waverStatus.isWaverOn())
            activateWaverForDuration.sendBody(TimeUnit.SECONDS.toMillis(time))
    }

    @RequestMapping(value = "/switch", method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void switchOnOrOff(@Valid @RequestParam boolean on) {
        if (!waverAvailabitityStatus.get()) {
            log.warn "waver switchOnOrOff rejected because not available"
            throw new WackyUnavailableException("wacky is not available")
        }

        sendHardwareMessage.sendBody(on);
    }

    @RequestMapping(value = "/switch", method = GET)
    public boolean switchStatus() {
        waverStatus.waverOn
    }


    @RequestMapping(value = "/logout", method = POST)
    @ResponseStatus(UNAUTHORIZED)
    public boolean logout() {
    }
}

@ResponseStatus(value = LOCKED)
class WackyUnavailableException extends RuntimeException {
    public WackyUnavailableException(String message) {
        super(message);
    }

    public WackyUnavailableException() {
    }

    public WackyUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public WackyUnavailableException(Throwable cause) {
        super(cause);
    }

    public WackyUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
