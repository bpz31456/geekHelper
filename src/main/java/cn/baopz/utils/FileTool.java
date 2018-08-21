package cn.baopz.utils;

/**
 * file tool
 * @author baopz
 */
public final class FileTool {
    /**
     * windows File Name Filter
     * @param filename
     * @return
     */
    public static String windowsFileNameFilter(String filename){
       return filename.replaceAll("[\\ \\\\/:\\\\*\\\\? \"<> |]","-");
    }
}
