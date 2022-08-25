package eu.andredick.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;


public class FileTools {
    /**
     * 从指定路径提取文件
     * @param path      文件路径
     * @param filename  文件名
     * @return          文件流
     */
    public static InputStream getStreamFromPackageFile(String path, String filename) {
        InputStream resourceAsStream = FileTools.class.getResourceAsStream(path + filename);
        return resourceAsStream;
    }

    public static InputStream getStreamFromLocalGZIPFile(File file) {
        InputStream inputStream = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            inputStream = new GZIPInputStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static InputStream getStreamFromLocalFile(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }


    public static void extractFileFromGZIP(File sourceFile, File destinationFile) {
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            gis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean downloadFile(String url, File destinationFile) {
        boolean success = false;
        URL website = null;
        try {
            website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(destinationFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            success = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean existsOnlineFile(String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean existsLocalFile(File file) {
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    public static boolean existsLocalFolder(File folder) {
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        return false;
    }

    public static boolean canWriteInPath(Path path) {
        if (Files.isWritable(path)) {
            return true;
        }
        return false;
    }

    public static boolean canWriteFile(File file) {
        if (file.canWrite()) {
            return true;
        }
        return false;
    }
}
