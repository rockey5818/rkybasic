package com.nfkj.test;
import org.json.me.JSONObjectDef;
import org.json.parser.JSONEntityDesc;

import com.nfkj.basic.parse.json.EntityAssembler;
import com.nfkj.basic.util.json.JSONUtil;

 /**  
 * @author Rockey
 * @Description todo
 */
public class People  implements JSONEntityDesc
{
	public int id ;
	public String name;
	public String des;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public void fromJson(JSONObjectDef jsonObj) throws Exception {
//		id = EntityParser.getInteger("id",jsonObj, 2);
//		name= jsonObj.getString("name");
	}
	@Override
	public JSONObjectDef toJson() throws Exception {
		JSONObjectDef d= JSONUtil.createJSONObject();
		EntityAssembler.putInt("id", 4, d);
		 	
		return d;
	}
	
}
