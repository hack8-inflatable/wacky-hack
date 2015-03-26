package switcher;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.apache.camel.Consume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Actually talk to the raspberry pi hardware and do stuff
 * @author Ryan Gardner
 * @date 3/26/15
 */
@Component
@Profile("raspberryPi")
public class ActualHardware implements WaverStatus {

    @Override
    public boolean isWaverOn() {
        return waverRelay.isLow();
    }

    protected GpioPinDigitalOutput waverRelay;

    private static final Logger log = LoggerFactory.getLogger(ActualHardware.class);

    private final GpioController gpio = GpioFactory.getInstance();

    @PostConstruct
    public void initializeHardware() {
        log.info("Initializing hardware interface for raspberry pi");
        waverRelay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,  PinState.HIGH);
    }


    @Consume(uri="seda:turnOnWaver")
    public void turnOnWaver() {
        log.info("turning ON waver");
        waverRelay.low();
    }

    @Consume(uri="seda:turnOffWaver")
    public void turnOffWaver() {
        log.info("turning OFF waver");
        waverRelay.high();
    }



}

