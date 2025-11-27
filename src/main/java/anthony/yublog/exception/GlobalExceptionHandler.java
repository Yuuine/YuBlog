package anthony.yublog.exception;

import anthony.yublog.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获自定义业务异常
     * 例如：用户不存在、权限不足等
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<Object>> handleBizException(BizException e, HttpServletRequest request) {
        // 记录业务异常日志（使用 WARN 级别，预期的业务错误）
        String traceId = generateTraceId();
        MDC.put("traceId", traceId);
        try {
            log.warn("业务异常: URI={}, method={}, code={}, message={}",
                    request.getRequestURI(),
                    request.getMethod(),
                    e.getCode(),
                    e.getMessage());

            // 业务异常统一 Result 响应格式
            Result<Object> result = Result.error(Integer.parseInt(e.getCode()), e.getMessage());

            // 返回 400 状态码
            return ResponseEntity.badRequest().body(result);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 生成追踪ID
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}
