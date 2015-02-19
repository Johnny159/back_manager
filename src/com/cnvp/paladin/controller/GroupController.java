package com.cnvp.paladin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseController;
import com.cnvp.paladin.model.SysGroup;

public class GroupController extends BaseController {
	public void index(){
		setAttr("page", SysGroup.dao.paginate(getParaToInt(0, 1), 10));
	}
	public void getlist(){
		Map<String, Object> json =  new HashMap<String, Object>();
		List<SysGroup> data = SysGroup.dao.where("");
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).hasChild())
				data.get(i).getAttrs().put("hasChild",true);
			else
				data.get(i).getAttrs().put("hasChild",false);
		}
		json.put("data", data);
		renderJson(json);
	}
	public void create(){
		if(isPost()){
			if(getModel(SysGroup.class,"sysgroup").save())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", new SysGroup());
		render("form.html");
	}

	public void update(){
		if(isPost()){
			if(getModel(SysGroup.class,"sysgroup").set("id", getParaToInt(0)).update())
				redirect(getControllerKey());
				return;
		}
		setAttr("data", SysGroup.dao.findById(getParaToInt(0)));
		render("form.html");
	}
	public void delete(){
		if (SysGroup.dao.findById(getParaToInt(0)).delete()) 
			redirect(getControllerKey());
		else
			alertAndGoback("删除失败");
	}
	public void deleteAll(){
		Integer[] ids=getParaValuesToInt("id");
		for (Integer id : ids) {
			SysGroup.dao.findById(id).delete();
		}
		redirect(getControllerKey());
	}
	
}
			