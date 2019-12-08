package com.union.service.reference;

import com.auth.dto.result.UserDTO;
import com.union.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth",url = "http://localhost:9002")
public interface UserInfoReference {

    @GetMapping("/api/user/query")
    R<UserDTO> query(@RequestParam("userId") Long userId);
}
