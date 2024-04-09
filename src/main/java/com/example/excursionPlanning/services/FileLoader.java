package com.example.excursionPlanning.services;

import jakarta.persistence.Column;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class FileLoader {

    public byte[] loadFileAsBytes(String filePath) throws IOException {
        // Создаем объект ресурса, представляющий статический файл
        Resource resource = new ClassPathResource("static/" + filePath);

        // Получаем поток ввода из ресурса
        InputStream inputStream = resource.getInputStream();

        // Создаем ByteArrayOutputStream для сохранения байтов
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Читаем данные из InputStream и записываем их в ByteArrayOutputStream
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Закрываем потоки
        inputStream.close();
        outputStream.close();

        // Возвращаем массив байтов
        return outputStream.toByteArray();
    }
}
