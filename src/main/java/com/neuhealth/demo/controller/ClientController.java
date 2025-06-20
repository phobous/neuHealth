package com.neuhealth.demo.controller;

import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/unallocated")
    @ResponseBody
    public Map<String, Object> getUnallocatedClients() {
        // 只传 false，表示未分配
        List<Client> clients = clientService.searchClientsNoCg(false);

        Map<String, Object> response = new HashMap<>();
        response.put("isOk", true);
        response.put("count", clients.size());
        response.put("data", clients);
        response.put("msg", "未分配客户查询成功");
        return response;
    }

}
