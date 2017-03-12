package org.yawlfoundation.yawl.resourcing.datastore.orgdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.resourcing.resource.Role;
import org.yawlfoundation.yawl.resourcing.util.HttpRequestUtils;

import net.sf.json.JSONObject;

/*
 * use busirole  to get organrole from mapping center
 */
public class GetOrganPosByBusiRole {
	
	  private GetExternalSource _getExterSource = new GetExternalSource();
	  private String url = "http://localhost:3000/rolemap/getOrganRoleByBusiRole/";  // the address of mapping center
	
	  private List<String> getBusiRole( WorkItemRecord wir, HashSet<Role> roles) {
		  List<String> _busiRoleList = new ArrayList<String>();  //  busirole set
		  String _specID = wir.getSpecIdentifier();
	      String _taskID = wir.getTaskID();
	      for ( Role role : roles ) {
          	String _busiRoleId =  _specID+_taskID+role.getID();  // _busiRloleId composed by specId+taskID+roleID
              _busiRoleList.add(_busiRoleId);
          }
	      return _busiRoleList;
        
	  }
	
	  private String getOrganPos (WorkItemRecord wir, HashSet<Role> roles) {
		  List<String> busiRoles = getBusiRole(wir,roles);
		  JSONObject _jsonObj = new JSONObject();
		  _jsonObj.put("busiRoles", busiRoles);
          String _strResult= HttpRequestUtils.httpPost(url, _jsonObj,false); // get organPos 
          System.out.println( _strResult );
          return _strResult;
	  }
    
	  public List<String> getParticipantsId ( WorkItemRecord wir, HashSet<Role> roles ) {
		  String _organPosName = getOrganPos(wir, roles);
		  return  _getExterSource.loadParticipantByPosName(_organPosName);
	  }
     
}
