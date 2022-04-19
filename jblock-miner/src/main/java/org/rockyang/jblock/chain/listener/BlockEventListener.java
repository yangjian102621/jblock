package org.rockyang.jblock.chain.listener;

import org.rockyang.jblock.base.model.Block;
import org.rockyang.jblock.base.utils.SerializeUtils;
import org.rockyang.jblock.chain.event.NewBlockEvent;
import org.rockyang.jblock.chain.event.SyncBlockEvent;
import org.rockyang.jblock.chain.service.BlockService;
import org.rockyang.jblock.net.base.MessagePacket;
import org.rockyang.jblock.net.base.MessagePacketType;
import org.rockyang.jblock.net.client.AppClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author yangjian
 */
@Component
public class BlockEventListener {

	private static final Logger logger = LoggerFactory.getLogger(BlockEventListener.class);

	private final AppClient client;
	private final BlockService blockService;

	public BlockEventListener(AppClient appClient, BlockService blockService)
	{
		this.client = appClient;
		this.blockService = blockService;
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void appReady()
//	{
//		ApplicationContextProvider.publishEvent(new SyncBlockEvent(0));
//	}

	// mine a new block event
	@EventListener(NewBlockEvent.class)

	public void newBlock(NewBlockEvent event)
	{
		Block block = (Block) event.getSource();
		logger.info("NewBlockEvent: start to broadcast block {}", block.getHeader().getHeight());
		MessagePacket messagePacket = new MessagePacket();
		messagePacket.setType(MessagePacketType.REQ_NEW_BLOCK);
		messagePacket.setBody(SerializeUtils.serialize(block));
		client.sendGroup(messagePacket);
	}

	// sync the specified height block
	@EventListener(SyncBlockEvent.class)
	public void syncBlock(SyncBlockEvent event)
	{
		long height = (long) event.getSource();
		if (height == 0) {
			long head = blockService.chainHead();
			if (head > 0) {
				height = head + 1;
			}
		}
		logger.info("SyncBlockEvent: start to sync block {}", height);
		MessagePacket messagePacket = new MessagePacket();
		messagePacket.setType(MessagePacketType.REQ_BLOCK_SYNC);
		messagePacket.setBody(SerializeUtils.serialize(height));
		// @TODO: maybe we should not to send all peers to fetch block
		// @TODO: it's a waste of network, we can choose some well-synchronized nodes ONLY.
		client.sendGroup(messagePacket);
	}
}
