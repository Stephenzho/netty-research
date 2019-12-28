package study.common.auth;


import study.common.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    @Override
    public AuthOperationResult execute() {
        if("admin".equalsIgnoreCase(this.userName)){
            AuthOperationResult orderResponse = new AuthOperationResult(true);
            return orderResponse;
        }

        return new AuthOperationResult(false);
    }
}
