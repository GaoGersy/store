package com.piesat.project.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gersy
 * @since 2018-04-24
 */
@TableName("sys_exception_info")
public class SysExceptionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String errorType;
    private String errorStackInfo;

    public SysExceptionInfo(String errorType, String errorStackInfo) {
        this.errorType = errorType;
        this.errorStackInfo = errorStackInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorStackInfo() {
        return errorStackInfo;
    }

    public void setErrorStackInfo(String errorStackInfo) {
        this.errorStackInfo = errorStackInfo;
    }

    @Override
    public String toString() {
        return "SysExceptionInfo{" +
        ", id=" + id +
        ", errorType=" + errorType +
        ", errorStackInfo=" + errorStackInfo +
        "}";
    }
}
