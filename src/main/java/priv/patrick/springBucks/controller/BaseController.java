package priv.patrick.springBucks.controller;

import priv.patrick.springBucks.enums.ResultCodeEnum;
import priv.patrick.springBucks.pojo.Result;

public abstract class BaseController {
    protected <T> Result<T> returnSuccess() {
        return new Result<T>(true, ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    protected <T> Result<T> returnSuccess(T data) {
        return new Result<T>(true, ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    protected <T> Result<T> returnFail() {
        return new Result<T>(false, ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage(), null);
    }

    protected <T> Result<T> returnFail(String message) {
        return new Result<T>(false, ResultCodeEnum.FAIL.getCode(), message, null);
    }

}
