package app.atr.mobitribe.com.app_time_recorder.model;

/**
 * Author: Muhammad Shahab
 * Date: 5/8/17.
 * Description: Generic response for all apis
 */
public class Response {

    private Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public class Meta {

        private boolean success;
        private int code;
        private String message;
        private String message_detail;

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage_detail() {
            return message_detail;
        }

        public void setMessage_detail(String message_detail) {
            this.message_detail = message_detail;
        }

    }

}

