package priv.patrick.springBucks.enums;

public enum ResultCodeEnum {
    SUCCESS("0", "操作成功"),
    FAIL("-1", "操作失败");

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
