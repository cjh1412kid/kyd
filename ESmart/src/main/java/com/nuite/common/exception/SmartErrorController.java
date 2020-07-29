package com.nuite.common.exception;

import com.nuite.common.utils.Result;
import com.nuite.common.utils.ResultStatus;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class SmartErrorController extends BasicErrorController {
    public SmartErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = this.getStatus(request);
        Result result;
        if (status.value() == 404) {
            result = Result.error(ResultStatus.FAILED.value(), "请求api不存在");
        } else {
            Object message = body.get("message");
            result = Result.error(ResultStatus.FAILED.value(), message == null ? "" : message.toString());
        }

        return new ResponseEntity<>(result, status);
    }
}
