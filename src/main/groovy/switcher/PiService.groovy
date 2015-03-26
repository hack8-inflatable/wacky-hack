package switcher

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service

import java.util.concurrent.Future

/**
 *
 * @author Nicolas GANDRIAU
 * @since 3/26/15
 */
@Service
class PiService {


    @Async
    Future<String> switckOnOrOf(boolean on) {
        println "Async::switckOnOrOf($on)..."

        sleep (2000)

        println "... Async::switckOnOrOf($on) - DONE"

        return new AsyncResult<String>("OK")
    }
}
