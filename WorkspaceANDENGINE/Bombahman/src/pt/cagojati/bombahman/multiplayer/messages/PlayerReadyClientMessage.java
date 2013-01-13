package pt.cagojati.bombahman.multiplayer.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

import android.R.bool;

public class PlayerReadyClientMessage  extends ClientMessage{

	private int mPlayerId;
	private boolean mIsReady;

	public PlayerReadyClientMessage() {

	}

	public PlayerReadyClientMessage(final int playerId) {
		this.setPlayerId(playerId);
	}

	public void set(int playerid, boolean isready) {
		this.setPlayerId(playerid);
		this.setIsReady(isready);
	}

	@Override
	public short getFlag() {
		return MessageFlags.FLAG_MESSAGE_CLIENT_PLAYER_READY;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		this.setPlayerId(pDataInputStream.readInt());
		this.setIsReady(pDataInputStream.readBoolean());
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.getPlayerId());
		pDataOutputStream.writeBoolean(this.getIsReady());
	}

	public int getPlayerId() {
		return mPlayerId;
	}

	private void setPlayerId(int playerId) {
		this.mPlayerId = playerId;
	}

	public boolean getIsReady() {
		return mIsReady;
	}

	public void setIsReady(boolean mIsReady) {
		this.mIsReady = mIsReady;
	}

}
