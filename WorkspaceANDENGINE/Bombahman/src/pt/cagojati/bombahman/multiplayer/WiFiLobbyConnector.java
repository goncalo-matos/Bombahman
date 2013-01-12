package pt.cagojati.bombahman.multiplayer;

import java.io.IOException;
import java.net.Socket;

import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.client.IClientMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;
import org.andengine.util.debug.Debug;

import pt.cagojati.bombahman.Bomb;
import pt.cagojati.bombahman.GameActivity;
import pt.cagojati.bombahman.LobbyActivity;
import pt.cagojati.bombahman.Player;
import pt.cagojati.bombahman.multiplayer.messages.AddBombServerMessage;
import pt.cagojati.bombahman.multiplayer.messages.AddPlayerServerMessage;
import pt.cagojati.bombahman.multiplayer.messages.ConnectionCloseServerMessage;
import pt.cagojati.bombahman.multiplayer.messages.ExplodeBombServerMessage;
import pt.cagojati.bombahman.multiplayer.messages.JoinedServerServerMessage;
import pt.cagojati.bombahman.multiplayer.messages.KillPlayerServerMessage;
import pt.cagojati.bombahman.multiplayer.messages.MessageFlags;
import pt.cagojati.bombahman.multiplayer.messages.MovePlayerServerMessage;
import android.util.Log;

public class WiFiLobbyConnector implements ILobbyConnector  {
	private static final int SERVER_PORT = 4444;
	
	MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();
	private ServerConnector<SocketConnection> mServerConnector;
	private String mServerIP;
	private LobbyActivity mLobbyActivity;
	
	public WiFiLobbyConnector(String ip) {
		MessageFlags.initMessagePool(mMessagePool);
		mServerIP = ip;
	}
	
	public void setActivity(LobbyActivity game){
		mLobbyActivity = game;
	}
	
	public void initClient()
	{
		try{
			this.mServerConnector = new SocketConnectionServerConnector(new SocketConnection(new Socket(this.mServerIP, SERVER_PORT)), new ExampleServerConnectorListener());
	
			this.mServerConnector.registerServerMessage(MessageFlags.FLAG_MESSAGE_SERVER_CONNECTION_CLOSE, ConnectionCloseServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					mLobbyActivity.finish();
				}
			});
	
			this.mServerConnector.getConnection().start();
		}catch (final Throwable t) {
			Log.d("oteste", t.getMessage());
			Debug.e(t);
			mLobbyActivity.finish();
		}
	}
	
	private class ExampleServerConnectorListener implements ISocketConnectionServerConnectorListener {
		@Override
		public void onStarted(final ServerConnector<SocketConnection> pConnector) {
		}

		@Override
		public void onTerminated(final ServerConnector<SocketConnection> pConnector) {
//			MultiplayerExample.this.toast("CLIENT: Disconnected from Server...");
//			MultiplayerExample.this.finish();
		}
	}

	@Override
	public MessagePool<IMessage> getMessagePool() {
		return mMessagePool;
	}

	@Override
	public void sendClientMessage(IClientMessage msg) {
		try {
			this.mServerConnector.sendClientMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void terminate() {
		if(this.mServerConnector!=null){
			this.mServerConnector.terminate();
		}
	}
}
