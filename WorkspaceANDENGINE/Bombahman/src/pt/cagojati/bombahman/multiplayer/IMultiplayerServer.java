package pt.cagojati.bombahman.multiplayer;

import org.andengine.extension.multiplayer.protocol.server.Server;
import org.andengine.extension.multiplayer.protocol.server.connector.ClientConnector;
import org.andengine.extension.multiplayer.protocol.shared.Connection;

public interface IMultiplayerServer {

	@SuppressWarnings("rawtypes")
	public Server getServerSocket();
	
	public void initServer();
}
