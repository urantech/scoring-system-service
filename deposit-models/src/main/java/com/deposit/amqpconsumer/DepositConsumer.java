package com.deposit.amqpconsumer;

import com.deposit.modelservice.ModelService;
import com.scoring.commons.dto.kafka.DepositDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DepositConsumer {

    private final ModelService modelService;

    @RabbitListener(queues = "${rabbitmq.queues.deposit-models}")
    public void consume(DepositDto deposit) {
        final String depositId = deposit.getDepositId();
        log.info("Consumed deposit with depositId = {}", depositId);
        try {
            modelService.scoreDeposit(deposit);
        } catch (RuntimeException ex) {
            log.error("ERROR during scoring deposit = {}", depositId);
        }
    }
}
