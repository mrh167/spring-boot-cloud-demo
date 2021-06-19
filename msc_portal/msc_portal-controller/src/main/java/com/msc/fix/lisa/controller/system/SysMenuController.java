package com.msc.fix.lisa.controller.system;

import com.alibaba.cola.dto.SingleResponse;
import com.msc.fix.lisa.base.AbstractController;
import com.msc.fix.lisa.common.BaseHttpCodeResponse;
import com.msc.fix.lisa.common.RRException;
import com.msc.fix.lisa.domain.common.annotation.SysLog;
import com.msc.fix.lisa.domain.common.utils.BeanUtils;
import com.msc.fix.lisa.domain.common.utils.Constant;
import com.msc.fix.lisa.domain.entity.system.SysMenu;
import com.msc.fix.lisa.domain.gateway.system.ShiroGateway;
import com.msc.fix.lisa.domain.gateway.system.SysMenuGateway;
import com.msc.fix.lisa.dto.system.cto.SysMenuCo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuGateway sysMenuGateway;
    @Autowired
    private ShiroGateway shiroGateway;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public SingleResponse<Map<String, Object>> nav(){
        List<SysMenuCo> menuList = sysMenuGateway.getUserMenuList(getUserId());
        Set<String> permissions = shiroGateway.getUserPermissions(getUserId());
        Map<String,Object> map = new HashMap<>();
        map.put("menuList", menuList);
        map.put("permissions", permissions);
        return SingleResponse.of(map);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuCo> list(){
        List<SysMenu> menuList = sysMenuGateway.list();
        for(SysMenu sysMenuEntity : menuList){
            SysMenu parentMenuEntity = sysMenuGateway.getById(sysMenuEntity.getParentId());
            if(parentMenuEntity != null){
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }
        List<SysMenuCo> sysMenuCos = BeanUtils.convertList(menuList, SysMenuCo.class);
        return sysMenuCos;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public SingleResponse select(){
        //查询列表数据
        List<SysMenuCo> menuList = sysMenuGateway.queryNotButtonList();

        //添加顶级菜单
        SysMenuCo root = new SysMenuCo();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        Map<String,Object> map = new HashMap<>();
        map.put("menuList", menuList);
        return SingleResponse.of(map);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public SingleResponse info(@PathVariable("menuId") Long menuId){
        SysMenu menu = sysMenuGateway.getById(menuId);
        Map<String,Object> map = new HashMap<>();
        map.put("menu", menu);
        return SingleResponse.of(map);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public SingleResponse save(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuGateway.save(menu);

        return SingleResponse.buildSuccess();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public SingleResponse update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuGateway.updateById(menu);

        return SingleResponse.buildSuccess();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public SingleResponse delete(@PathVariable("menuId") long menuId){
        if(menuId <= 31){
            return BaseHttpCodeResponse.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuCo> menuList = sysMenuGateway.queryListParentId(menuId);
        if(menuList.size() > 0){
            return BaseHttpCodeResponse.error("请先删除子菜单或按钮");
        }

        sysMenuGateway.delete(menuId);

        return SingleResponse.buildSuccess();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new RRException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new RRException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenu parentMenu = sysMenuGateway.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new RRException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new RRException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }



}
