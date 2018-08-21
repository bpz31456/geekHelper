package cn.baopz.core;

/**
 * @author baopz
 * @date 2018.08.17
 */
public enum  KeyWords {
    NOT_LOGGED_IN("注册")
    ;
    private String value;

    KeyWords(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }


}
