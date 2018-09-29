import java.time.LocalDateTime;

public class LogToken {
    private LocalDateTime time;
    private HttpMethod method;
    private String message;

    public LogToken(HttpMethod method, String message) {
        this.method = method;
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public HttpMethod getMetthod() {
        return method;
    }

    public void setMetthod(HttpMethod metthod) {
        this.method = metthod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogToken{" +
                "time=" + time +
                ", method=" + method +
                ", message='" + message + '\'' +
                '}';
    }
}
