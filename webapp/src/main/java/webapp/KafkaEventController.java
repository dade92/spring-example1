package webapp;

import adapters.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaEventController {

    private KafkaProducer kafkaProducer;

    public KafkaEventController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/produce")
    public ResponseEntity produceKafkaEvent() {
        kafkaProducer.sendMessage("Hello kafka !!");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
