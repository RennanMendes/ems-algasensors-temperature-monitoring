package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorAlertService {

    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void handleAlert(TemperatureLogData temperatureLogData) {
        sensorAlertRepository.findById(new SensorId(temperatureLogData.getSensorId())).ifPresentOrElse(alert -> {
            if (alert.getMaxTemperature() != null && temperatureLogData.getValue().compareTo(alert.getMaxTemperature()) >= 0) {
                log.info("Alert Max Temp: Sensor Id {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
            } else if (alert.getMinTemperature() != null && temperatureLogData.getValue().compareTo(alert.getMinTemperature()) <= 0) {
                log.info("Alert Min Temp: Sensor Id {} Temp {}", temperatureLogData);
            } else {
                logIgnoredAlert(temperatureLogData);
            }
        }, () -> logIgnoredAlert(temperatureLogData));
    }

    private void logIgnoredAlert(TemperatureLogData temperatureLogData) {
        log.info("Alert Ignored: Sensor Id {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }

}