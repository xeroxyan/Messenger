package common;

public enum PacketType {
    // client
    REGISTER,   // payload: String[] {login, password}
    LOGIN,      // payload: String[] {login, password}
    LOGOUT,     // payload: null

    // common
    MESSAGE,    // payload: String (текст); to = получатель или null для общего чата
    FILE,       // payload: FileData (появится позже)

    // server
    AUTH_OK,    // успешный вход/регистрация; payload: null
    USER_LIST,  // payload: ArrayList<String> логинов онлайн
    ERROR       // payload: String (описание ошибки)
}
