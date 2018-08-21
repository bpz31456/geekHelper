package cn.baopz.core;

/**
 * @author baopz
 */
public interface Authentication {

    /**
     * passed ?
     * @return
     * @throws LoginException
     */
    default boolean check() throws LoginException {
        return false;
    }
}
