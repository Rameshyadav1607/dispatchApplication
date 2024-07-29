package com.iocl.dispatchapplication.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.model.CaptchaEntity;
import com.iocl.dispatchapplication.repository.CaptchaRepository;
import com.iocl.dispatchapplication.requestdto.EmployeeloginRequest;
import com.iocl.dispatchapplication.responsedto.CaptchaResponseData;

@Service
public class CaptchaService {

    private final CaptchaRepository captchaRepository;

    public CaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    public ResponseEntity<CaptchaResponseData> getCaptcha() throws IOException, java.io.IOException {
    	System.out.println("fromg getCaptcha()");
        BufferedImage image = new BufferedImage(200, 40, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 350, 40);
        String[] fontNames = { "Dialog", "KaiTi", "Comic Sans MS", "AngsanaUPC", "MV Boli", "Cooper Black", "MingLiU",
                "Microsoft YaHei Light", "Consolas", "Simplified Arabic", "Vijaya", "Lucida Bright",
                "Microsoft Yi Baiti", "FangSong", "Gill Sans Ultra Bold Condensed", "Malgun Gothic", "KodchiangUPC",
                "Algerian", "Calibri", "MingLiU-ExtB", "Centaur", "Showcard Gothic", "Agency FB", "Ebrima", "Forte",
                "Gadugi", "Levenim MT", "Microsoft Tai Le", "Calibri Light", "Perpetua", "Kokila", "Dialog",
                "Segoe Script", "Gill Sans MT", "Rockwell Condensed", "Segoe UI", "Sitka Subheading", "Century Gothic",
                "Yu Gothic", "Tahoma", "Raavi", "Aldhabi", "David", "Cordia New", "Bodoni MT Poster Compressed",
                "KaiTi", "KodchiangUPC", "Utsaah", "Franklin Gothic Demi", "Informal Roman", "Serif", "Browallia New",
                "MoolBoran", "Microsoft YaHei", "Snap ITC", "Imprint MT Shadow", "Bell MT", "SansSerif", "Gulim" };
        int index = (int) (Math.random() * (fontNames.length - 1));
        Font font = new Font(fontNames[index], Font.ITALIC, 35);
        graphics.setFont(font);
        String captchaValue = RandomStringUtils.randomAlphabetic(6).toUpperCase();
        CaptchaEntity captchaEntity = new CaptchaEntity();
        captchaEntity.setValue(captchaValue);
        captchaEntity.setExpiryTime(LocalDateTime.now().plusSeconds(200));
        captchaEntity = captchaRepository.save(captchaEntity);

        graphics.drawString(captchaValue, 10, 30);
        graphics.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        baos.close();

        CaptchaResponseData responseData = new CaptchaResponseData();
        responseData.setImage(bytes);
        responseData.setId(captchaEntity.getId());
        responseData.setCaptchaValue(captchaValue);

        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<?> checkCaptcha(EmployeeloginRequest loginRequest) {
        Optional<CaptchaEntity> captchaEntity = captchaRepository.findByValue(loginRequest.getCaptcha_value());
        if (captchaEntity.isPresent() && captchaEntity.get().getValue().equals(loginRequest.getCaptcha_value())
                && LocalDateTime.now().isBefore(captchaEntity.get().getExpiryTime())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).body("Invalid or expired captcha");
        }
    }
}
