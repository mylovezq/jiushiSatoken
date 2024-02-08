package fegin;

import com.jiushi.pay.api.dto.PayMsgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pay", path = "/api/pay/")
public interface PayMsgFeignClient {

    @PostMapping("/save/payMsg")
    void save(@RequestBody PayMsgDTO payMsg);
}