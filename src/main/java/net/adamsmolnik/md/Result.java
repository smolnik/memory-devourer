package net.adamsmolnik.md;

public class Result {

    private final String url;

    private final String status;

    private final String html;

    public Result(String url, String status, String html) {
        this.status = status;
        this.html = html;
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public String getHtml() {
        return html;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Result [status=" + status + ", html=" + html + "]";
    }

}
