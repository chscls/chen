package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ThumbnailatorTest {

    /**
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ThumbnailatorTest thumbnailatorTest = new ThumbnailatorTest();
     
        thumbnailatorTest.test2("e:/xxx/yyy",0.25f);
        System.out.print("ok");
      
    }

    /**
     * 指定大小进行缩放
     * 
     * @throws IOException
     */
 

    /**
     * 按照比例进行缩放
     * 
     * @throws IOException
     */
    private void test2(String path,float f) throws IOException {
        /**
         * scale(比例)
         */
    	
    	 File file=new File(path);
    	  File[] tempList = file.listFiles();
    	  System.out.println("该目录下对象个数："+tempList.length);
    	  for (int i = 0; i < tempList.length; i++) {
    	   if (tempList[i].isFile()) {
    		 System.out.println(tempList[i].getAbsolutePath());
    		 System.out.println("e:/s/"+tempList[i].getName());
    		   Thumbnails.of(tempList[i].getAbsolutePath()).scale(f).toFile("e:/s/"+tempList[i].getName());
    		      
    	    
    	   }
    	   if (tempList[i].isDirectory()) {
    	    System.out.println("文件夹："+tempList[i]);
    	   }
    	  }
         //Thumbnails.of("images/test.jpg").scale(1.10f).toFile("C:/image_110%.jpg");
    }
    
}
