package com.ticho.storage.view;

import com.ticho.core.mvc.enums.IResultCode;

import java.io.Serializable;

/**
 * 状态错误码
 *
 * @author AdoroTutto
 * @date 2021-10-16 20:24
 */
public enum MinioResultCode implements Serializable, IResultCode {

    // @formatter:off

    /**
     *
     */
    BUCKET_IS_NOT_EMPTY(10001, "文件桶不为空"),

    BUCKET_IS_ALREAD_EXISITS(10001, "文件桶已存在"),

    CREATE_BUCKET_ERROR(10002, "创建文件桶失败"),

    DELETE_BUCKET_ERROR(10003, "删除文件桶失败"),

    SELECT_BUCKET_ERROR(10004, "查询文件桶失败"),

    UPLOAD_ERROR(10005, "上传文件失败"),

    DOWNLOAD_ERROR(10005, "下载文件失败"),

    DELETE_OBJECT_ERROR(10006, "删除文件失败"),

    SELECT_OBJECT_URL_ERROR(10008, "查询文件外链失败"),

    SELECT_OBJECT_ERROR(10008, "查询文件失败");

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final int code;

    /**
     *状态信息
     */
    private final String msg;

    MinioResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return String.format(" %s:{code=%s, msg=%s} ", this.getClass().getSimpleName(), this.code, this.msg);
    }

}
