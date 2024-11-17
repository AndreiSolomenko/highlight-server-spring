package com.onrender.highlightserverspring;

import net.sourceforge.tess4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OCRController {

    @PostMapping("/api/process-image")
    public Response processImage(@RequestParam("image") MultipartFile image, @RequestParam("language") String language) {
        try {

            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            ITesseract instance = new Tesseract();

            instance.setDatapath("src/main/resources/tessdata");

            instance.setLanguage(language);

            String resultText = instance.doOCR(bufferedImage);

            return new Response(resultText);
        } catch (IOException e) {
            throw new RuntimeException("Помилка при обробці зображення", e);
        } catch (TesseractException e) {
            throw new RuntimeException("Помилка OCR", e);
        }
    }

    // Клас відповіді
    public static class Response {
        private String text;

        public Response(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
