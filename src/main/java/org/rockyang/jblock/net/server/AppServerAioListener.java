package org.rockyang.jblock.net.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.TioServerListener;

/**
 * 服务端连接状态的监听器
 * @author yangjian
 */
@Component
public class AppServerAioListener implements TioServerListener {

	private static Logger log = LoggerFactory.getLogger(AppServerAioListener.class);

	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) {

	}

	@Override
	public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception
	{

	}

	@Override
	public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception
	{

	}

	@Override
	public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) {

	}

	@Override
	public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception
	{

	}

	@Override
	public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {

	}

	@Override
	public boolean onHeartbeatTimeout(ChannelContext channelContext, Long aLong, int i)
	{
		return false;
	}
}
