package cn.baopz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author baopz
 * @date 2018.08.17
 */
public final class Serializer {
    private static Logger logger = LoggerFactory.getLogger(Serializer.class);

    private Serializer() {
    }

    /**
     * save
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean save(Object source, Path target) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(target));
            objectOutputStream.writeObject(source);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("save file error.");
            return false;
        }
        return true;
    }

    /**
     * read
     *
     * @param source
     * @return
     */
    public static Object back(Path source) {
        Object o = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(source));
            o = objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("read file error.");
            return null;
        }
        return o;
    }
}
