package switcher

import org.apache.camel.builder.RouteBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

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
                .to("seda:turnOnWaver")
                .delay(simple('${body}'))
                .to("seda:turnOffWaver")
    }
}
