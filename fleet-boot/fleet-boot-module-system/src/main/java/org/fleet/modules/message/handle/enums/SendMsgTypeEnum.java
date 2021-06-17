package org.fleet.modules.message.handle.enums;

import org.fleet.common.util.oConvertUtils;

/**
 * 发送消息类型枚举
 */
public enum SendMsgTypeEnum {

    //推送方式：1短信 2邮件 3微信
    SMS("1", "org.fleet.modules.message.handle.impl.SmsSendMsgHandle"),
    EMAIL("2", "org.fleet.modules.message.handle.impl.EmailSendMsgHandle"),
    WX("3", "org.fleet.modules.message.handle.impl.WxSendMsgHandle");

    private String type;

    private String implClass;

    private SendMsgTypeEnum(String type, String implClass) {
        this.type = type;
        this.implClass = implClass;
    }

    public static SendMsgTypeEnum getByType(String type) {
        if (oConvertUtils.isEmpty(type)) {
            return null;
        }
        for (SendMsgTypeEnum val : values()) {
            if (val.getType().equals(type)) {
                return val;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImplClass() {
        return implClass;
    }

    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }
}
