package onshow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import onshow.domain.Room;
import onshow.service.RoomService;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.ServiceUtils;

public class Application extends ApplicationAdapter {

	private Map<String,IConnection> clientMap;
	
	@Override
	public boolean appStart(IScope app) {
		clientMap = new HashMap<String, IConnection>();
		return super.appStart(app);
	}
	
	
	@Override
	public boolean appConnect(IConnection conn, Object[] arg1) {
		Object o = conn.getScope().getContext().getBean("roomService");
		RoomService roomService = (RoomService)o;
		List<Room> rooms = roomService.getRoomList();
		ServiceUtils.invokeOnConnection(conn, "initRoomList", new Object[]{rooms});
		return super.appConnect(conn, arg1);
	}


	@Override
	public boolean roomConnect(IConnection conn, Object[] params) {
//		ObjectMap<String, String> param = (ObjectMap<String, String>)params[0];
//		if(param == null){
//			rejectClient("没有传递用户信息，不能连接房间");
//		}
//		UserInfo userInfo = new UserInfo();
//		userInfo.setUserName(param.get("userName"));
//		userInfo.setIcon(param.get("icon"));
//		userInfo.setIconm(param.get("iconm"));
//		userInfo.setUserId(conn.getClient().getId());
//		ISharedObject so = this.getSharedObject(conn.getScope(), "clientList");
//		if(so == null){
//			this.createSharedObject(conn.getScope(), "clientList", false);
//			so = this.getSharedObject(conn.getScope(), "clientList");
//		}
//		so.setAttribute(userInfo.getUserId(), userInfo);
//		
//		//缓存当前用户连接
//		clientMap.put(userInfo.getUserId(), conn);
//		
//		//调用当前客户端，传递当前客户端id
//		ServiceUtils.invokeOnConnection("setClientId", new Object[]{userInfo.getUserId()});
		
		return super.roomConnect(conn, params);
	}

	@Override
	public void roomDisconnect(IConnection conn) {
		// TODO Auto-generated method stub
		super.roomDisconnect(conn);
	}
	
	public void callClient(String methodName,String clientId,Object[] args){
		IConnection conn = clientMap.get(clientId);
		ServiceUtils.invokeOnConnection(conn, methodName, args);		
	}
	
	public void callEvery(String methodName,Object[] args){
		ServiceUtils.invokeOnAllConnections(methodName, args);
	}
}
