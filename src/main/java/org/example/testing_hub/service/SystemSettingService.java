package org.example.testing_hub.service;

import org.example.testing_hub.entity.SystemSetting;
import org.example.testing_hub.entity.ValueType;
import org.example.testing_hub.repository.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SystemSettingService {

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    // Получить настройку по ключу
    public SystemSetting getSettingByKey(String key) {
        return systemSettingRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Setting not found for key: " + key));
    }

    // Создать или обновить настройку
    public SystemSetting updateSetting(SystemSetting setting) {
        return systemSettingRepository.save(setting);
    }
    public List<SystemSetting> getAllSettings() {
        return systemSettingRepository.findAll();
    }


    // Пример метода использования
    public void exampleUsage() {
        // Получение настройки
        SystemSetting languageSetting = getSettingByKey("DEFAULT_LANGUAGE");
        System.out.println("Default Language: " + languageSetting.getValue());

        // Создание или обновление настройки
        SystemSetting newSetting = new SystemSetting();
        newSetting.setKey("MAX_LOGIN_ATTEMPTS");
        newSetting.setValue("5");
        newSetting.setValueType(ValueType.INTEGER);
        newSetting.setDescription("Максимальное количество попыток входа");
        updateSetting(newSetting);
    }
}
