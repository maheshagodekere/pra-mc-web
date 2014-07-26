package com.pradeya.cast.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VideoThumbnailCreatorUtil{
	
	private static final Logger logger = LoggerFactory.getLogger(VideoThumbnailCreatorUtil.class);

    public static final double SECONDS_BETWEEN_FRAMES = 1;

    // The video stream index, used to ensure we display frames from one and
    // only one video stream from the media container.
    private static int mVideoStreamIndex = -1;

    // Time of last frame write
//    private static long mLastPtsWrite = Global.NO_PTS;
//
//    public static final long MICRO_SECONDS_BETWEEN_FRAMES = 
//    	(long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
//
//    public static void createThumbnail(String inputFile){
//        long startTime = System.currentTimeMillis();
//        long stopTime = 0L;
//        String fileNameWithoutExt = inputFile.substring(0,inputFile.lastIndexOf("."));
//        
//        logger.info("createThumbnail - fileNameWithoutExt is {} " + fileNameWithoutExt);
//
//        IMediaReader mediaReader = ToolFactory.makeReader(inputFile);
//
//        try
//        {
//            mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
//            ImageSnapListener isListener = new ImageSnapListener();
//            isListener.outputFilePath = fileNameWithoutExt;
//            mediaReader.addListener(isListener);
//
//            // read out the contents of the media file and
//            // dispatch events to the attached listener
//            while (!isListener.isImageGrabbed()){
//                mediaReader.readPacket();
//            }
//            stopTime = System.currentTimeMillis();
//        } catch(Exception ex){
//            ex.printStackTrace();
//        }
//        logger.info("Total Time taken to create thumbnail: {} " + (stopTime-startTime));
//    }
//
//    private static class ImageSnapListener extends MediaListenerAdapter
//    {
//        public boolean imageGrabbed = false;
//        public String outputFilePath = null;  
//        
//        public void onVideoPicture(IVideoPictureEvent event){
//            if (event.getStreamIndex() != mVideoStreamIndex){
//
//                // if the selected video stream id is not yet set, go ahead an
//                // select this lucky video stream
//                if (mVideoStreamIndex == -1)
//                    mVideoStreamIndex = event.getStreamIndex();
//                // no need to show frames from this video stream
//                else
//                    return;
//            }
//
//            // if uninitialized, back date mLastPtsWrite to get the very first frame
//            if (mLastPtsWrite == Global.NO_PTS)
//                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
//
//            // if it's time to write the next frame
//            if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES){
//                String outputFilename = dumpImageToFile(event.getImage());
//                this.imageGrabbed = true; //set this var to true once an image is grabbed out of the movie.
//                // indicate file written
//                double seconds = ((double) event.getTimeStamp()) / Global.DEFAULT_PTS_PER_SECOND;
//
//                logger.info("at elapsed time of {} seconds wrote: {}\n",seconds, outputFilename);
//
//                // update last write time
//                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
//            }
//        }
//
//        private String dumpImageToFile(BufferedImage image){
//            try {
//                String outputFilename = outputFilePath + ".png";
//                logger.info("Thumbnail image name is going to be : {}",outputFilename);
//                ImageIO.write(image, "png", new File(outputFilename));
//                return outputFilename;
//            } catch (IOException e){
//                e.printStackTrace();
//                return null;
//            }
//        }

//        public boolean isImageGrabbed() {
//            return imageGrabbed;
//        }
//    }
}