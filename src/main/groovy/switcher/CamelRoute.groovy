package switcher

import org.apache.camel.Exchange
import org.apache.camel.Expression
import org.apache.camel.builder.RouteBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import java.util.concurrent.TimeUnit

/**
 * @author Ryan Gardner
 * @date 3/26/15
 */
@Component
class CamelRoute extends RouteBuilder {
    static final Logger log = LoggerFactory.getLogger(CamelRoute)

    @Override
    void configure() throws Exception {
        log.info("configuring time delay route")

        from("seda:activateWaver")
                .setHeader("delayTime", simple('${body}'))
                .process({log.info("activating waver for ${TimeUnit.MILLISECONDS.toSeconds(it.in.body)} seconds")})
                .process({it.in.body = true})
                .to("seda:sendHardwareMessage")
                .delay(simple('${in.header.delayTime}')).asyncDelayed()
                .process({log.info("de-activating waver after it was running for ${TimeUnit.MILLISECONDS.toSeconds(it.in.headers['delayTime'])} seconds")})
                .process({it.in.body = false})
                .to("seda:sendHardwareMessage")

        from("seda:sendHardwareMessage")
            .throttle(1).timePeriodMillis(TimeUnit.SECONDS.toMillis(3))
            .to("seda:hardwareWaverControl")
    }
}
