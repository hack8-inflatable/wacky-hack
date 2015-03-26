package switcher

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import java.util.concurrent.atomic.AtomicLong

import static java.lang.String.format
import static org.springframework.http.HttpStatus.NON_AUTHORITATIVE_INFORMATION
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping("/wacky")
public class WackyController {

    private static final String template = "Hello, %s!"
    private static final AtomicLong counter = new AtomicLong()

    @RequestMapping
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greet = new Greeting()


        return new Greeting(id: counter.incrementAndGet(), content: format(template, name),
                fields: [key1:"val1", key2: "val2"])
    }

    @RequestMapping(method = POST)
    @ResponseStatus(NON_AUTHORITATIVE_INFORMATION)
    public void witch( @Valid @RequestParam long time) {

        println "POST::wacky?time=$time"
    }
}
