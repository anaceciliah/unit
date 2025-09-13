package unit.br.unitnetwork.dto;

import com.sun.net.httpserver.HttpContext;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ResponseDTO {

    @Getter
    private List<String> messages;

    @Getter
    private HttpStatus status;

    @Getter
    private int  code;

    public ResponseDTO(HttpStatus status, List<String> messages) {
        this.messages = messages;
        this.status = status;
        this.code = status.value();

    }
    public ResponseDTO(String messages, HttpStatus status) {
        this.messages = Arrays.asList(messages);
        this.status = status;
        this.code = status.value();
    }



}
