package com.msyh.controller;

import com.msyh.enttity.Person;
import com.msyh.enttity.DTO.PersonDTO;
import com.msyh.mapper.PersonMapper;
import com.msyh.service.jpa.PersonService;
import com.msyh.utillity.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 13778
 */
@RestController
@RequestMapping(value = "person")
public class PeronController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @ApiOperation(value = "添加用户",notes ="添加一个完成的用户" )
    @PostMapping(path = "addPerson")
    public void addPerson(Person person){
        personService.save(person);
    }




    @ApiOperation(value = "删除用户",notes ="通过ID用户" )
    @ApiImplicitParam(name = "id",value = "用户ID",required = true, dataType = "Integer", paramType = "path")
    @DeleteMapping(path = "deletePerson")
    public void deletePerson(Long id){
        personService.deleteById(id);
    }



    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @RequestMapping(value = "personList", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList () {
        JsonResult r = new JsonResult();
        try {
            List<PersonDTO> personList=personMapper.selectList(null);
            r.setResult(personList);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);

    }
}
