package com.zhitail.app.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JLabel;

import org.apache.commons.io.FilenameUtils;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zhitail.frame.util.service.Result;

@Controller
public class PublicAction {
	public static final String CAPTCHA_IMAGE_FORMAT = "png";
	@RequestMapping(value = "/latex")
    public void upload(String code,HttpServletResponse response) throws IOException{
      
		TeXFormula formula = new TeXFormula(code);
		
		// Note: Old interface for creating icons:
		//TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
		// Note: New interface using builder pattern (inner class):
		TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(20).build();
		
		icon.setInsets(new Insets(5, 5, 5, 5));
		
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);
			byte[] captchaChallengeAsJpeg  = jpegOutputStream.toByteArray();
			ServletOutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
