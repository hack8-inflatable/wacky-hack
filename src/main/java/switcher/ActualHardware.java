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
import java.util.concurrent.atomic.AtomicBoolean;

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
        return waverStatus;
    }

    protected GpioPinDigitalOutput waverRelay;

    private static final Logger log = LoggerFactory.getLogger(ActualHardware.class);

    private final GpioController gpio = GpioFactory.getInstance();

    private boolean waverStatus = false;

    @PostConstruct
    public void initializeHardware() {
        log.info("Initializing hardware interface for raspberry pi");
        log.info(" --- relay to turn on waver should be connected to GPIO pin #15");
        log.info(" --- when it goes to the low state it will turn on the wavy thing");
        waverRelay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,  PinState.HIGH);
    }


    @Consume(uri="seda:hardwareWaverControl")
    public synchronized void waver(boolean on) {
        waverStatus = on;
        log.info("turning {} waver", on);
        if (on) {
            waverRelay.low();
        }else {
            waverRelay.high();
        }
    }




}

