package com.neuhealth.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neuhealth.demo.domain.Client;
import com.neuhealth.demo.domain.ClientRegisterDTO;
import com.neuhealth.demo.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("clients")
public class ClientController {
    @Autowired
    IClientService clientService;
    //展示列表
    @GetMapping("findAll")
    public List<Client> findAll(){
        return clientService.findAll();
    }
    //分页
    @GetMapping("/page")
    public Map<String, Object> getClientsPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) {
        Map<String, Object> res = new HashMap<>();
        // 调用分页查询
        Page<Client> pageResult = clientService.findClientsByPage(pageNum, pageSize, name, type);
        res.put("isOk", true);
        res.put("msg", "分页查询成功");
        res.put("records", pageResult.getRecords());
        res.put("total", pageResult.getTotal());
        return res;
    }

    //展示逻辑删除后列表
    @GetMapping("/active")
    public Map<String, Object> getAllActiveClients() {
        List<Client> clients = clientService.findAllActive();
        Map<String, Object> res = new HashMap<>();
        res.put("isOk", true);
        res.put("msg", "查询成功");
        res.put("clients", clients);
        return res;
    }
    //查询单个
    //权限：@PreAuthorize("@ss.hasPermi('system:clients:query')")
    @GetMapping
    //public Map getInfo(@PathVariable("id") Long id)
    public Map getInfo(
            @RequestParam String name,
            @RequestParam String type
    )
    {
        List<Client> client = this.clientService.searchClients(name,type);
        Map res = new HashMap();
        res.put("isOk",true);
        res.put("msg","信息查询成功");
        res.put("client",client);
        return res;
    }
    //添加客户
    @PostMapping
    public Map addClient(@RequestBody ClientRegisterDTO dto){
        this.clientService.registerClient(dto);
        Map res = new HashMap();
        res.put("isOk",true);
        res.put("msg","新的客户添加成功！");
        return res;
    }
    //删除客户
    @DeleteMapping("/{id}")
    public Map delClient(@PathVariable("id") int id){
        this.clientService.deleteClient(id);
        Map res= new HashMap();
        res.put("isOk",true);
        res.put("msg","删除成功");
        return res;
    }
    //批量删除
    @DeleteMapping("/batch")
    public Map delClients(@RequestBody List<Long> ids) {
        this.clientService.deleteClientsByIds(ids);
        Map<String, Object> res = new HashMap<>();
        res.put("isOk", true);
        res.put("msg", "批量删除成功");
        return res;
    }

    //修改信息
    @PutMapping
    public Map edit(@RequestBody Client client){
        this.clientService.updateClient(client);
        Map res = new HashMap();
        res.put("isOk",true);
        res.put("msg","修改成功");
        return res;
    }


}
